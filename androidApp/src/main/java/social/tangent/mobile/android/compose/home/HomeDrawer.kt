package social.tangent.mobile.android.compose.home

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ColumnScope.HomeDrawer() {
    Text(text = "Testing", modifier = Modifier.padding(16.dp))
    Divider()
    Text("Hi")
}