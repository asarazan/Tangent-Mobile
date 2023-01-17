package social.tangent.mobile.android.compose.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import social.tangent.mobile.android.compose.util.PreviewContent
import social.tangent.mobile.viewmodel.SharedAccountViewModel

@Composable
fun AccountScreen(vm: SharedAccountViewModel) {
    CollapsingToolbarScaffold(
        state = rememberCollapsingToolbarScaffoldState(),
        toolbar = { AccountHeader(vm) },
        modifier = Modifier.fillMaxSize(),
        scrollStrategy = ScrollStrategy.EnterAlways
    ) {
        Column(modifier = Modifier.zIndex(2f).padding(horizontal = 16.dp)) {
            AccountInfo(vm)
            AccountContent(vm)
            AccountTimeline()
        }
    }
}

@Composable
@Preview
fun PreviewAccountScreen() {
    PreviewContent {
        // TODO
        // ProfileScreen(PreviewModel(State()))
    }
}