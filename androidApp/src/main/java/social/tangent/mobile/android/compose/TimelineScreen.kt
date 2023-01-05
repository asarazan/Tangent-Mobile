package social.tangent.mobile.android.compose

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.compose.status.LoadMoreView
import social.tangent.mobile.android.compose.status.StatusView
import social.tangent.mobile.android.compose.util.MyDivider
import social.tangent.mobile.android.compose.util.scrollbar
import social.tangent.mobile.api.mock.MockApi
import social.tangent.mobile.data.tweets.TimelineContent.StatusContent
import social.tangent.mobile.viewmodel.SharedTimelineViewModel
import social.tangent.mobile.viewmodel.TimelineViewModel
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Refresh
import social.tangent.mobile.viewmodel.base.PreviewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TimelineScreen(
    vm: SharedTimelineViewModel,
    listState: LazyListState = rememberLazyListState()
) {
    val state by vm.stateFlow.collectAsState()
    val pullRefreshState = rememberPullRefreshState(state.refreshing, {
        vm.send(Refresh)
    })

    Surface(color = MaterialTheme.colors.background) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            if (state.loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp)
                )
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .scrollbar(listState, false, fixedKnobRatio = 0.05f),
                ) {
                    items(state.content, key = { it.id }) {
                        when (it) {
                            is StatusContent -> {
                                StatusView(vm, it.status)
                                if (it.loadMore || it == state.content.lastOrNull()) {
                                    MyDivider()
                                    LoadMoreView(vm = vm, lastStatus = it.status)
                                }
                            }
                        }
                        MyDivider()
                    }
                }
            }
            PullRefreshIndicator(state.refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }

    // nudge up a bit when the list changes.
    val firstId = state.content.getOrNull(0)?.id
    LaunchedEffect(firstId) {
        listState.animateScrollBy(-64f, tween(500))
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