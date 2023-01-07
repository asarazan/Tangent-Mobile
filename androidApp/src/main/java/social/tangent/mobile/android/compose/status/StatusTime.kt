package social.tangent.mobile.android.compose.status

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.dates.shortDateFormat

fun Status.formatTime(): String {
    val created = reblog?.createdAt ?: createdAt
    val now = Clock.System.now()
    val diff = created.periodUntil(now, TimeZone.UTC)
    if (diff.days >= 7) {
        return created.shortDateFormat()
    }
    if (diff.days > 0) {
        return "${diff.days}d"
    }
    if (diff.hours > 0) {
        return "${diff.hours}h"
    }
    if (diff.minutes > 0) {
        return "${diff.minutes}m"
    }
    return "${diff.seconds}"
}
