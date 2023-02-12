package social.tangent.mobile.data.tweets

import social.tangent.mobile.api.entities.Status

fun timelineMapper(
    json: Status,
    load_more: Boolean
): StatusContent {
    return StatusContent(json.id, json, load_more)
}

fun timelineMapperBasic(
    json: Status,
    load_more: Boolean
): Status {
    return json
}
