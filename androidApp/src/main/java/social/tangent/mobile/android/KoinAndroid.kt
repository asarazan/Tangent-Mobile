package social.tangent.mobile.android

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.decode.VideoFrameDecoder
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import social.tangent.mobile.data.DriverFactory
import social.tangent.mobile.koin.initKoin

@SuppressLint("ObsoleteSdkInt")
fun initKoinAndroid(appModule: Module): KoinApplication {
    return initKoin(
        appModule,
        module {
            // single { GlobalScope.async { Mastodon.createMockTimeline(1000L) } } // temporary
            single<Settings.Factory> {
                SharedPreferencesSettings.Factory(get())
            }
            single {
                DriverFactory(get())
            }
            single { ImageLoader.Builder(get())
                // .logger(DebugLogger())
                .components {
                    if (Build.VERSION.SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                    add(SvgDecoder.Factory())
                    add(VideoFrameDecoder.Factory())
                }.memoryCache {
                    MemoryCache.Builder(get())
                        .maxSizePercent(0.25)
                        .build()
                }.diskCache {
                    DiskCache.Builder()
                        .directory(get<Context>().cacheDir.resolve("image_cache"))
                        .maxSizePercent(0.02)
                        .build()
                }
                .build()
            }

        }
    )
}
