package modules

import ext.alias
import ext.implementation
import ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class ModelModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                alias(libs.plugins.convention.android.library)
                alias(libs.plugins.kotlin.serialization)
                alias(libs.plugins.kotlin.parcelize)
            }

            dependencies {
                implementation(libs.androidx.core.ktx.get())
                implementation(libs.kotlinx.serialization.json.get())
                implementation(libs.room.runtime.get())
                implementation(libs.room.ktx.get())
            }
        }
    }
}
