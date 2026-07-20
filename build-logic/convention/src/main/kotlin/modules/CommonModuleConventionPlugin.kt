package modules

import ext.alias
import ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class CommonModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                alias(libs.plugins.convention.android.library)
                alias(libs.plugins.convention.compose.library)
            }
        }
    }
}