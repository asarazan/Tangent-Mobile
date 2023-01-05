package social.tangent.mobile.data.tweets

import social.tangent.mobile.api.entities.Status

sealed class TimelineContent() {
    abstract val id: String
    data class StatusContent(
        override val id: String,
        val status: Status,
        val loadMore: Boolean
    ) : TimelineContent()
}
