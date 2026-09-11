package configs

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project

enum class FlavorDimension {
    environment
}

enum class AppFlavor(val dimension: FlavorDimension, val applicationIdSuffix: String? = null) {
    dev(FlavorDimension.environment, ".dev"),
    staging(FlavorDimension.environment, ".staging"),
    prod(FlavorDimension.environment, null)
}

fun configureApplicationFlavors(
    extension: ApplicationExtension
) {
    extension.apply {
        flavorDimensions += FlavorDimension.environment.name
        productFlavors {
            AppFlavor.values().forEach { flavor ->
                create(flavor.name) {
                    dimension = flavor.dimension.name
                    if (flavor.applicationIdSuffix != null) {
                        applicationIdSuffix = flavor.applicationIdSuffix
                    }
                    val baseUrl = when (flavor) {
                        AppFlavor.dev -> "\"https://api.dev.example.com/\""
                        AppFlavor.staging -> "\"https://api.staging.example.com/\""
                        AppFlavor.prod -> "\"https://api.example.com/\""
                    }
                    buildConfigField("String", "BASE_URL", baseUrl)
                }
            }
        }
    }
}

fun configureLibraryFlavors(
    extension: LibraryExtension
) {
    extension.apply {
        flavorDimensions += FlavorDimension.environment.name
        productFlavors {
            AppFlavor.values().forEach { flavor ->
                create(flavor.name) {
                    dimension = flavor.dimension.name
                    val baseUrl = when (flavor) {
                        AppFlavor.dev -> "\"https://api.dev.example.com/\""
                        AppFlavor.staging -> "\"https://api.staging.example.com/\""
                        AppFlavor.prod -> "\"https://api.example.com/\""
                    }
                    buildConfigField("String", "BASE_URL", baseUrl)
                }
            }
        }
    }
}
