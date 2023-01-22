package social.tangent.mobile.android.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.extensions.deserialize
import social.tangent.mobile.data.extensions.serialize
import social.tangent.mobile.sdk.storage.MastodonStorage
import social.tangent.mobile.viewmodel.AndroidStatusViewModel

class StatusActivity : ComponentActivity() {

    private val mastodon by lazy {
        MastodonStorage.default
    }

    private val status by lazy {
        Status.deserialize(intent.getStringExtra("status")!!)
    }

    private val vm by viewModels<AndroidStatusViewModel> {
        AndroidStatusViewModel.Factory(mastodon, status)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text("Soon...\n(${status.account.displayName})")
        }
    }

    companion object {
        fun create(c: Context, status: Status): Intent {
            return Intent(c, StatusActivity::class.java)
                .putExtra("status", status.serialize())
        }
    }
}