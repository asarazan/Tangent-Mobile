package social.tangent.mobile.android.compose.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
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
import social.tangent.mobile.android.compose.HomeScreen
import social.tangent.mobile.viewmodel.HomeViewModel
import social.tangent.mobile.viewmodel.base.PreviewModel

@Composable
fun HomeTopBarNew(scaffold: ScaffoldState, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.statusBarsPadding()
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
            Icon(
                painterResource(id = R.drawable.logo),
                "Logo",
                tint = MaterialTheme.colors.onBackground,
            )
            // Spacer(modifier = Modifier.fillMaxWidth(0.5f))
        }
        Divider(
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.height(0.5.dp)
        )
    }
}

@Composable
fun HomeTopBar(scaffold: ScaffoldState) {
    val scope = rememberCoroutineScope()
    val drawer = scaffold.drawerState
    Column {
        TopAppBar(
            title = { Icon(painterResource(id = R.drawable.logo), "Logo") },
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
@Preview(widthDp = 540)
fun PreviewTopBaNew() {
    MyApplicationTheme(darkTheme = true) {
        Surface {
            HomeTopBarNew(rememberScaffoldState())
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
