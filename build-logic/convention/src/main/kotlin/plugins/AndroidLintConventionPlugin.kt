package plugins

import com.diffplug.gradle.spotless.SpotlessExtension
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLintConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.diffplug.spotless")
            pluginManager.apply("io.gitlab.arturbosch.detekt")

            extensions.configure<SpotlessExtension> {
                kotlin {
                    target("**/*.kt")
                    targetExclude("**/build/**/*.kt")
                    ktlint()
                }
                kotlinGradle {
                    target("**/*.kts")
                    targetExclude("**/build/**/*.kts")
                    ktlint()
                }
            }

            extensions.configure<DetektExtension> {
                toolVersion = "1.23.6"
                buildUponDefaultConfig = true
            }

            dependencies.add("detektPlugins", "io.gitlab.arturbosch.detekt:detekt-formatting:1.23.6")
        }
    }
}
