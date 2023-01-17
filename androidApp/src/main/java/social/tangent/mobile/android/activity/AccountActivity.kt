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
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.compose.account.AccountScreen
import social.tangent.mobile.api.entities.Account
import social.tangent.mobile.data.extensions.deserialize
import social.tangent.mobile.data.extensions.serialize
import social.tangent.mobile.sdk.storage.MastodonStorage
import social.tangent.mobile.viewmodel.AndroidAccountViewModel

class AccountActivity : ComponentActivity() {

    private val mastodon by lazy {
        val me = intent.getStringExtra("me")!!
        MastodonStorage.get(me)!!
    }

    private val account by lazy {
        Account.deserialize(intent.getStringExtra("profile")!!)
    }

    private val vm by viewModels<AndroidAccountViewModel> {
        AndroidAccountViewModel.Factory(mastodon, account)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AccountScreen(vm)
                }
            }
        }
    }


    companion object {
        fun create(c: Context, me: String, id: String): Intent {
            return Intent(c, AccountActivity::class.java)
                .putExtra("me", me)
                .putExtra("id", id)
        }

        fun create(c: Context, me: String, profile: Account): Intent {
            return Intent(c, AccountActivity::class.java)
                .putExtra("me", me)
                .putExtra("profile", profile.serialize())
        }
    }
}