package social.tangent.mobile.sdk.extensions

import social.tangent.mobile.api.mock.MockApi
import social.tangent.mobile.sdk.Mastodon
import social.tangent.mobile.sdk.MastodonServer

suspend fun Mastodon.Companion.createMockTimeline(delay: Long = 0L): MastodonServer {
    return MastodonServer.acquire("mastodon.social", MockApi(delay))
}
