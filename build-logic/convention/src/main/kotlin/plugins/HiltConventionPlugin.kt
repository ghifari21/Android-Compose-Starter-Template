package plugins

import constants.ConventionConstants.KSP
import ext.alias
import ext.implementation
import ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                alias(libs.plugins.hilt)
                alias(libs.plugins.ksp)
            }

            dependencies {
                implementation(libs.hilt.android.get())
                implementation(libs.hilt.navigation.compose.get())
                add(KSP, libs.hilt.compiler.get())
                add(KSP, libs.hilt.android.get())
                add(KSP, libs.kotlin.metadataJvm.get())
            }
        }
    }
}