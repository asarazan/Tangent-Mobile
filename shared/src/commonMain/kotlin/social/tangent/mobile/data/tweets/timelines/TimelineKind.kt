package social.tangent.mobile.data.tweets.timelines

import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.sdk.Mastodon

sealed class TimelineKind(
    val id: String,
    val canLoadMore: Boolean = true
) {
    operator fun invoke(): String = id
    open fun process(list: List<Status>) = list
    abstract suspend fun fetch(mastodon: Mastodon, from: String? = null): List<Status>
}