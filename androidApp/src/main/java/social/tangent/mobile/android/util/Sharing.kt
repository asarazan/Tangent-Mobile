package social.tangent.mobile.android.util

import android.app.Activity
import android.content.Intent
import social.tangent.mobile.api.entities.Status

fun shareStatus(activity: Activity, status: Status) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, status.reblog?.url ?: status.url)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, status.account.username)
    activity.startActivity(shareIntent)
}