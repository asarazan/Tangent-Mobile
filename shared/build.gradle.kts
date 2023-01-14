
plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization") version Versions.kotlin
    id("com.android.library")
    id("com.google.devtools.ksp") version Versions.ksp
    id("com.squareup.sqldelight") version Versions.sqldelight
    // id("org.jmailen.kotlinter") version Versions.kotlinter
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
                implementation(Deps.kotlinSerializationJson)
                implementation(Deps.ktorClientCore)
                implementation(Deps.ktorClientContentNegotiation)
                implementation(Deps.ktorSerializationJson)
                implementation(Deps.ktorClientLogging)
                implementation(Deps.ktorClientAuth)
                implementation(Deps.coroutinesCore)
                implementation(Deps.kermit)
                api(Deps.koinCore)
                api(Deps.kotlinDatetime)
                implementation(Deps.ktorfit)
                api(Deps.multiplatformSettings)
                implementation(Deps.sqldelightCoroutinesExt)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(Deps.koinTest)
            }
        }
        val androidMain by getting {
            dependencies {
                api(Deps.browser)
                implementation(Deps.ktorOkhttp)
                api(Deps.koinAndroid)
                implementation(Deps.ktorOkhttp)
                implementation(Deps.koinAndroid)
                implementation(Deps.koinWorkManager)
                implementation(Deps.koinNavigation)
                implementation(Deps.koinKtor)
                api(Deps.lifecycleViewModelCompose)
                implementation(Deps.sqldelightAndroidDriver)
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
                implementation(Deps.ktorDarwin)
                implementation(Deps.sqldelightNativeDriver)
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
    compileSdk = Versions.androidCompileSdk
    defaultConfig {
        minSdk = Versions.androidMinSdk
    }
}

dependencies {
    add("kspCommonMainMetadata", "de.jensklingenberg.ktorfit:ktorfit-ksp:${Versions.ktorfit}")
    add("kspAndroid", "de.jensklingenberg.ktorfit:ktorfit-ksp:${Versions.ktorfit}")
    add("kspIosX64", "de.jensklingenberg.ktorfit:ktorfit-ksp:${Versions.ktorfit}")
    add("kspIosSimulatorArm64", "de.jensklingenberg.ktorfit:ktorfit-ksp:${Versions.ktorfit}")
}

sqldelight {
    database("TangentDatabase") {
        packageName = "social.tangent.mobile"
    }
}
