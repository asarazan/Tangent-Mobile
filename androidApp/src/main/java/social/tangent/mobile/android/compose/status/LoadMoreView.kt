package social.tangent.mobile.android.compose.status

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.api.mock.MockApi
import social.tangent.mobile.viewmodel.SharedTimelineViewModel
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.LoadMore
import social.tangent.mobile.viewmodel.base.PreviewModel

@Composable
fun LoadMoreView(
    vm: SharedTimelineViewModel,
    lastStatus: Status
) {
    TextButton(
        onClick = { vm.send(LoadMore(lastStatus)) },
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)) {
        Text(text = "Load More...", style = MaterialTheme.typography.h6)
    }
}

@Preview(widthDp = 540)
@Composable
fun PreviewSeparator() {
    MyApplicationTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colors.background) {
            LoadMoreView(PreviewModel(mockState), MockApi.longStatus)
        }
    }
}