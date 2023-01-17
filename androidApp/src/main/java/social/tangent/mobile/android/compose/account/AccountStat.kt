package social.tangent.mobile.android.compose.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import social.tangent.mobile.util.shortFormat
import java.util.Locale

@Composable
fun AccountStat(title: String, number: Long) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)) {
        Text(
            number.shortFormat().uppercase(Locale.getDefault()),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
        Text(
            title,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.alpha(0.5f)
        )
    }
}