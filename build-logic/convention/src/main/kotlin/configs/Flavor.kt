package configs

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension

enum class FlavorDimension {
    Environment
}

enum class AppFlavor(val dimension: FlavorDimension, val applicationIdSuffix: String? = null) {
    Dev(FlavorDimension.Environment, ".dev"),
    Staging(FlavorDimension.Environment, ".staging"),
    Prod(FlavorDimension.Environment, null)
}

fun configureApplicationFlavors(
    extension: ApplicationExtension
) {
    extension.apply {
        flavorDimensions += FlavorDimension.Environment.name
        productFlavors {
            AppFlavor.values().forEach { flavor ->
                create(flavor.name) {
                    dimension = flavor.dimension.name
                    if (flavor.applicationIdSuffix != null) {
                        applicationIdSuffix = flavor.applicationIdSuffix
                    }
                    val baseUrl = when (flavor) {
                        AppFlavor.Dev -> "\"https://api.dev.example.com/\""
                        AppFlavor.Staging -> "\"https://api.staging.example.com/\""
                        AppFlavor.Prod -> "\"https://api.example.com/\""
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
        flavorDimensions += FlavorDimension.Environment.name
        productFlavors {
            AppFlavor.values().forEach { flavor ->
                create(flavor.name) {
                    dimension = flavor.dimension.name
                    val baseUrl = when (flavor) {
                        AppFlavor.Dev -> "\"https://api.dev.example.com/\""
                        AppFlavor.Staging -> "\"https://api.staging.example.com/\""
                        AppFlavor.Prod -> "\"https://api.example.com/\""
                    }
                    buildConfigField("String", "BASE_URL", baseUrl)
                }
            }
        }
    }
}
