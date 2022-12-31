package social.tangent.mobile.android.compose.util

import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
fun MyDivider(modifier: Modifier = Modifier) {
    Divider(color = MaterialTheme.colors.onBackground, modifier = modifier.height(0.5.dp).alpha(0.5f))
}