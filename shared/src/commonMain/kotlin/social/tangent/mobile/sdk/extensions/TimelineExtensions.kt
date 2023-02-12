package social.tangent.mobile.sdk.extensions

import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.tweets.StatusContent

fun List<Status>.replaceStatus(status: Status): List<Status> {
    val index = this.indexOfFirst {
        status.id == it.id
    }
    if (index < 0) {
        throw RuntimeException("Could not find status with id ${status.id}")
    }
    val result = this.toMutableList()
    result[index] = status
    return result.toList()
}

fun List<StatusContent>.replaceContent(status: Status): List<StatusContent> {
    val index = this.indexOfFirst {
        status.id == it.id
    }
    if (index < 0) {
        throw RuntimeException("Could not find status with id ${status.id}")
    }
    val result = this.toMutableList()
    val existing = result[index]
    result[index] = existing.copy(status = status)
    return result.toList()
}

fun MutableList<StatusContent>.flagLoadMore(value: Boolean = true) {
    val existing = last()
    this[this.lastIndex] = existing.copy(gap = value)
}
