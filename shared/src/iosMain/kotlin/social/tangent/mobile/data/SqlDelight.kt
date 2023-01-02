package social.tangent.mobile.data

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import social.tangent.mobile.TangentStorage

actual class DriverFactory {
    actual fun createDriver(id: String): SqlDriver {
        return NativeSqliteDriver(TangentStorage.Schema, "${id}.db")
    }
}