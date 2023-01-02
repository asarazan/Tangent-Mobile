package social.tangent.mobile.data

import com.squareup.sqldelight.db.SqlDriver
import social.tangent.mobile.TangentStorage

expect class DriverFactory {
    fun createDriver(id: String): SqlDriver
}

fun createDatabase(id: String, driverFactory: DriverFactory): TangentStorage {
    val driver = driverFactory.createDriver(id)
    val database = TangentStorage(driver)
    // Do more work with the database (see below).
    return database
}