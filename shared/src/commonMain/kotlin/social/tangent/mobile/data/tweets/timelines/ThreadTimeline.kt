package social.tangent.mobile.data.tweets.timelines

import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.extensions.toList
import social.tangent.mobile.sdk.Mastodon

class ThreadTimeline(val status: Status) : TimelineKind(
    "thread:$status",
    false
) {
    override fun process(list: List<Status>) = list.asReversed()//.threaded(status)
    override suspend fun fetch(mastodon: Mastodon, from: String?): List<Status> {
        return mastodon.timeline.fetchThread(status.id).toList(status)
    }
}