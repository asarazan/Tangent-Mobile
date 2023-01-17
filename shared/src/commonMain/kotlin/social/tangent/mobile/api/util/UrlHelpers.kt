package social.tangent.mobile.api.util

import io.ktor.http.Url

data class StatusLink(
    val profile: String,
    val status: String
)

object UrlHelpers {
    private val profileRegex = Regex("@([\\w-]+)")
    fun statusFromUrl(urlString: String): StatusLink? {
        val url = Url(urlString)
        val segments = url.pathSegments
        if (segments.count() < 2) return null
        val status = segments.lastOrNull()?.toLongOrNull() ?: return null
        val profile = segments[segments.lastIndex - 1]
        if (profileRegex.matches(profile)) {
            return StatusLink(profile, "$status")
        }
        return null
    }
}