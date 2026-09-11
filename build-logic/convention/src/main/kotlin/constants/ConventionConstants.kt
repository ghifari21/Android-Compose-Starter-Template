package constants

import ext.androidTestImplementation
import ext.debugImplementation
import ext.implementation
import ext.libs
import ext.releaseImplementation
import ext.testImplementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal object ConventionConstants {
    val coreModules = listOf(
        ":core:data",
        ":core:domain",
        ":core:model",
        ":core:navigation",
        ":common"
    )
    val resourceExcludes = listOf(
        "/META-INF/{AL2.0,LGPL2.1}",
        "/META-INF/gradle/incremental.annotation.processors"
    )
    const val BASE_NAME = "com.project.starter"
    const val MIN_SDK_VERSION = 28
    const val MAX_SDK_VERSION = 37
    const val KSP = "ksp"
    const val FREE_COMPILER = "-opt-in=kotlin.RequiresOptIn"

    fun Project.injectComposeDependencies() {
        dependencies {
            val bom = libs.androidx.compose.bom.get()
            implementation(platform(bom))
            implementation(libs.androidx.activity.compose.get())
            implementation(libs.androidx.appcompat.get())
            implementation(libs.androidx.compose.material3.get())
            implementation(libs.androidx.compose.ui.tooling.preview.get())
            debugImplementation(libs.androidx.compose.ui.tooling.debug.get())
            implementation(libs.androidx.core.ktx.get())
            implementation(libs.coil.compose.get())
            implementation(libs.coil.network.get())
            implementation(libs.coil.video.get())
            implementation(libs.timber.get())
            implementation(libs.material.icons.core.get())
            implementation(libs.material.icons.extended.get())
            implementation(libs.androidx.compose.ui.text.google.fonts.get())
        }
    }

    fun Project.injectDataDependencies() {
        dependencies {
            implementation(project(coreModules.last()))
            implementation(project(coreModules[1]))
            implementation(project(coreModules[2]))
            implementation(libs.datastore.preferences.get())
            implementation(libs.security.crypto.get())
            implementation(libs.okhttp.core.get())
            implementation(libs.okhttp.logging.interceptor.get())
            implementation(libs.retrofit.core.get())
            implementation(libs.retrofit.converter.kotlinx.serialization.get())
            implementation(libs.room.runtime.get())
            implementation(libs.timber.get())
            implementation(libs.kotlinx.coroutines.android.get())
            implementation(libs.kotlinx.serialization.json.get())
            add(KSP, libs.room.compiler.get())
            debugImplementation(libs.chucker.debug.get())
            releaseImplementation(libs.chucker.release.get())
        }
    }

    fun Project.injectTestDependencies() {
        dependencies {
            testImplementation(project(":core:testing"))
            testImplementation(libs.junit.get())
            testImplementation(libs.mockk.get())
            testImplementation(libs.turbine.get())
            testImplementation(libs.kotlinx.coroutines.test.get())
            androidTestImplementation(libs.androidx.junit.get())
            androidTestImplementation(libs.androidx.espresso.core.get())
        }
    }
}
