package social.tangent.mobile.android.util

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Environment
import androidx.core.content.FileProvider
import social.tangent.mobile.api.entities.Status
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun Activity.shareStatus(status: Status) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, status.reblog?.url ?: status.url)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, status.account.username)
    startActivity(shareIntent)
}

fun Activity.shareImage(image: Bitmap) {
    try {
        val file = File(
            getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "share_image_" + System.currentTimeMillis() + ".png"
        )
        val out = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.JPEG, 70, out)
        out.close()
        val bmpUri = FileProvider.getUriForFile(this, "social.tangent.fileprovider", file)
        val share = Intent(Intent.ACTION_SEND)
            .setType("image/*")
            .putExtra(Intent.EXTRA_STREAM, bmpUri)
        startActivity(Intent.createChooser(share, "Share Image"))
    } catch (e: IOException) {
        e.printStackTrace()
    }
}