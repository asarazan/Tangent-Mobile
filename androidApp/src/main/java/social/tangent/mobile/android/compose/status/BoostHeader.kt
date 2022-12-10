package social.tangent.mobile.android.compose.status

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import social.tangent.mobile.api.entities.Status

@Composable
fun BoostHeader(status: Status) {
    if (status.reblog == null) return
    // TODO Icon.
    Text(text = "${status.account.displayName} Reblogged")
}