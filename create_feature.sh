#!/bin/bash

# Ensure script stops on first error
set -e

if [ -z "$1" ]; then
    echo "❌ Error: Please provide a feature module name."
    echo "Usage: ./create_feature.sh <feature_name>"
    echo "Example: ./create_feature.sh login"
    exit 1
fi

FEATURE_NAME=$1
# Capitalize first letter for class names
FEATURE_CLASS_NAME="$(tr '[:lower:]' '[:upper:]' <<< ${FEATURE_NAME:0:1})${FEATURE_NAME:1}"

# Define base package (This will be automatically updated by setup.py if the template is cloned)
BASE_PKG="com.project"
FEATURE_PKG="$BASE_PKG.feat.$FEATURE_NAME"
PKG_PATH="$(echo $FEATURE_PKG | tr '.' '/')"

TARGET_DIR="feat/$FEATURE_NAME"
SRC_DIR="$TARGET_DIR/src/main"
JAVA_DIR="$SRC_DIR/java/$PKG_PATH"

echo "🚀 Creating feature module: $FEATURE_NAME"

# 1. Create directories
mkdir -p "$JAVA_DIR/data/repository"
mkdir -p "$JAVA_DIR/domain/model"
mkdir -p "$JAVA_DIR/domain/repository"
mkdir -p "$JAVA_DIR/domain/usecase"
mkdir -p "$JAVA_DIR/presentation/navigation"
mkdir -p "$SRC_DIR/keepRules"

# 2. Create build.gradle.kts
cat <<EOF > "$TARGET_DIR/build.gradle.kts"
plugins {
    alias(libs.plugins.convention.feature)
}
EOF

# 3. Create AndroidManifest.xml
cat <<EOF > "$SRC_DIR/AndroidManifest.xml"
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

</manifest>
EOF

# 4. Create keepRules
touch "$SRC_DIR/keepRules/rules.keep"
cat <<EOF > "$TARGET_DIR/consumer-rules.keep"
# Add your consumer rules here
EOF

# 5. Create Routes (Type-safe Navigation)
cat <<EOF > "$JAVA_DIR/presentation/navigation/${FEATURE_CLASS_NAME}Routes.kt"
package $FEATURE_PKG.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object ${FEATURE_CLASS_NAME}Route
EOF

# 6. Create Basic ViewModel
cat <<EOF > "$JAVA_DIR/presentation/${FEATURE_CLASS_NAME}ViewModel.kt"
package $FEATURE_PKG.presentation

import $BASE_PKG.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class ${FEATURE_CLASS_NAME}State(
    val isLoading: Boolean = false
)

sealed class ${FEATURE_CLASS_NAME}Event {
    object Init : ${FEATURE_CLASS_NAME}Event()
}

sealed class ${FEATURE_CLASS_NAME}Effect

@HiltViewModel
class ${FEATURE_CLASS_NAME}ViewModel @Inject constructor() : 
    BaseViewModel<${FEATURE_CLASS_NAME}Event, ${FEATURE_CLASS_NAME}State, ${FEATURE_CLASS_NAME}Effect>(${FEATURE_CLASS_NAME}State()) {

    override fun handleEvent(event: ${FEATURE_CLASS_NAME}Event) {
        when (event) {
            is ${FEATURE_CLASS_NAME}Event.Init -> {
                // Initialize something
            }
        }
    }
}
EOF

# 7. Create Basic Screen
cat <<EOF > "$JAVA_DIR/presentation/${FEATURE_CLASS_NAME}Screen.kt"
package $FEATURE_PKG.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ${FEATURE_CLASS_NAME}Screen(
    viewModel: ${FEATURE_CLASS_NAME}ViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "${FEATURE_CLASS_NAME} Screen")
    }
}
EOF

# 8. Create Navigation Graph Builder
cat <<EOF > "$JAVA_DIR/presentation/navigation/${FEATURE_CLASS_NAME}Navigation.kt"
package $FEATURE_PKG.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import $FEATURE_PKG.presentation.${FEATURE_CLASS_NAME}Screen

fun NavGraphBuilder.${FEATURE_NAME}Graph(navController: NavController) {
    composable<${FEATURE_CLASS_NAME}Route> {
        ${FEATURE_CLASS_NAME}Screen()
    }
}
EOF

# 9. Inject to settings.gradle.kts
if ! grep -q "include(\":feat:$FEATURE_NAME\")" settings.gradle.kts; then
    echo "include(\":feat:$FEATURE_NAME\")" >> settings.gradle.kts
    echo "✅ Added to settings.gradle.kts"
fi

# 10. Inject to app/build.gradle.kts
if ! grep -q "implementation(projects.feat.$FEATURE_NAME)" app/build.gradle.kts; then
    # We use sed to insert it right after the existing feat.home dependency (or just inside dependencies block)
    # Using perl for cross-platform reliability to match the dependencies block
    perl -0777 -pi -e "s/(dependencies\s*\{[^\}]+)(implementation\(projects\.feat.*?\)|\n\s*\})/\$1implementation(projects.feat.$FEATURE_NAME)\n    \$2/" app/build.gradle.kts
    echo "✅ Added to app/build.gradle.kts"
fi

echo ""
echo "🎉 Module 'feat:$FEATURE_NAME' created successfully!"
echo "👉 Next step: Click 'Sync Project with Gradle Files' in Android Studio."
EOF
