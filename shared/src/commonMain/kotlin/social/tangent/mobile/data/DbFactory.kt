package social.tangent.mobile.data

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import social.tangent.mobile.TangentDatabase
import social.tangent.mobile.util.safeCacheOf

object DbFactory : KoinComponent {
    private val cache = safeCacheOf<String, TangentDatabase> {
        createDatabase(it, (this as KoinComponent).get())
    }

    operator fun get(accountId: String) = cache[accountId]
}