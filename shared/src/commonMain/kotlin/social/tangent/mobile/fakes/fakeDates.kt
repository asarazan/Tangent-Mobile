package social.tangent.mobile.fakes

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

object datetimes {
    val december2022 = Instant.parse("2022-12-10T13:14:30+07")
    val april1984 = Instant.parse("1984-04-14T02:00:00+04")
}

object dates {
    val december2022 = LocalDate.parse("2022-12-10")
}