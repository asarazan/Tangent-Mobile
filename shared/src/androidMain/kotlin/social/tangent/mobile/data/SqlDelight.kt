package social.tangent.mobile.data

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import social.tangent.mobile.TangentDatabase

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(id: String): SqlDriver {
        return AndroidSqliteDriver(
            TangentDatabase.Schema,
            context,
            "timeline_${id}.db"
        )
    }
}