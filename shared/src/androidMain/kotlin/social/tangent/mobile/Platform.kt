package social.tangent.mobile

import android.app.Application
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object koiner : KoinComponent

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun launchWebView(url: String, useSystemBrowser: Boolean) {
    val context: Application = koiner.get()
    if (useSystemBrowser) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).addFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(browserIntent)
        return
    }
    val chrome = CustomTabsIntent.Builder().build()
    chrome.launchUrl(context, Uri.parse(url))
}
