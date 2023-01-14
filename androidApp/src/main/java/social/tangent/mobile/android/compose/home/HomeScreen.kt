package social.tangent.mobile.android.compose.home

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
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
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Init
import social.tangent.mobile.viewmodel.base.PreviewModel

@Composable
fun HomeScreen(vm: SharedHomeViewModel) {
    val state by vm.stateFlow.collectAsState()
    val effect by vm.sideEffectFlow.collectAsState(null)
    val mastodon = remember { state.mastodon }
    val scaffold = rememberScaffoldState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    LaunchedEffect(effect) {
        when (effect) {
            is TabReclicked -> {
                scope.launch {
                    listState.animateScrollToItem(0)
                }
            }
            else -> {}
        }
    }
    Scaffold(
        scaffoldState = scaffold,
        topBar = { HomeTopBar(listState = listState) },
        bottomBar = { HomeBottomBar(vm) },
        // drawerContent = { HomeDrawer() },
        drawerShape = RectangleShape,
        modifier = Modifier.fillMaxSize(),
    )
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
