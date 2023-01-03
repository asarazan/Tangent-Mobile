package social.tangent.mobile.data.tweets

import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.tweets.TimelineContent.StatusContent

fun timelineMapper(
    json: Status,
    is_last: Boolean
): TimelineContent {
    val content = StatusContent(json)
    return content
    // val result = mutableListOf<TimelineContent>(content)
    // if (is_last) {
    //     result.add(SeparatorContent(json.id))
    // }
    // return result.toList()
}