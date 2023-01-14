package social.tangent.mobile.android.util

import android.widget.Toast
import org.koin.core.component.get
import social.tangent.mobile.koiner

fun longToast(text: String) {
    Toast.makeText(koiner.get(), text, Toast.LENGTH_LONG).show()
}