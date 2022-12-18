package social.tangent.mobile.sdk.extensions

import social.tangent.mobile.api.mock.MockApi
import social.tangent.mobile.sdk.Mastodon

fun Mastodon.Companion.createMockTimeline(delay: Long = 0L): Mastodon {
    return Mastodon(MockApi(delay), "mastodon.social")
}
