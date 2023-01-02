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
import social.tangent.mobile.data.DriverFactory
import social.tangent.mobile.data.createDatabase
import social.tangent.mobile.sdk.storage.MastodonStorage

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
        MastodonStorage.all().firstOrNull()?.let {
            val id = it.id
            val db = createDatabase(id, DriverFactory(this))
            println("Created database!!! ${db}")
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun newImageLoader(): ImageLoader {
        return get()
    }
}
