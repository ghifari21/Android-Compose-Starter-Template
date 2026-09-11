package configs

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import constants.ConventionConstants
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configAndroid(extension: CommonExtension) {
    extension.apply {
        namespace =
            "${ConventionConstants.BASE_NAME}.${project.path.replace(":", ".").substring(1)}"
        compileSdk = ConventionConstants.MAX_SDK_VERSION
        
        defaultConfig.apply {
            minSdk = ConventionConstants.MIN_SDK_VERSION
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            if (extension is LibraryExtension) {
                if (project.file("consumer-rules.pro").exists()) {
                    extension.defaultConfig.consumerProguardFiles("consumer-rules.pro")
                }
            }
        }
        
        buildFeatures.buildConfig = true
        
        buildTypes.getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        
        compileOptions.apply {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }
    configureKotlinCompiler()
}

private fun Project.configureKotlinCompiler() {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            freeCompilerArgs.add(ConventionConstants.FREE_COMPILER)
        }
    }
}
