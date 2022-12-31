package social.tangent.mobile.android.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import social.tangent.mobile.sdk.storage.MastodonStorage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mastodons = MastodonStorage.all()
        if (mastodons.isEmpty()) {
            startActivity(LoginActivity.create(this))
        } else {
            startActivity(HomeActivity.create(this))
        }
        finish()
    }
}
