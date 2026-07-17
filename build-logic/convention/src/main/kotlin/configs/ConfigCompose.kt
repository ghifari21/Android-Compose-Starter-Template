package configs

import com.android.build.api.dsl.CommonExtension
import constants.ConventionConstants.injectComposeDependencies
import org.gradle.api.Project

internal fun Project.configJetpackCompose(extension: CommonExtension) {
    extension.apply {
        buildFeatures.compose = true
        injectComposeDependencies()
    }
}
