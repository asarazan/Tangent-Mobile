package social.tangent.mobile.android.compose.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.compose.HomeScreen
import social.tangent.mobile.android.compose.util.MyDivider
import social.tangent.mobile.viewmodel.HomeViewModel
import social.tangent.mobile.viewmodel.base.PreviewModel

@Composable
fun HomeBottomBar() {
    Column(
        modifier = Modifier.navigationBarsPadding().height(32.dp)
    ) {
        MyDivider()
        BottomAppBar(
            backgroundColor = MaterialTheme.colors.background
        ) {

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
            HomeBottomBar()
        }
    }
}