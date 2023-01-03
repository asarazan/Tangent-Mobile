package social.tangent.mobile.data

import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import social.tangent.mobile.TangentDatabase
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.DbStatus.Adapter

expect class DriverFactory {
    fun createDriver(id: String): SqlDriver
}

fun createDatabase(id: String, driverFactory: DriverFactory): TangentDatabase {
    val driver = driverFactory.createDriver(id)
    val database = TangentDatabase(driver, Adapter(statusAdapter))
    // Do more work with the database (see below).
    return database
}

val statusAdapter = object : ColumnAdapter<Status, String> {
    override fun decode(databaseValue: String): Status = Json.decodeFromString(databaseValue)
    override fun encode(value: Status): String = Json.encodeToString(value)
}