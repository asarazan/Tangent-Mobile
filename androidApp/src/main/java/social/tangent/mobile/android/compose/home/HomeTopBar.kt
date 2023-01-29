package social.tangent.mobile.android.compose.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.R
import social.tangent.mobile.android.compose.util.MyDivider
import social.tangent.mobile.viewmodel.HomeViewModel
import social.tangent.mobile.viewmodel.SharedTimelineViewModel
import social.tangent.mobile.viewmodel.TimelineViewModel
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.ScrollToTop
import social.tangent.mobile.viewmodel.base.PreviewModel

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    vm: SharedTimelineViewModel
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .height(48.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // Spacer(modifier = Modifier.fillMaxWidth(0.5f))
            IconButton(onClick = {
                scope.launch {
                    vm.send(ScrollToTop)
                }
            }) {
                Icon(
                    painterResource(id = R.drawable.logo),
                    "Logo",
                    tint = MaterialTheme.colors.onBackground,
                )
            }
            // Spacer(modifier = Modifier.fillMaxWidth(0.5f))
        }
        MyDivider()
    }
}

@Composable
@Preview(widthDp = 540)
fun PreviewTopBaNew() {
    MyApplicationTheme(darkTheme = true) {
        Surface {
            HomeTopBar(vm = PreviewModel(TimelineViewModel.State("")))
        }
    }
}

@Composable
@Preview(widthDp = 540, heightDp = 1080)
fun HomeScreenPreviewTop() {
    MyApplicationTheme(darkTheme = true) {
        Surface {
            HomeScreen(
                vm = PreviewModel(HomeViewModel.State()),
                tlvm = PreviewModel(TimelineViewModel.State(""))
            )
        }
    }
}
