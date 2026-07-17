package configs

import com.android.build.api.dsl.LibraryExtension
import ext.androidTestImplementation
import ext.debugImplementation
import ext.implementation
import ext.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configJetpackCompose(libraryExtension: LibraryExtension) {
    libraryExtension.apply {
        buildFeatures {
            compose = true
        }

    }
}