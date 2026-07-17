package modules

import com.android.build.api.dsl.ApplicationExtension
import configs.configAndroid
import configs.configJetpackCompose
import constants.ConventionConstants
import ext.alias
import ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                alias(libs.plugins.android.application)
                alias(libs.plugins.kotlin.compose)
                alias(libs.plugins.convention.hilt)
            }

            extensions.configure<ApplicationExtension> {
                configAndroid(this)
                configJetpackCompose(this)
                defaultConfig.targetSdk = ConventionConstants.MAX_SDK_VERSION
                packaging {
                    resources.excludes.addAll(ConventionConstants.resourceExcludes)
                }
            }
        }
    }
}