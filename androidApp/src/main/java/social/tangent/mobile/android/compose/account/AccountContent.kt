package social.tangent.mobile.android.compose.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import social.tangent.mobile.viewmodel.SharedAccountViewModel

@Composable
fun AccountContent(vm: SharedAccountViewModel) {
    var counter = 0
    Column {
        Text("content ${counter++}", modifier = Modifier.fillMaxWidth().height(64.dp))
        Text("content ${counter++}", modifier = Modifier.fillMaxWidth().height(64.dp))
        Text("content ${counter++}", modifier = Modifier.fillMaxWidth().height(64.dp))
        Text("content ${counter++}", modifier = Modifier.fillMaxWidth().height(64.dp))
        Text("content ${counter++}", modifier = Modifier.fillMaxWidth().height(64.dp))
        Text("content ${counter++}", modifier = Modifier.fillMaxWidth().height(64.dp))
        Text("content ${counter++}", modifier = Modifier.fillMaxWidth().height(64.dp))
    }
}