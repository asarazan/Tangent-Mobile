package social.tangent.mobile.android

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.koin.core.module.Module
import org.koin.dsl.module
import social.tangent.mobile.initKoin
import social.tangent.mobile.sdk.Mastodon
import social.tangent.mobile.sdk.extensions.createMockTimeline

@OptIn(DelicateCoroutinesApi::class) // temporary
fun initKoinAndroid(appModule: Module) {
    val app = initKoin(appModule)
    app.modules(
        module {
            single { GlobalScope.async { Mastodon.createMockTimeline() } } // temporary
        }
    )
    // TODO
}
