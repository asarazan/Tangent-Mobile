package social.tangent.mobile.data.tweets

import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.tweets.TimelineContent.StatusContent

fun timelineMapper(
    json: Status,
    load_more: Boolean
): TimelineContent {
    return StatusContent(json.id, json, load_more)
}