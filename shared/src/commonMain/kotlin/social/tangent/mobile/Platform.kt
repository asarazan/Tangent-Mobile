package social.tangent.mobile

import social.tangent.mobile.api.entities.Status

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun launchWebView(url: String, useSystemBrowser: Boolean = true)
expect fun shareStatus(status: Status)
