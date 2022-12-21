package social.tangent.mobile.android.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import org.koin.core.component.KoinComponent
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.compose.TimelineScreen
import kotlin.properties.Delegates

class PublicTimelineActivity : ComponentActivity(), KoinComponent {
    companion object {
        fun create(c: Context): Intent {
            return Intent(c, PublicTimelineActivity::class.java)
        }
        fun createDemo(c: Context): Intent {
            return Intent(c, PublicTimelineActivity::class.java)
                .putExtra("demo", true)
        }
    }

    private var demo by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        demo = intent.getBooleanExtra("demo", false)

        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TimelineScreen()
                }
            }
        }
    }
}
