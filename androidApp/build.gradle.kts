plugins {
    id("com.android.application")
    // id("org.jmailen.kotlinter")
    kotlin("android")
}

android {
    namespace = "social.tangent.mobile.android"
    compileSdk = Versions.androidCompileSdk
    defaultConfig {
        applicationId = "social.tangent.mobile.android"
        minSdk = Versions.androidMinSdk
        targetSdk = Versions.androidTargetSdk
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.kotlinCompilerExt
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    compileOptions {
        // sourceCompatibility = JavaVersion.VERSION_17
        // targetCompatibility = JavaVersion.VERSION_17
        // isCoreLibraryDesugaringEnabled = true
    }
    buildTypes {
        getByName("debug") {
            // Enables code shrinking, obfuscation, and optimization for only
            // your project's release build type.
            isMinifyEnabled = false

            // Enables resource shrinking, which is performed by the
            // Android Gradle plugin.
            isShrinkResources = false

            // Includes the default ProGuard rules files that are packaged with
            // the Android Gradle plugin. To learn more, go to the section about
            // R8 configuration files.
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
            // Enables code shrinking, obfuscation, and optimization for only
            // your project's release build type.
            isMinifyEnabled = true

            // Enables resource shrinking, which is performed by the
            // Android Gradle plugin.
            isShrinkResources = true

            // Includes the default ProGuard rules files that are packaged with
            // the Android Gradle plugin. To learn more, go to the section about
            // R8 configuration files.
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(Deps.activityCompose)
    implementation(Deps.composeUi)
    implementation(Deps.composeUiTooling)
    implementation(Deps.composeUiToolingPreview)
    implementation(Deps.composeFoundation)
    implementation(Deps.composeMaterial)
    implementation(Deps.coroutinesAndroid)
    implementation(Deps.lottieCompose)
    implementation(Deps.coil)
    implementation(Deps.coilCompose)
    implementation(Deps.coilGif)
    implementation(Deps.coilSvg)
    implementation(Deps.coilVideo)
    implementation(Deps.takt)
    implementation(Deps.glideCompose)
    implementation(Deps.jsoup)
    implementation(Deps.toolbarCompose)
    implementation(Deps.composeScreenshot)

    // tests
    testImplementation(Deps.junit)
    testImplementation(Deps.androidXTesting)
}