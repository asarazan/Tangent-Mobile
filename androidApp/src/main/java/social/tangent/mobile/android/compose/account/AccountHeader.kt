package social.tangent.mobile.android.compose.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import social.tangent.mobile.android.compose.images.PreviewableImage
import social.tangent.mobile.viewmodel.SharedAccountViewModel

@Composable
fun AccountHeader(vm: SharedAccountViewModel) {
    val state by vm.stateFlow.collectAsState()
    val account = state.account
    Box(
        contentAlignment = Alignment.BottomStart,
        // modifier = Modifier.padding(bottom = offset)
    ) {
        Column {
            PreviewableImage(
                url = account.header,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f)
            )
            // Spacer(modifier = Modifier.height(48.dp))
        }
    }
}