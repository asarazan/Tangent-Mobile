package social.tangent.mobile.android.compose.status

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.compose.status.attachments.BiImage
import social.tangent.mobile.android.compose.status.attachments.MonoImage
import social.tangent.mobile.android.compose.status.attachments.QuadImage
import social.tangent.mobile.android.compose.status.attachments.TriImage
import social.tangent.mobile.android.compose.util.MyDivider
import social.tangent.mobile.api.entities.Attachment
import social.tangent.mobile.api.mock.MockApi
import social.tangent.mobile.viewmodel.SharedTimelineViewModel
import social.tangent.mobile.viewmodel.base.PreviewModel

@Composable
fun StatusAttachments(
    vm: SharedTimelineViewModel,
    attachments: List<Attachment>,
    modifier: Modifier = Modifier)
{
    when (attachments.count()) {
        0 -> { /* nothing */ }
        1 -> MonoImage(attachment = attachments.first(), modifier)
        2 -> BiImage(attachments = attachments, modifier)
        3 -> TriImage(attachments = attachments, modifier)
        4 -> QuadImage(attachments = attachments, modifier)
        else -> QuadImage(attachments = attachments.take(4), modifier)
    }
}

@Preview(widthDp = 540)
@Composable
fun PreviewStatusAttachment() {
    MyApplicationTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.padding(0.dp)) {
            Column {
                StatusView(PreviewModel(mockState), MockApi.reblogStatus.copy(mediaAttachments = MockApi.singleAttachment))
                MyDivider()
            }
        }
    }
}