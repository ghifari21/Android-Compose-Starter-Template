package constants

internal object ConventionConstants {
    val commonModule = ":common"
    val coreModules = listOf(
        ":core:data",
        ":core:domain",
        ":core:models",
        ":core:navigation"
    )
    val resourceExcludes = listOf(
        "/META-INF/{AL2.0,LGPL2.1}",
        "/META-INF/gradle/incremental.annotation.processors"
    )
    const val BASE_NAME = "com.project.starter"
    const val MIN_SDK_VERSION = 28
    const val MAX_SDK_VERSION = 35
    const val KSP = "ksp"
    const val FREE_COMPILER = "-opt-in=kotlin.RequiresOptIn"
}