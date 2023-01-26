package social.tangent.mobile.android.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.compose.TimelineScreen
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.extensions.actionableStatus
import social.tangent.mobile.data.extensions.deserialize
import social.tangent.mobile.data.extensions.serialize
import social.tangent.mobile.data.tweets.TimelineId.ThreadTimeline
import social.tangent.mobile.viewmodel.AndroidTimelineViewModel

class StatusActivity : ComponentActivity() {

    private val status by lazy {
        Status.deserialize(intent.getStringExtra("status")!!)
    }

    private val me by lazy { intent.getStringExtra("me")!! }
    private val vm by viewModels<AndroidTimelineViewModel> {
        AndroidTimelineViewModel.Factory(ThreadTimeline(status.actionableStatus.id), me)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MyApplicationTheme {
                Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
                    TimelineScreen(vm = vm)
                }
            }
        }
    }

    companion object {
        fun create(c: Context, me: String, status: Status): Intent {
            return Intent(c, StatusActivity::class.java)
                .putExtra("me", me)
                .putExtra("status", status.serialize())
        }
    }
}