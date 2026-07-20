package modules

import constants.ConventionConstants.injectDataDependencies
import ext.alias
import ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.Actions.with

class DataModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                alias(libs.plugins.convention.android.library)
                alias(libs.plugins.room.db)
            }

            injectDataDependencies()
        }
    }
}