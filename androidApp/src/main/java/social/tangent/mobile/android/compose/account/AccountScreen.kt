package social.tangent.mobile.android.compose.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import social.tangent.mobile.android.compose.util.MyDivider
import social.tangent.mobile.android.compose.util.PreviewContent
import social.tangent.mobile.api.mock.MockApi
import social.tangent.mobile.api.mock.mockStatus
import social.tangent.mobile.viewmodel.AccountViewModel.State
import social.tangent.mobile.viewmodel.SharedAccountViewModel
import social.tangent.mobile.viewmodel.SharedTimelineViewModel
import social.tangent.mobile.viewmodel.TimelineViewModel
import social.tangent.mobile.viewmodel.base.PreviewModel

@Composable
fun AccountScreen(
    vm: SharedAccountViewModel,
    tlvm: SharedTimelineViewModel
) {
    CollapsingToolbarScaffold(
        state = rememberCollapsingToolbarScaffoldState(),
        toolbar = { AccountHeader(vm) },
        modifier = Modifier.fillMaxSize(),
        scrollStrategy = ScrollStrategy.EnterAlways
    ) {
        Column(modifier = Modifier
            .zIndex(2f)
        ) {
            AccountInfo(vm)
            MyDivider()
            // AccountContent(vm)
            AccountTimeline(tlvm)
        }
    }
}

@Composable
@Preview
fun PreviewAccountScreen() {
    PreviewContent {
        AccountScreen(
            vm = PreviewModel(State(mockStatus.account)),
            tlvm = PreviewModel(TimelineViewModel.State("", MockApi.timeline))
        )
    }
}