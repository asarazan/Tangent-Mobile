package social.tangent.mobile.android.compose.account

import androidx.compose.runtime.Composable
import social.tangent.mobile.android.compose.TimelineScreen
import social.tangent.mobile.viewmodel.SharedTimelineViewModel

@Composable
fun AccountTimeline(vm: SharedTimelineViewModel) {
    TimelineScreen(vm = vm)
}