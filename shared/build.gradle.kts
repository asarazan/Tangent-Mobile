val ktorfitVersion = "1.0.0-beta16"

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization") version "1.7.20"
    id("com.android.library")
    id("com.google.devtools.ksp") version "1.7.20-1.0.7"
    id("org.jmailen.kotlinter")
}

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val ktor_version = "2.1.3"
        val koin_version = "3.2.2"
        val koin_android_version = "3.3.0"
        val koin_android_compose_version = "3.3.0"
        val koin_ktor = "3.2.2"

        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
                implementation("io.ktor:ktor-client-logging:$ktor_version")
                implementation("io.ktor:ktor-client-auth:$ktor_version")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("co.touchlab:kermit:1.1.3") // Add latest version
                api("io.insert-koin:koin-core:$koin_version")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("de.jensklingenberg.ktorfit:ktorfit-lib:$ktorfitVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("io.insert-koin:koin-test:$koin_version")
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.browser:browser:1.4.0")
                implementation("io.ktor:ktor-client-okhttp:$ktor_version")
                api("io.insert-koin:koin-android:$koin_android_version")
                implementation("io.insert-koin:koin-android-compat:$koin_android_version")
                implementation("io.insert-koin:koin-androidx-workmanager:$koin_android_version")
                implementation("io.insert-koin:koin-androidx-navigation:$koin_android_version")
                implementation("io.insert-koin:koin-androidx-compose:$koin_android_compose_version")
                implementation("io.insert-koin:koin-ktor:$koin_ktor")
                api("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:$ktor_version")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "social.tangent.mobile"
    compileSdk = 32
    defaultConfig {
        minSdk = 24
        targetSdk = 32
    }
}

dependencies {
    add("kspCommonMainMetadata", "de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")
    add("kspAndroid", "de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")
    add("kspIosX64", "de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")
    add("kspIosSimulatorArm64", "de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")
}