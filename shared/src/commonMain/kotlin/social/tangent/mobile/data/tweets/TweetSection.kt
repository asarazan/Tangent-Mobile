package social.tangent.mobile.data.tweets

import social.tangent.mobile.api.entities.Status

data class TweetSection(
    val tweets: List<Status>,
    // TODO - how do cursors work?
)
