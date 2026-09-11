plugins {
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.project.testing"
}

dependencies {
    api(libs.junit)
    api(libs.kotlinx.coroutines.test)
    api(libs.mockk)
    api(libs.turbine)
}
