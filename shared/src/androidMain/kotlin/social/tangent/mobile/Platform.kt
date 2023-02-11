package social.tangent.mobile

import android.app.Application
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import social.tangent.mobile.api.entities.Status

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

actual fun shareStatus(status: Status) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, status.url ?: status.uri)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, status.account.username)
    koiner.get<android.content.Context>().startActivity(shareIntent)
}

actual val BackgroundDispatcher: CoroutineDispatcher
    get() = Dispatchers.IO