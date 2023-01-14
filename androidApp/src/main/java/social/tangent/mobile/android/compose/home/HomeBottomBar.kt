package social.tangent.mobile.android.compose.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.R
import social.tangent.mobile.android.compose.util.MyDivider
import social.tangent.mobile.android.onBackgroundFaint
import social.tangent.mobile.viewmodel.HomeViewModel
import social.tangent.mobile.viewmodel.HomeViewModel.Event.ClickTab
import social.tangent.mobile.viewmodel.HomeViewModel.Tab.Home
import social.tangent.mobile.viewmodel.HomeViewModel.Tab.Search
import social.tangent.mobile.viewmodel.SharedHomeViewModel
import social.tangent.mobile.viewmodel.base.PreviewModel



@Composable
fun HomeBottomBar(vm: SharedHomeViewModel) {
    val state by vm.stateFlow.collectAsState()
    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .height(48.dp)
    ) {
        MyDivider()
        BottomAppBar(
            backgroundColor = MaterialTheme.colors.background
        ) {
            Row(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.house_user_solid),
                    contentDescription = null,
                    tint = if (state.tab == HomeViewModel.Tab.Home)
                        MaterialTheme.colors.onBackground else
                            MaterialTheme.colors.onBackgroundFaint,
                    modifier = Modifier
                        .clickable {
                            vm.send(ClickTab(Home))
                        }.padding(12.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.magnifying_glass_solid),
                    contentDescription = null,
                    tint = if (state.tab == Search)
                        MaterialTheme.colors.onBackground else
                        MaterialTheme.colors.onBackgroundFaint,
                    modifier = Modifier
                        .clickable {
                            vm.send(ClickTab(Search))
                        }.padding(12.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
@Preview(widthDp = 540, heightDp = 1080)
fun HomeScreenPreviewBottom() {
    MyApplicationTheme(darkTheme = true) {
        Surface {
            HomeScreen(vm = PreviewModel(HomeViewModel.State()))
        }
    }
}

@Composable
@Preview(widthDp = 540)
fun PreviewBottomBar() {
    MyApplicationTheme(darkTheme = true) {
        Surface {
            HomeBottomBar(vm = PreviewModel(HomeViewModel.State()))
        }
    }
}