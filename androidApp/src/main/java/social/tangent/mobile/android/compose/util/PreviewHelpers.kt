package social.tangent.mobile.android.compose.util

import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import social.tangent.mobile.android.MyApplicationTheme

@Composable
fun PreviewContent(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit)
{
    MyApplicationTheme(darkTheme = true) {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.size(540.dp, 1080.dp))
        {
            content()
        }
    }
}