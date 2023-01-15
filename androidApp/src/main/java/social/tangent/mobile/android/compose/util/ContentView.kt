package social.tangent.mobile.android.compose.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.api.mock.mockStatus
import social.tangent.mobile.data.extensions.actionableStatus

@Composable
fun ContentView(
    status: Status,
    modifier: Modifier = Modifier
) {
    Text(text = status.actionableStatus.content, modifier = modifier.fillMaxSize())
}


@Preview(widthDp = 540)
@Composable
fun PreviewContentView() {
    MyApplicationTheme(darkTheme = true) {
        Surface {
            ContentView(status = mockStatus)
        }
    }
}