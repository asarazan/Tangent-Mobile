package social.tangent.mobile.dates

import kotlinx.datetime.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private const val format = "dd MMM yy"

actual fun Instant.shortDateFormat(): String {
    val iso = this.toString()
    val date = java.time.Instant.parse(iso)
    val tz = ZoneId.systemDefault()
    val local = date.atZone(tz)
    return DateTimeFormatter.ofPattern(format).format(local)
}