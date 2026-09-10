import os
import sys
import shutil

# --- Configuration ---
OLD_BASE_PACKAGE = "com.project"
OLD_APP_PACKAGE = "com.project.starter"
OLD_APP_NAME_STRING = "Android Project Template" # Used in app/src/main/res/values/strings.xml
OLD_PROJECT_NAME = "Android-Project-Template" # Used in settings.gradle.kts

def rename_package_in_file(filepath, old_pkg, new_pkg):
    try:
        with open(filepath, 'r', encoding='utf-8') as file:
            content = file.read()
        
        if old_pkg in content:
            new_content = content.replace(old_pkg, new_pkg)
            with open(filepath, 'w', encoding='utf-8') as file:
                file.write(new_content)
    except Exception as e:
        print(f"Error processing file {filepath}: {e}")

def replace_app_name_in_file(filepath, new_name):
    try:
        with open(filepath, 'r', encoding='utf-8') as file:
            content = file.read()
            
        # specifically target strings.xml
        if '<string name="app_name">' in content:
            import re
            content = re.sub(r'<string name="app_name">.*</string>', f'<string name="app_name">{new_name}</string>', content)
            
        # replace generic project names
        content = content.replace(OLD_PROJECT_NAME, new_name.replace(" ", "-"))
        
        with open(filepath, 'w', encoding='utf-8') as file:
            file.write(content)
    except Exception as e:
        print(f"Error processing app name in {filepath}: {e}")

def move_directories(root_dir, old_pkg, new_pkg):
    old_path_parts = old_pkg.split('.')
    new_path_parts = new_pkg.split('.')
    
    # We walk bottom-up so renaming parent directories doesn't break our path iteration
    for root, dirs, files in os.walk(root_dir, topdown=False):
        # Prevent touching git or gradle build/cache folders
        if '.git' in root or '.gradle' in root or '\\build\\' in root or '/build/' in root:
            continue
            
        for d in dirs:
            if d == old_path_parts[-1]:
                # Check if the full path matches the old package structure
                full_dir_path = os.path.join(root, d)
                old_relative = os.path.sep.join(old_path_parts)
                
                if full_dir_path.endswith(old_relative):
                    # We found a matching directory structure, let's move it
                    base_path = full_dir_path[:-len(old_relative)]
                    new_relative = os.path.sep.join(new_path_parts)
                    new_full_path = os.path.join(base_path, new_relative)
                    
                    print(f"Moving {full_dir_path} -> {new_full_path}")
                    os.makedirs(new_full_path, exist_ok=True)
                    
                    # Move all contents to new path
                    for item in os.listdir(full_dir_path):
                        shutil.move(os.path.join(full_dir_path, item), new_full_path)
                    
                    # Cleanup old empty directories
                    cleanup_path = full_dir_path
                    while cleanup_path != base_path and not os.listdir(cleanup_path):
                        os.rmdir(cleanup_path)
                        cleanup_path = os.path.dirname(cleanup_path)

def main():
    if len(sys.argv) != 3:
        print("Usage: python setup.py <new.package.name> \"New App Name\"")
        print("Example: python setup.py com.mycompany.awesomeapp \"Awesome App\"")
        sys.exit(1)

    new_package = sys.argv[1]
    new_app_name = sys.argv[2]
    root_dir = os.path.abspath(os.path.dirname(__file__))

    print(f"🚀 Setting up template for: {new_app_name} ({new_package})")

    # Step 1: Replace text in files
    print("📝 Updating package names and app names in files...")
    for root, dirs, files in os.walk(root_dir):
        if '.git' in root or '.gradle' in root or '\\build\\' in root or '/build/' in root or '.idea' in root:
            continue
            
        for file in files:
            if file.endswith(('.kt', '.kts', '.xml', '.pro', 'gradle.properties', '.sh')):
                filepath = os.path.join(root, file)
                
                # Replace the exact starter package first to avoid com.newpackage.starter
                rename_package_in_file(filepath, OLD_APP_PACKAGE, new_package)
                # Then replace the generic com.project package
                rename_package_in_file(filepath, OLD_BASE_PACKAGE, new_package)
                
                # Replace app name
                replace_app_name_in_file(filepath, new_app_name)

    # Step 2: Move directories to match new package structure
    print("📁 Restructuring directories...")
    # Restructure the starter package first
    move_directories(root_dir, OLD_APP_PACKAGE, new_package)
    # Then restructure the base packages (for common, core, feat modules)
    move_directories(root_dir, OLD_BASE_PACKAGE, new_package)

    print("\n✅ Template setup complete!")
    print("⚠️  You can now safely delete setup.py")
    print("🧹 Please Sync your Gradle project in Android Studio and do a Clean Build.")

if __name__ == "__main__":
    main()
