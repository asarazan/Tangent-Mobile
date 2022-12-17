package social.tangent.mobile.sdk.extensions

import social.tangent.mobile.api.entities.Status

fun List<Status>.replace(status: Status): List<Status> {
    val index = this.indexOfFirst {
        status.id == it.id
    }
    if (index < 0) throw RuntimeException("Could not find status with id ${status.id}")
    val result = this.toMutableList()
    result[index] = status
    return result.toList()
}