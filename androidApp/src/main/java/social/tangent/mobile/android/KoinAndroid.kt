package social.tangent.mobile.android

import android.annotation.SuppressLint
import android.content.Context
import coil.ImageLoader
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
                    add(ImageDecoderDecoder.Factory())
                    add(SvgDecoder.Factory())
                    add(VideoFrameDecoder.Factory())
                }.memoryCache {
                    MemoryCache.Builder(get())
                        .maxSizePercent(0.5)
                        .build()
                }.diskCache {
                    DiskCache.Builder()
                        .directory(get<Context>().cacheDir.resolve("image_cache"))
                        .maxSizePercent(0.01)
                        .build()
                }
                .build()
            }

        }
    )
}
