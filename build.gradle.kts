plugins {
    val androidPlugin = "7.4.0-rc03"
    val kotlinPlugin = "1.7.20"
    // trick: for the same plugin versions in all sub-modules
    id("com.android.application").version(androidPlugin).apply(false)
    id("com.android.library").version(androidPlugin).apply(false)
    // id("org.jmailen.kotlinter").version("3.12.0").apply(false)
    kotlin("android").version(kotlinPlugin
    ).apply(false)
    kotlin("multiplatform").version(kotlinPlugin).apply(false)
    kotlin("jvm").version(kotlinPlugin).apply(false)
    id("com.google.devtools.ksp") version "1.7.20-1.0.7" apply false
    id("com.squareup.sqldelight") version "1.5.4" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
