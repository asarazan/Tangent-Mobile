package social.tangent.mobile.android.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.core.component.KoinComponent
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.compose.home.HomeScreen
import social.tangent.mobile.sdk.storage.MastodonStorage
import social.tangent.mobile.viewmodel.AndroidHomeViewModel
import social.tangent.mobile.viewmodel.HomeViewModel.Event.Init

class HomeActivity : ComponentActivity(), KoinComponent {
    companion object {
        fun create(c: Context): Intent {
            val id = MastodonStorage.all().first().id
            return create(c, id)
        }

        fun create(c: Context, id: String): Intent {
            return Intent(c, HomeActivity::class.java)
                .putExtra("id", id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getStringExtra("id")!!
        val mastodon = MastodonStorage.get(id)!!
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val vm = viewModel<AndroidHomeViewModel>()
            vm.send(Init(mastodon))
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HomeScreen(vm = viewModel<AndroidHomeViewModel>())
                }
            }
        }
    }
}
