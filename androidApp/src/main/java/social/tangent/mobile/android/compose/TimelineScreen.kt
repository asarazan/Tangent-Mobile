package social.tangent.mobile.android.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.compose.status.StatusView
import social.tangent.mobile.api.mock.MockApi
import social.tangent.mobile.viewmodel.AndroidTimelineViewModel
import social.tangent.mobile.viewmodel.SharedTimelineViewModel
import social.tangent.mobile.viewmodel.TimelineViewModel
import social.tangent.mobile.viewmodel.base.PreviewModel

@Composable
fun TimelineScreen(
    vm: SharedTimelineViewModel = viewModel<AndroidTimelineViewModel>()
) {
    val state by vm.stateFlow.collectAsState()
    LazyColumn(
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {
        items(state.statuses.size) {
            StatusView(vm, state.statuses[it])
        }
    }
}


@Preview(widthDp = 540, heightDp = 2_000)
@Composable
fun PreviewTimeline() {
    MyApplicationTheme(darkTheme = true) {
        Surface {
            val timeline = MockApi.timeline
            TimelineScreen(PreviewModel(TimelineViewModel.State(timeline, false)))
        }
    }
}
