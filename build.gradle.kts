plugins {
    // trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("7.3.1").apply(false)
    id("com.android.library").version("7.3.1").apply(false)
    id("org.jmailen.kotlinter").version("3.12.0").apply(false)
    kotlin("android").version("1.7.20").apply(false)
    kotlin("multiplatform").version("1.7.20").apply(false)
    kotlin("jvm").version("1.7.20").apply(false)
    id("com.google.devtools.ksp") version "1.7.20-1.0.7" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
