package social.tangent.mobile.data.tweets

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.DbFactory
import social.tangent.mobile.sdk.storage.MastodonStorage
import social.tangent.mobile.util.safeCacheOf

class StatusDb(accountId: String) : KoinComponent {
    private val mastodon = MastodonStorage.get(accountId)!!
    val db = getKoin().get<DbFactory>()[accountId]

    fun insert(statuses: List<Status>) {
        if (statuses.isEmpty()) return
        db.transaction {
            statuses.forEach {
                db.timelineQueries.insert(
                    it.id,
                    it,
                    it.account.id,
                    Json.encodeToString(it.createdAt),
                    it.reblog?.id
                )
            }
        }
    }

    fun reblogsOf(status: Status): List<Status> {
        return db.timelineQueries.lookupReblogsOf(status.id).executeAsList()
    }

    companion object {
        private val cache = safeCacheOf<String, StatusDb> { StatusDb(it) }
        operator fun get(accountId: String): StatusDb = cache[accountId]
    }
}