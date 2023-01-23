package social.tangent.mobile.data.tweets

import org.koin.core.component.KoinComponent
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.sdk.storage.MastodonStorage
import social.tangent.mobile.util.safeCacheOf

class StatusInteractor private constructor(accountId: String) : KoinComponent {
    private val mastodon = MastodonStorage.get(accountId)!!
    private val db = StatusDb[accountId]

    suspend fun fave(status: Status, faved: Boolean): Status {
        val inc = if (faved) 1 else -1
        val preview = status.copy(
            favourited = faved,
            favouritesCount = status.favouritesCount + inc
        )
        updateWithReblogs(preview)
        mastodon.statuses.fave(status.id, faved)
        // Refetch because the server is stupid and doesn't properly update the count.
        return updateWithReblogs(fetchStatus(status.id))
    }

    suspend fun reblog(status: Status, reblogged: Boolean): Status {
        val inc = if (reblogged) 1 else -1
        val preview = status.copy(
            reblogged = reblogged,
            reblogsCount = status.reblogsCount + inc
        )
        updateWithReblogs(preview)
        mastodon.statuses.reblog(status.id, reblogged)
        // Refetch because the server is stupid and doesn't properly update the count.
        return updateWithReblogs(fetchStatus(status.id))
    }

    private fun updateWithReblogs(status: Status): Status {
        val lookup = db.reblogsOf(status)
        db.insert(lookup.map {
            it.copy(reblog = status)
        } + status)
        return status
    }

    private suspend fun fetchStatus(id: String): Status {
        return mastodon.timeline.fetchById(id)
    }

    companion object {
        private val cache = safeCacheOf<String, StatusInteractor> { StatusInteractor(it) }
        operator fun get(accountId: String): StatusInteractor = cache[accountId]
    }
}