plugins {
    alias(libs.plugins.convention.android.application)
}

android {
    namespace = "com.gosty.android_project_template"

    defaultConfig {
        applicationId = "com.gosty.android_project_template"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }
}

dependencies {
    implementation(projects.common)
    implementation(projects.core.domain)
}