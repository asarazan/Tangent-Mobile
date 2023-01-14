
plugins {
    // trick: for the same plugin versions in all sub-modules
    id("com.android.application").version(buildsrc.Versions.androidPlugin).apply(false)
    id("com.android.library").version(buildsrc.Versions.androidPlugin).apply(false)
    // id("org.jmailen.kotlinter").version(buildsrc.Versions.kotlinter).apply(false)
    kotlin("android").version(buildsrc.Versions.kotlin).apply(false)
    kotlin("multiplatform").version(buildsrc.Versions.kotlin).apply(false)
    kotlin("jvm").version(buildsrc.Versions.kotlin).apply(false)
    id("com.google.devtools.ksp") version buildsrc.Versions.ksp apply false
    id("com.squareup.sqldelight") version buildsrc.Versions.sqldelight apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
