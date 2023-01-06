package social.tangent.mobile.data.tweets

import social.tangent.mobile.api.entities.Status

data class StatusContent(
    val id: String,
    val status: Status,
    val loadMore: Boolean
)