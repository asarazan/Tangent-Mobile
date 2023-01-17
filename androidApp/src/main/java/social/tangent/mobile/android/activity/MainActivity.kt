package social.tangent.mobile.android.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import social.tangent.mobile.api.mock.MockApi.Companion.mockAccount
import social.tangent.mobile.sdk.storage.MastodonStorage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mastodons = MastodonStorage.all()
        if (mastodons.isEmpty()) {
            startActivity(LoginActivity.create(this))
        } else {
            // startActivity(HomeActivity.create(this))
            val account = mastodons.first()
            startActivity(AccountActivity.create(this, account.id, mockAccount))
        }
        finish()
    }
}
