package social.tangent.mobile.android.compose.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.russhwolf.settings.Settings
import org.koin.java.KoinJavaComponent

@Composable
fun rememberSettings(): Settings {
    return remember {
        KoinJavaComponent.getKoin().get()
    }
}