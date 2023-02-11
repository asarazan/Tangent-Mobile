object Versions {
    const val kotlin = "1.7.20"
    const val androidPlugin = "7.4.1"
    const val ksp = "1.7.20-1.0.7"
    const val sqldelight = "1.5.4"
    const val kotlinter = "3.12.0"

    const val ktor = "2.1.3"
    const val koin = "3.2.2"
    const val koinAndroid = "3.3.0"
    const val koinAndroidCompose = koinAndroid
    const val koinKtor = koin
    const val kotlinSerialization = "1.4.1"
    const val ktorfit = "1.0.0-beta16"
    const val coroutinesCore = "1.6.4"
    const val kermit = "1.2.2"
    const val kotlinDatetime = "0.4.0"
    const val multiplatformSettings = "1.0.0-RC"
    const val stately = "1.2.0"

    const val browser = "1.4.0"
    const val lifecycleViewModelCompose = "2.5.1"

    const val androidCompileSdk = 33
    const val androidMinSdk = 26
    const val androidTargetSdk = androidCompileSdk
    const val kotlinCompilerExt = "1.3.2"

    const val activityCompose = "1.6.1"
    const val composeUi = "1.3.2"
    const val composeUiTooling = composeUi
    const val composeUiToolingPreview = composeUi
    const val composeFoundation = "1.3.1"
    const val composeMaterial = composeFoundation
    const val coroutinesAndroid = "1.6.4"
    const val lottieCompose = "5.2.0"
    const val coil = "2.2.2"
    const val takt = "2.1.1"
    const val glideCompose = "1.0.0-alpha.1"
    const val jsoup = "1.15.3"
    const val toolbarCompose = "2.3.5"
    const val composeScreenshot = "1.0.3"
    const val junit = "4.13.2"
    const val androidXTesting = "1.5.0"
}

object Deps {
    // commonMain
    const val kotlinSerializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinSerialization}"
    const val ktorClientCore = "io.ktor:ktor-client-core:${Versions.ktor}"
    const val ktorClientContentNegotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
    const val ktorSerializationJson = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
    const val ktorClientLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
    const val ktorClientAuth = "io.ktor:ktor-client-auth:${Versions.ktor}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
    const val kermit = "co.touchlab:kermit:${Versions.kermit}"
    const val koinCore = "io.insert-koin:koin-core:${Versions.koin}"
    const val kotlinDatetime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinDatetime}"
    const val ktorfit = "de.jensklingenberg.ktorfit:ktorfit-lib:${Versions.ktorfit}"
    const val multiplatformSettings = "com.russhwolf:multiplatform-settings:${Versions.multiplatformSettings}"
    const val sqldelightCoroutinesExt = "com.squareup.sqldelight:coroutines-extensions:${Versions.sqldelight}"
    const val statelyIso = "co.touchlab:stately-isolate:${Versions.stately}"
    const val statelyConcurrency = "co.touchlab:stately-concurrency:${Versions.stately}"

    // commonTest
    const val koinTest = "io.insert-koin:koin-test:${Versions.koin}"

    // androidMain
    const val browser = "androidx.browser:browser:${Versions.browser}"
    const val ktorOkhttp = "io.ktor:ktor-client-okhttp:${Versions.ktor}"
    const val koinAndroid = "io.insert-koin:koin-android:${Versions.koinAndroid}"
    const val koinAndroidCompat = "io.insert-koin:koin-android-compat:${Versions.koinAndroid}"
    const val koinWorkManager = "io.insert-koin:koin-androidx-workmanager:${Versions.koinAndroid}"
    const val koinNavigation = "io.insert-koin:koin-androidx-navigation:${Versions.koinAndroid}"
    const val koinCompose = "io.insert-koin:koin-androidx-compose:${Versions.koinAndroidCompose}"
    const val koinKtor = "io.insert-koin:koin-ktor:${Versions.koinKtor}"
    const val lifecycleViewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycleViewModelCompose}"
    const val sqldelightAndroidDriver = "com.squareup.sqldelight:android-driver:${Versions.sqldelight}"

    // iosMain
    const val ktorDarwin = "io.ktor:ktor-client-darwin:${Versions.ktor}"
    const val sqldelightNativeDriver = "com.squareup.sqldelight:native-driver:${Versions.sqldelight}"

    // androidApp
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val composeUi = "androidx.compose.ui:ui:${Versions.composeUi}"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.composeUiTooling}"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.composeUiToolingPreview}"
    const val composeFoundation = "androidx.compose.foundation:foundation:${Versions.composeFoundation}"
    const val composeMaterial = "androidx.compose.material:material:${Versions.composeMaterial}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
    const val lottieCompose = "com.airbnb.android:lottie-compose:${Versions.lottieCompose}"
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val coilCompose = "io.coil-kt:coil-compose:${Versions.coil}"
    const val coilGif = "io.coil-kt:coil-gif:${Versions.coil}"
    const val coilSvg = "io.coil-kt:coil-svg:${Versions.coil}"
    const val coilVideo = "io.coil-kt:coil-video:${Versions.coil}"
    const val takt = "jp.wasabeef:takt:${Versions.takt}"
    const val glideCompose = "com.github.bumptech.glide:compose:${Versions.glideCompose}"
    const val jsoup = "org.jsoup:jsoup:${Versions.jsoup}"
    const val toolbarCompose = "me.onebone:toolbar-compose:${Versions.toolbarCompose}"
    const val composeScreenshot = "com.github.SmartToolFactory:Compose-Screenshot:${Versions.composeScreenshot}"
    const val junit = "junit:junit:${Versions.junit}"
    const val androidXTesting = "androidx.test:core:${Versions.androidXTesting}"
}