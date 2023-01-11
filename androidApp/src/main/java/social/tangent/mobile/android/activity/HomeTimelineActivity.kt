package social.tangent.mobile.android.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import org.koin.core.component.KoinComponent
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.compose.TimelineScreen
import social.tangent.mobile.sdk.Mastodon
import social.tangent.mobile.sdk.storage.MastodonStorage
import social.tangent.mobile.viewmodel.AndroidTimelineViewModel
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Init

class HomeTimelineActivity : ComponentActivity(), KoinComponent {
    companion object {
        fun create(c: Context, id: String): Intent {
            return Intent(c, HomeTimelineActivity::class.java)
                .putExtra("id", id)
        }
    }

    private lateinit var mastodon: Mastodon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getStringExtra("id")!!
        mastodon = MastodonStorage.get(id)!!
        val vm = ViewModelProvider(this).get<AndroidTimelineViewModel>()
        vm.send(Init(mastodon))
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TimelineScreen(vm)
                }
            }
        }
    }
}
