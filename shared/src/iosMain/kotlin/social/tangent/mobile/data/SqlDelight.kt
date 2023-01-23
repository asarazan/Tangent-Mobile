package social.tangent.mobile.data

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import social.tangent.mobile.TangentDatabase

actual class DriverFactory {
    actual fun createDriver(id: String): SqlDriver {
        return NativeSqliteDriver(
            TangentDatabase.Schema,
            "${id}.db"
        )
    }
}