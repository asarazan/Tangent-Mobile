package social.tangent.mobile.android.compose.status

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.fakes.fakeToot1

@Composable
fun StatusView(status: Status) {
    Surface(color = MaterialTheme.colors.background) {
        Column {
            BoostHeader(status = status)
            Row(modifier = Modifier.height(256.dp)) {
                Avatar(status, modifier = Modifier.width(256.dp))
                Text(text = status.account.displayName)
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 540,
    heightDp = 960
)
@Composable
fun PreviewStatusView() {
    MyApplicationTheme {
        Surface {
            StatusView(fakeToot1)
        }
    }
}
