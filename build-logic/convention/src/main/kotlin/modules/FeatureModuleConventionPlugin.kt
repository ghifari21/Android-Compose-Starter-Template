package modules

import constants.ConventionConstants
import ext.alias
import ext.implementation
import ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class FeatureModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                alias(libs.plugins.convention.android.library)
                alias(libs.plugins.convention.compose.library)
                alias(libs.plugins.convention.navigation)
            }

            dependencies {
                ConventionConstants.coreModules.drop(1).forEach { module ->
                    implementation(project(module))
                }
            }
        }
    }
}