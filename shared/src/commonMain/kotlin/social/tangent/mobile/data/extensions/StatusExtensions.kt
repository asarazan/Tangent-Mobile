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

fun Status.toContent(): StatusContent {
    return StatusContent(id, this, false)
}

// fun List<StatusContent>.threaded(focus: Status): List<StatusContent> {
//     val parentMap = this.groupBy { it.status.inReplyToId }
//     val seen = mutableSetOf<String>()
//     val result = mutableListOf<StatusContent>()
//     var threadFromPrev = false
//     var threadToNext = false
//     fun traverse(status: StatusContent) {
//         if (!seen.contains(status.id)) {
//             seen.add(status.id)
//             val children = parentMap[status.id]
//             threadFromPrev = threadToNext
//             threadToNext = children?.isNotEmpty() ?: false
//             result.add(status.copy(
//                 threadFromPrev = threadFromPrev,
//                 threadToNext = threadToNext,
//                 isFocus = status.id == focus.id
//             ))
//             parentMap[status.id]?.forEach {
//                 traverse(it)
//             }
//         }
//     }
//     this.forEach {
//         traverse(it)
//     }
//     return result
// }