plugins {
    // trick: for the same plugin versions in all sub-modules
    id("com.android.application").version(Versions.androidPlugin).apply(false)
    id("com.android.library").version(Versions.androidPlugin).apply(false)
    // id("org.jmailen.kotlinter").version(buildsrc.Versions.kotlinter).apply(false)
    kotlin("android").version(Versions.kotlin).apply(false)
    kotlin("multiplatform").version(Versions.kotlin).apply(false)
    kotlin("jvm").version(Versions.kotlin).apply(false)
    id("com.google.devtools.ksp") version Versions.ksp apply false
    id("com.squareup.sqldelight") version Versions.sqldelight apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
