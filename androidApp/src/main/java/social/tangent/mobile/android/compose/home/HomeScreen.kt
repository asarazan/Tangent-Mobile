package social.tangent.mobile.android.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.compose.home.HomeBottomBar
import social.tangent.mobile.android.compose.home.HomeTopBarNew
import social.tangent.mobile.viewmodel.AndroidTimelineViewModel
import social.tangent.mobile.viewmodel.HomeViewModel
import social.tangent.mobile.viewmodel.SharedHomeViewModel
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Init
import social.tangent.mobile.viewmodel.base.PreviewModel

@Composable
fun HomeScreen(vm: SharedHomeViewModel) {
    val state by vm.stateFlow.collectAsState()
    val mastodon = state.mastodon
    val scaffold = rememberScaffoldState()
    val listState = rememberLazyListState()
    Scaffold(
        scaffoldState = scaffold,
        topBar = { HomeTopBarNew(scaffold, listState = listState) },
        bottomBar = { HomeBottomBar() },
        // drawerContent = { HomeDrawer() },
        drawerShape = RectangleShape,
        modifier = Modifier.fillMaxSize())
    {
        contentPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding))
        {
            if (mastodon == null) {
                CircularProgressIndicator()
            } else {
                val tlvm = viewModel<AndroidTimelineViewModel>()
                tlvm.send(Init(mastodon))
                TimelineScreen(vm = tlvm, listState = listState)
            }
        }
    }
}


@Composable
@Preview(widthDp = 540)
fun HomeScreenPreview() {
    MyApplicationTheme(darkTheme = true) {
        Surface {
            HomeScreen(vm = PreviewModel(HomeViewModel.State()))
        }
    }
}
