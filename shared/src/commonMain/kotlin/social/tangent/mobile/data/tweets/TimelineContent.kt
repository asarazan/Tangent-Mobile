package social.tangent.mobile.data.tweets

import social.tangent.mobile.api.entities.Status

sealed class TimelineContent {
    data class StatusContent(val status: Status) : TimelineContent()
    data class SeparatorContent(val lastId: String) : TimelineContent()
}
