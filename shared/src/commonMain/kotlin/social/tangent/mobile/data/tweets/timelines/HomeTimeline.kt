package social.tangent.mobile.data.tweets.timelines

import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.sdk.Mastodon

object HomeTimeline : TimelineKind("home") {
    override suspend fun fetch(mastodon: Mastodon, from: String?): List<Status> {
        return mastodon.timeline.fetchFrom(from)
    }
}