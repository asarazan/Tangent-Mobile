package social.tangent.mobile.data.extensions

import social.tangent.mobile.api.entities.Context
import social.tangent.mobile.api.entities.Status

fun Context.toList(status: Status): List<Status> {
    return (ancestors + status + descendants)
}