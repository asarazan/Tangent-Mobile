package social.tangent.mobile.android.compose.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.compose.HomeScreen
import social.tangent.mobile.viewmodel.HomeViewModel
import social.tangent.mobile.viewmodel.base.PreviewModel

@Composable
fun HomeTopBar(scaffold: ScaffoldState) {
    val scope = rememberCoroutineScope()
    val drawer = scaffold.drawerState
    Column {
        TopAppBar(
            title = { Text("Tangent") },
            backgroundColor = MaterialTheme.colors.background,
            navigationIcon = {
                Icon(
                    Icons.Default.Menu,
                    "Menu Drawer",
                    modifier = Modifier.clickable(onClick = {
                        scope.launch {
                            if (drawer.isOpen) drawer.close() else drawer.open()
                        }
                    })
                )
            },
            modifier = Modifier.statusBarsPadding()
        )
        Divider(
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.height(0.5.dp)
        )
    }
}

@Composable
@Preview(widthDp = 540)
fun PreviewTopBar() {
    MyApplicationTheme(darkTheme = true) {
        Surface {
            HomeTopBar(rememberScaffoldState())
        }
    }
}

@Composable
@Preview(widthDp = 540, heightDp = 1080)
fun HomeScreenPreviewTop() {
    MyApplicationTheme(darkTheme = true) {
        Surface {
            HomeScreen(vm = PreviewModel(HomeViewModel.State()))
        }
    }
}
