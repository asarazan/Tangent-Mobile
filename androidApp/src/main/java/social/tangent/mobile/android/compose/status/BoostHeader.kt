package social.tangent.mobile.android.compose.status

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.api.mock.MockApi
import social.tangent.mobile.viewmodel.base.PreviewModel

@Composable
fun BoostHeader(status: Status, modifier: Modifier = Modifier) {
    if (status.reblog == null) return
    // TODO Icon.
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier.height(32.dp)
    ) {
        Text(text = "${status.account.displayName} Reblogged", modifier = Modifier.alpha(0.4f))
    }
}

@Preview(widthDp = 540)
@Composable
fun PreviewStatusReblog() {
    MyApplicationTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colors.background) {
            Box(modifier = Modifier.padding(8.dp)) {
                StatusView(
                    PreviewModel(mockState),
                    MockApi.reblogStatus
                )
            }
        }
    }
}