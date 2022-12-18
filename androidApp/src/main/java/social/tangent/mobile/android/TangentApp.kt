package social.tangent.mobile.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Build
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.decode.VideoFrameDecoder
import coil.util.DebugLogger
import org.koin.dsl.module

// gross hack.
lateinit var imageLoader: ImageLoader
    private set

class TangentApp : Application() {

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate() {
        super.onCreate()
        imageLoader = ImageLoader.Builder(this@TangentApp)
            .logger(DebugLogger())
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
                add(SvgDecoder.Factory())
                add(VideoFrameDecoder.Factory())
            }
            .build()
        initKoinAndroid(
            module {
                single<Context> { this@TangentApp }
                single<Application> { this@TangentApp }
            }
        )
    }
}
