package social.tangent.mobile.data.extensions

import social.tangent.mobile.api.entities.Status

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