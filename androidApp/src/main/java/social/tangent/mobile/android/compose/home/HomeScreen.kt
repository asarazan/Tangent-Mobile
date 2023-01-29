package social.tangent.mobile.android.compose.home

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.core.component.get
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.compose.TimelineScreen
import social.tangent.mobile.koiner
import social.tangent.mobile.viewmodel.AndroidTimelineViewModel
import social.tangent.mobile.viewmodel.HomeViewModel
import social.tangent.mobile.viewmodel.HomeViewModel.Effect.TabReclicked
import social.tangent.mobile.viewmodel.HomeViewModel.Tab.Home
import social.tangent.mobile.viewmodel.HomeViewModel.Tab.Search
import social.tangent.mobile.viewmodel.SharedHomeViewModel
import social.tangent.mobile.viewmodel.SharedTimelineViewModel
import social.tangent.mobile.viewmodel.TimelineViewModel
import social.tangent.mobile.viewmodel.base.PreviewModel

@Composable
fun HomeScreen(
    vm: SharedHomeViewModel,
    tlvm: SharedTimelineViewModel
) {
    val state by vm.stateFlow.collectAsState()
    val effect by vm.sideEffectFlow.collectAsState(null)
    val scaffold = rememberScaffoldState()
    val listState = rememberLazyListState()
    LaunchedEffect(effect) {
        when (effect) {
            is TabReclicked -> {
                tlvm.send(TimelineViewModel.Event.ScrollToTop)
            }
            else -> {}
        }
    }
    Scaffold(
        scaffoldState = scaffold,
        topBar = { HomeTopBar(vm = tlvm) },
        bottomBar = { HomeBottomBar(vm) },
        // drawerContent = { HomeDrawer() },
        // drawerShape = RectangleShape,
        modifier = Modifier.fillMaxSize(),
    )
    {
        contentPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding))
        {
            val tlvm = viewModel<AndroidTimelineViewModel>()
            when (state.tab) {
                Home -> {
                    TimelineScreen(vm = tlvm, listState = listState)
                }
                Search -> {
                    SideEffect {
                        Toast.makeText(koiner.get(), "TODO", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}


@Composable
@Preview(widthDp = 540)
fun HomeScreenPreview() {
    MyApplicationTheme(darkTheme = true) {
        Surface {
            HomeScreen(
                vm = PreviewModel(HomeViewModel.State()),
                tlvm = PreviewModel(TimelineViewModel.State(""))
            )
        }
    }
}
