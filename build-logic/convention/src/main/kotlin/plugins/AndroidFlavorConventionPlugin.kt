package plugins

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import configs.configureApplicationFlavors
import configs.configureLibraryFlavors
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType

class AndroidFlavorConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.findByType<ApplicationExtension>()?.let {
                configureApplicationFlavors(it)
            }
            extensions.findByType<LibraryExtension>()?.let {
                configureLibraryFlavors(it)
            }
        }
    }
}
