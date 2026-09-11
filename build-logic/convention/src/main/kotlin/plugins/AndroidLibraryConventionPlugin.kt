package plugins

import com.android.build.api.dsl.LibraryExtension
import configs.configAndroid
import constants.ConventionConstants.MAX_SDK_VERSION
import constants.ConventionConstants.injectTestDependencies
import ext.alias
import ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                alias(libs.plugins.android.library)
                alias(libs.plugins.kotlin.compose)
                alias(libs.plugins.convention.hilt)
                alias(libs.plugins.convention.lint)
            }

            extensions.configure<LibraryExtension> {
                configAndroid(this)
                testOptions.targetSdk = MAX_SDK_VERSION
                lint.targetSdk = MAX_SDK_VERSION
            }

            injectTestDependencies()
        }
    }
}
