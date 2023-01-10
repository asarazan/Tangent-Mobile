package social.tangent.mobile.android.compose.status

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.onBackgroundFaint
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.api.mock.MockApi
import social.tangent.mobile.api.mock.mockState
import social.tangent.mobile.viewmodel.base.PreviewModel

@Composable
fun BoostHeader(status: Status, modifier: Modifier = Modifier) {
    if (status.reblog == null) return
    // TODO Icon.
    Box(
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = "${status.account.displayName} Reblogged",
            color = MaterialTheme.colors.onBackgroundFaint,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Preview(widthDp = 540)
@Composable
fun PreviewStatusReblog() {
    MyApplicationTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.padding(0.dp)) {
            StatusView(
                PreviewModel(mockState),
                MockApi.reblogStatus
            )
        }
    }
}