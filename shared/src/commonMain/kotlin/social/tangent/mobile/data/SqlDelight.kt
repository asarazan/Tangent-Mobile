package social.tangent.mobile.data

import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import social.tangent.mobile.TangentDatabase
import social.tangent.mobile.api.entities.Status

expect class DriverFactory {
    fun createDriver(id: String): SqlDriver
}

fun createDatabase(id: String, driverFactory: DriverFactory): TangentDatabase {
    val driver = driverFactory.createDriver(id).apply {
        execute(null, "PRAGMA foreign_keys=ON;", 0)
    }
    return TangentDatabase(
        driver,
        DbStatusV2.Adapter(statusAdapter)
    )
}

val statusAdapter = object : ColumnAdapter<Status, String> {
    override fun decode(databaseValue: String): Status = Json.decodeFromString(databaseValue)
    override fun encode(value: Status): String = Json.encodeToString(value)
}