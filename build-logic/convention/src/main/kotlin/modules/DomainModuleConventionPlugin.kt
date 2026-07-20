package modules

import ext.alias
import ext.implementation
import ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class DomainModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.alias(libs.plugins.convention.android.library)

            dependencies {
                implementation(libs.kotlinx.coroutines.android)
            }
        }
    }
}