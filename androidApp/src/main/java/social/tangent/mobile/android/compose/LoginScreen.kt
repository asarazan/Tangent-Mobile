package social.tangent.mobile.android.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.viewmodel.AndroidLoginViewModel
import social.tangent.mobile.viewmodel.LoginViewModel
import social.tangent.mobile.viewmodel.LoginViewModel.Event.SelectInstance
import social.tangent.mobile.viewmodel.LoginViewModel.Event.SetTextEvent
import social.tangent.mobile.viewmodel.SharedLoginViewModel
import social.tangent.mobile.viewmodel.base.PreviewModel

@Composable
fun LoginScreen(vm: SharedLoginViewModel = viewModel<AndroidLoginViewModel>()) {
    val state by vm.stateFlow.collectAsState()
    if (state.loading) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier.size(48.dp))
        }
        return
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        TextField(
            value = state.text,
            label = { Text("Enter Server Address") },
            placeholder = { Text("https://mastodon.social") },
            onValueChange = { vm.send(SetTextEvent(it)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth(0.8f),
            onClick = { vm.send(SelectInstance) }
        ) {
            Text("Select Instance")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = {
            vm.send(SetTextEvent("https://mastodon.gamedev.place/"))
            vm.send(SelectInstance)
        }) {
            Text("(debug w/ gamedev)")
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        LoginScreen(PreviewModel(LoginViewModel.State()))
    }
}
