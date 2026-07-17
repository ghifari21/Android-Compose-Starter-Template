package plugins

import com.android.build.api.dsl.LibraryExtension
import configs.configJetpackCompose
import ext.alias
import ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class ComposeLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.alias(libs.plugins.android.library)
            extensions.configure<LibraryExtension> { configJetpackCompose(this) }
        }
    }
}