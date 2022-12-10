package social.tangent.mobile.android.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import social.tangent.mobile.android.compose.status.StatusView
import social.tangent.mobile.viewmodel.AndroidTimelineViewModel
import social.tangent.mobile.viewmodel.SharedTimelineViewModel

@Composable
fun TimelineScreen(
    vm: SharedTimelineViewModel = viewModel<AndroidTimelineViewModel>()
) {
    val state by vm.stateFlow.collectAsState()
    LazyColumn {
        items(state.statuses.size) {
            StatusView(state.statuses[it])
        }
    }
}
