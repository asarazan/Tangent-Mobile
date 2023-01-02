package social.tangent.mobile.data

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import social.tangent.mobile.TangentStorage

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(id: String): SqlDriver {
        return AndroidSqliteDriver(TangentStorage.Schema, context, "profile_${id}.db")
    }
}