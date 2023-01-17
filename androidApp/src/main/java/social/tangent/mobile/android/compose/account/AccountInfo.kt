package social.tangent.mobile.android.compose.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import social.tangent.mobile.android.compose.text.EmojiText
import social.tangent.mobile.android.onBackgroundFaint
import social.tangent.mobile.viewmodel.SharedAccountViewModel

@Composable
fun AccountInfo(vm: SharedAccountViewModel, modifier: Modifier = Modifier) {
    val state by vm.stateFlow.collectAsState()
    val account = state.account
    Column(
        modifier = modifier.offset(y = (-52).dp)
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            AccountAvatar(
                account = account
            )
            Spacer(modifier = Modifier.weight(1f))
            AccountStat(title = "posts", number = account.statusesCount)
            AccountStat(title = "following", number = account.followingCount)
            AccountStat(title = "followers", number = account.followersCount)
        }
        Row(modifier = Modifier.padding(top = 16.dp)) {
            Column {
                EmojiText(
                    text = account.displayName,
                    emoji = account.emojis,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "@${account.acct}",
                    color = MaterialTheme.colors.onBackgroundFaint,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Follow")
            }
        }
    }
}