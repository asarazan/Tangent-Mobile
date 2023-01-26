package social.tangent.mobile.data.extensions

import social.tangent.mobile.api.entities.Context
import social.tangent.mobile.api.entities.Status

fun Context.toList(status: Status): List<Status> {
    // TODO -- threading.
    // val first = ancestors.firstOrNull() ?: status
    // val all = (ancestors + status + descendants)
    // val roots = all.filter { it.inReplyToId == null || it.inReplyToId == first.actionableStatus.id || it.inReplyToId == first.id }
    // val result = mutableListOf(status)
    // roots.forEach {
    //
    // }
    return (ancestors + status + descendants)
}