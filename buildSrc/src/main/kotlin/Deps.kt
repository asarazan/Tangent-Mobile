object Versions {
    const val kotlin = "1.7.20"
    const val androidPlugin = "7.4.0"
    const val ksp = "1.7.20-1.0.7"
    const val sqldelight = "1.5.4"
    const val kotlinter = "3.12.0"

    const val ktor = "2.1.3"
    const val koin = "3.2.2"
    const val koinAndroid = "3.3.0"
    const val koinAndroidCompose = koinAndroid
    const val kotlinSerialization = "1.4.1"
    const val ktorfit = "1.0.0-beta16"
    const val coroutinesCore = "1.6.4"
    const val kermit = "1.2.2"
    const val kotlinDatetime = "0.4.0"
    const val multiplatformSettings = "1.0.0-RC"

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
}

object Deps {
    // commonMain
    val kotlinSerializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinSerialization}"
    val ktorClientCore = "io.ktor:ktor-client-core:${Versions.ktor}"
    val ktorClientContentNegotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
    val ktorSerializationJson = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
    val ktorClientLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
    val ktorClientAuth = "io.ktor:ktor-client-auth:${Versions.ktor}"
    val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
    val kermit = "co.touchlab:kermit:${Versions.kermit}"
    val koinCore = "io.insert-koin:koin-core:${Versions.koin}"
    val kotlinDatetime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinDatetime}"
    val ktorfit = "de.jensklingenberg.ktorfit:ktorfit-lib:${Versions.ktorfit}"
    val multiplatformSettings = "com.russhwolf:multiplatform-settings:${Versions.multiplatformSettings}"
    val sqldelightCoroutinesExt = "com.squareup.sqldelight:coroutines-extensions:${Versions.sqldelight}"

    // commonTest
    val koinTest = "io.insert-koin:koin-test:${Versions.koin}"

    // androidMain
    val browser = "androidx.browser:browser:${Versions.browser}"
    val ktorOkhttp = "io.ktor:ktor-client-okhttp:${Versions.ktor}"
    val koinAndroid = "io.insert-koin:koin-android:${Versions.koinAndroid}"
    val koinAndroidCompat = "io.insert-koin:koin-android-compat:${Versions.koinAndroid}"
    val koinWorkManager = "io.insert-koin:koin-androidx-workmanager:${Versions.koin}"
    val koinNavigation = "io.insert-koin:koin-androidx-navigation:${Versions.koin}"
    val koinCompose = "io.insert-koin:koin-androidx-compose:${Versions.koinAndroidCompose}"
    val koinKtor = "io.insert-koin:koin-ktor:${Versions.koin}"
    val lifecycleViewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycleViewModelCompose}"
    val sqldelightAndroidDriver = "com.squareup.sqldelight:android-driver:${Versions.sqldelight}"

    // iosMain
    val ktorDarwin = "io.ktor:ktor-client-darwin:${Versions.ktor}"
    val sqldelightNativeDriver = "com.squareup.sqldelight:native-driver:${Versions.sqldelight}"

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
}