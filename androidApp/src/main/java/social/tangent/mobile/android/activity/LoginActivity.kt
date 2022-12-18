package social.tangent.mobile.android.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginScreen(onSelect = {
                        lifecycleScope.launch(Dispatchers.Main) {
                            startActivity(PublicTimelineActivity.create(this@LoginActivity))
                            // launchBrowser(it)
                        }
                    })
                }
            }
        }
    }

    override fun setIntent(newIntent: Intent?) {
        super.setIntent(newIntent)
        val data = newIntent?.data
        println("Got data $data")
    }

    @MainThread
    private fun launchBrowser(url: String) {
        val chrome = CustomTabsIntent.Builder().build()
        chrome.launchUrl(this, Uri.parse(url))
    }
}
