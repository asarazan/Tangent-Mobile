package social.tangent.mobile.android.compose.status

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.compose.util.Html
import social.tangent.mobile.android.compose.util.trimPTags
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.api.mock.MockApi
import social.tangent.mobile.viewmodel.SharedTimelineViewModel
import social.tangent.mobile.viewmodel.TimelineViewModel
import social.tangent.mobile.viewmodel.base.PreviewModel

@Composable
fun StatusView(
    vm: SharedTimelineViewModel,
    status: Status
) {
    Column {
        BoostHeader(status = status)
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Column(
                modifier = Modifier
                    .width(72.dp)
                    .padding(end = 8.dp, bottom = 8.dp)
            ) {
                Avatar(status = status)
            }
            Column {
                Text(text = status.account.displayName, fontWeight = FontWeight.Bold)
                Text(text = status.account.acct, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp).alpha(0.4f))
                Html(text = status.content.trimPTags(), modifier = Modifier.padding(bottom = 8.dp).fillMaxWidth())
                StatusFooter(vm, status = status)
            }
        }
    }
}

@Preview(widthDp = 540)
@Composable
fun PreviewStatusView() {
    MyApplicationTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colors.background) {
            StatusView(
                PreviewModel(mockState),
                mockStatus.copy(reblogged = true, favourited = true)
            )
        }
    }
}

@Preview(widthDp = 540)
@Composable
fun PreviewStatusViewDark() {
    MyApplicationTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colors.background) {
            StatusView(
                PreviewModel(mockState),
                mockStatus.copy(reblogged = true, favourited = true)
            )
        }
    }
}

@Preview(widthDp = 540)
@Composable
fun PreviewStatusViewRTL() {
    MyApplicationTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colors.background) {
            StatusView(PreviewModel(mockState), MockApi.rtlStatus)
        }
    }
}

@Preview(widthDp = 540, showBackground = true)
@Composable
fun PreviewStatusViewTall() {
    MyApplicationTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colors.background) {
            StatusView(PreviewModel(mockState), MockApi.longStatus)
        }
    }
}

val mockState by lazy {
    TimelineViewModel.State(MockApi.timeline)
}
val mockStatus by lazy {
    MockApi.fakeStatus
}
