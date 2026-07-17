import org.gradle.initialization.DependenciesAccessors
import org.gradle.kotlin.dsl.support.serviceOf
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.gosty.buildlogic"

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)

    gradle.serviceOf<DependenciesAccessors>().classes.asFiles.forEach { file ->
        compileOnly(files(file.absolutePath))
    }
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "convention.android.application"
            implementationClass = "modules.AndroidApplicationModuleConventionPlugin"
        }

        register("androidLibrary") {
            id = "convention.android.library"
            implementationClass = "plugins.AndroidLibraryConventionPlugin"
        }
        register("composeLibrary") {
            id = "convention.compose.library"
            implementationClass = "plugins.ComposeLibraryConventionPlugin"
        }
        register("hiltLibrary") {
            id = "convention.hilt"
            implementationClass = "plugins.HiltConventionPlugin"
        }
    }
}