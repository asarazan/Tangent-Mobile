package social.tangent.mobile

import platform.UIKit.UIDevice
import social.tangent.mobile.api.entities.Status

class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun launchWebView(url: String, useSystemBrowser: Boolean) {
    // TODO
}
actual fun shareStatus(status: Status) {
    // TODO
}
