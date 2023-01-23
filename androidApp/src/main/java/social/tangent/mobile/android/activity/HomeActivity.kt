package social.tangent.mobile.android.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.compose.home.HomeScreen
import social.tangent.mobile.android.util.longToast
import social.tangent.mobile.data.tweets.TimelineId.HomeTimeline
import social.tangent.mobile.sdk.storage.MastodonStorage
import social.tangent.mobile.viewmodel.AndroidHomeViewModel
import social.tangent.mobile.viewmodel.AndroidTimelineViewModel
import social.tangent.mobile.viewmodel.TimelineViewModel.Effect.Click
import social.tangent.mobile.viewmodel.TimelineViewModel.Effect.Comment
import social.tangent.mobile.viewmodel.TimelineViewModel.Effect.Profile

class HomeActivity : ComponentActivity(), KoinComponent {

    private val me by lazy { intent.getStringExtra("me")!! }
    private val tlvm by viewModels<AndroidTimelineViewModel> {
        AndroidTimelineViewModel.Factory(HomeTimeline, me)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HomeScreen(vm = viewModel<AndroidHomeViewModel>())
                }
            }
        }
        lifecycleScope.launch { listen() }
    }

    private suspend fun listen() {
        tlvm.sideEffectFlow.collectLatest {
            when (it) {
                is Click -> {
                    startActivity(StatusActivity.create(this, me, it.status))
                }
                is Comment -> {
                    longToast("Comment ${it.status.id}")
                }
                is Profile -> {
                    startActivity(AccountActivity.create(this, me, it.account))
                }
                else -> {
                    // nothing.
                }
            }
        }
    }

    companion object {
        fun create(c: Context): Intent {
            val id = MastodonStorage.all().first().id
            return create(c, id)
        }

        fun create(c: Context, me: String): Intent {
            return Intent(c, HomeActivity::class.java)
                .putExtra("me", me)
        }
    }
}
