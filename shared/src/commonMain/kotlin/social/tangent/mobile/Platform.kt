package social.tangent.mobile

import kotlinx.coroutines.CoroutineDispatcher
import social.tangent.mobile.api.entities.Status

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun launchWebView(url: String, useSystemBrowser: Boolean = true)
expect fun shareStatus(status: Status)

expect val BackgroundDispatcher: CoroutineDispatcher