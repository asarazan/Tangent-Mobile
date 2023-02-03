package social.tangent.mobile.data.tweets

import social.tangent.mobile.api.entities.Status

data class StatusContent(
    val id: String,
    val status: Status,
    val loadMore: Boolean,
    val isFocus: Boolean = false,
    val threadFromPrev: Boolean = false,
    val threadToNext: Boolean = false
)