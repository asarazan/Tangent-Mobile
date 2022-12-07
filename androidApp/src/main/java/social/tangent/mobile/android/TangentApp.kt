package social.tangent.mobile.android

import android.app.Application
import android.content.Context
import org.koin.dsl.module

class TangentApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoinAndroid(module {
            single<Context> { this@TangentApp }
            single<Application> { this@TangentApp }
        })
    }
}