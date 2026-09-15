// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room.db) apply false
}
tasks.register("installGitHooks", Copy::class) {
    from(File(rootProject.rootDir, "scripts/git-hooks"))
    into(File(rootProject.rootDir, ".git/hooks"))
    filePermissions { unix("rwxr-xr-x") }
}


gradle.projectsEvaluated {
    tasks.getByPath(":app:preBuild").dependsOn(tasks.named("installGitHooks"))
}
