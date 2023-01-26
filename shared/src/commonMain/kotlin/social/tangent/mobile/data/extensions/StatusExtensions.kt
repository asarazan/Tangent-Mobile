package social.tangent.mobile.data.extensions

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.tweets.StatusContent

val Status.combinedFavorites: Long get() {
    var sum = favouritesCount
    if (this != actionableStatus) {
        sum += actionableStatus.favouritesCount
    }
    return sum
}

val Status.combinedReblogs: Long get() {
    var sum = reblogsCount
    if (this != actionableStatus) {
        sum += actionableStatus.reblogsCount
    }
    return sum
}

val Status.actionableStatus
    get() = reblog ?: this

val Status.actionableId
    get() = (reblog ?: this).id

fun Status.update(fn: (Status) -> Status): Status {
    return if (reblog != null) {
        copy(reblog = fn(reblog))
    } else {
        fn(this)
    }
}

fun Status.serialize(): String {
    return Json.encodeToString(this)
}

fun Status.Companion.deserialize(json: String): Status {
    return Json.decodeFromString(json)
}

fun Status.toContent(loadMore: Boolean = false): StatusContent {
    return StatusContent(id, this, loadMore)
}