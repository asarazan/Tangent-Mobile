package social.tangent.mobile.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

class TangentApp : Application(), KoinComponent, ImageLoaderFactory {

    private lateinit var koin: KoinApplication

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate() {
        super.onCreate()
        koin = initKoinAndroid(
            module {
                single<Context> { this@TangentApp }
                single<Application> { this@TangentApp }
            }
        )
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun newImageLoader(): ImageLoader {
        return get()
    }
}
