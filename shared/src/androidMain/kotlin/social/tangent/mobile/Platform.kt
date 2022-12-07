package social.tangent.mobile

import android.app.Application
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object koiner : KoinComponent

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun launchWebView(url: String) {
    val chrome = CustomTabsIntent.Builder().build()
    val context: Application = koiner.get()
    chrome.launchUrl(context, Uri.parse(url))
}
