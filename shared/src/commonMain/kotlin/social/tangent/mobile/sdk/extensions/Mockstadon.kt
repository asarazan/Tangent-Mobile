package social.tangent.mobile.sdk.extensions

import social.tangent.mobile.api.mock.MockApi
import social.tangent.mobile.sdk.Mastodon

fun Mastodon.Companion.createMockTimeline(): Mastodon {
    return Mastodon(MockApi(), "mastodon.social")
}