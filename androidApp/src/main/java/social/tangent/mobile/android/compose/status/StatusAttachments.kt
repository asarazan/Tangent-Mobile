package social.tangent.mobile.android.compose.status

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.R
import social.tangent.mobile.android.compose.util.MyDivider
import social.tangent.mobile.api.entities.Attachment
import social.tangent.mobile.api.mock.MockApi
import social.tangent.mobile.viewmodel.SharedTimelineViewModel
import social.tangent.mobile.viewmodel.base.PreviewModel

@Composable
fun StatusAttachments(
    vm: SharedTimelineViewModel,
    attachments: List<Attachment>)
{
    LazyHorizontalGrid(
        rows = GridCells.Adaptive(128.dp),
        modifier = Modifier.height(128.dp).padding(top = 8.dp)
    ) {
        items(attachments) {
            if (LocalInspectionMode.current) {
                Image(
                    painter = painterResource(id = R.drawable.sarazan),
                    contentDescription = it.description
                )
            } else {
                AsyncImage(
                    ImageRequest.Builder(LocalContext.current)
                        .data(it.url)
                        .crossfade(true)
                        .build(),
                    contentDescription = it.description,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
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