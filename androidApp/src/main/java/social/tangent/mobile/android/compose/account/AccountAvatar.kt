package social.tangent.mobile.android.compose.account

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import social.tangent.mobile.android.compose.images.PreviewableImage
import social.tangent.mobile.api.entities.Account

@Composable
fun AccountAvatar(account: Account, modifier: Modifier = Modifier) {
    PreviewableImage(
        url = account.avatar,
        modifier = modifier
            .size(128.dp)
            .clip(RoundedCornerShape(16.dp))
    )
}