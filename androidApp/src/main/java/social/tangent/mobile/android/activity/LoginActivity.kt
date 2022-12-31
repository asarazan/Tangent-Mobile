package social.tangent.mobile.android.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.MainThread
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.compose.LoginScreen
import social.tangent.mobile.viewmodel.AndroidLoginViewModel
import social.tangent.mobile.viewmodel.LoginViewModel
import social.tangent.mobile.viewmodel.LoginViewModel.Effect.Complete
import social.tangent.mobile.viewmodel.LoginViewModel.Event.ProvideOauthCode

class LoginActivity : ComponentActivity() {
    companion object {
        fun create(c: Context): Intent {
            return Intent(c, LoginActivity::class.java)
        }
    }

    private lateinit var viewModel: AndroidLoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModels<AndroidLoginViewModel>().value
        lifecycleScope.launch { listen() }
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginScreen(viewModel)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val code = intent?.data?.getQueryParameter("code")
        if (code != null) {
            viewModel.send(ProvideOauthCode(code))
        }
    }

    private suspend fun listen() {
        viewModel.sideEffectFlow.collect {
            when (it) {
                is LoginViewModel.Effect.GoToUrl -> {
                    lifecycleScope.launch(Dispatchers.Main) {
                        launchBrowser(it.url)
                    }
                }
                is Complete -> {
                    startActivity(HomeTimelineActivity.create(this, it.mastodon.id))
                }
            }
        }
    }

    @MainThread
    private fun launchBrowser(url: String) {
        val chrome = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .build()
        chrome.intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        chrome.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        chrome.launchUrl(this, Uri.parse(url))
    }
}
