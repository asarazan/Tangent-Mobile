package social.tangent.mobile.android.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.compose.status.StatusView
import social.tangent.mobile.api.mock.MockApi
import social.tangent.mobile.viewmodel.AndroidTimelineViewModel
import social.tangent.mobile.viewmodel.SharedTimelineViewModel
import social.tangent.mobile.viewmodel.TimelineViewModel
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Refresh
import social.tangent.mobile.viewmodel.base.PreviewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TimelineScreen(
    vm: SharedTimelineViewModel = viewModel<AndroidTimelineViewModel>()
) {
    val state by vm.stateFlow.collectAsState()
    val pullRefreshState = rememberPullRefreshState(state.refreshing, { vm.send(Refresh) })
    Surface(color = MaterialTheme.colors.background) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().pullRefresh(pullRefreshState)
        ) {
            if (state.loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.background(MaterialTheme.colors.background),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.statuses, key = { it.id }) {
                        StatusView(vm, it)
                    }
                }
            }
            PullRefreshIndicator(state.refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}

@Preview(widthDp = 540, heightDp = 2_000)
@Composable
fun PreviewTimeline() {
    MyApplicationTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colors.background) {
            val timeline = MockApi.timeline
            TimelineScreen(PreviewModel(TimelineViewModel.State(timeline, false)))
        }
    }
}

@Preview(widthDp = 540)
@Composable
fun PreviewTimelineLoading() {
    MyApplicationTheme(darkTheme = true) {
        Surface {
            val timeline = MockApi.timeline
            TimelineScreen(PreviewModel(TimelineViewModel.State(timeline, true)))
        }
    }
}