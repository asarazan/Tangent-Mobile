package social.tangent.mobile.data.tweets

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.DbFactory
import social.tangent.mobile.data.extensions.toContent
import social.tangent.mobile.data.ng.repos.gaps.DbGapRepo
import social.tangent.mobile.data.ng.repos.gaps.GapRepo
import social.tangent.mobile.data.ng.repos.posts.CompositeRepo
import social.tangent.mobile.data.ng.repos.posts.PostRepo
import social.tangent.mobile.data.tweets.timelines.TimelineKind
import social.tangent.mobile.sdk.Mastodon
import social.tangent.mobile.sdk.storage.MastodonStorage
import social.tangent.mobile.util.safeCacheOf

class TimelineStorage(
    private val kind: TimelineKind,
    private val posts: PostRepo,
    private val gaps: GapRepo,
    private val mastodon: Mastodon,
) {
    val timeline
        get() = posts.posts.map { list ->
            Timeline(list.map {
                StatusContent(
                    id = it.id,
                    status = it,
                    gap = gaps.hasGap(it.id)
                )
                it.toContent()
            })
        }

    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    val api get() = mastodon.api

    private val _isLoading = MutableStateFlow(false)

    suspend fun fetchFrom(from: Status?) {
        insert(kind.fetch(mastodon, from?.id), from?.id)
    }

    suspend fun fetch() {
        if (_isLoading.value) return
        _isLoading.emit(true)
        fetchFrom(null)
        _isLoading.emit(false)
    }

    suspend fun fave(status: Status, faved: Boolean): Status {
        val inc = if (faved) 1 else -1
        val preview = status.copy(
            favourited = faved,
            favouritesCount = status.favouritesCount + inc
        )
        updateWithReblogs(preview)
        mastodon.statuses.fave(status.id, faved)
        // Refetch because the server is stupid and doesn't properly update the count.
        return updateWithReblogs(mastodon.timeline.fetchById(status.id))
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
        return updateWithReblogs(mastodon.timeline.fetchById(status.id))
    }

    private fun updateWithReblogs(status: Status): Status {
        val lookup = posts.reblogsOf(status.id)
        val updated = lookup.map {
            it.copy(reblog = status)
        } + status
        posts.insert(updated)
        return status
    }

    private fun insert(statuses: List<Status>, closeGap: String? = null) {
        posts.insert(statuses)
        closeGap?.let {
            gaps.closeGap(it)
        }
    }

    companion object : KoinComponent {
        private data class Key(
            val id: String,
            val kind: TimelineKind
        )
        @OptIn(DelicateCoroutinesApi::class)
        private val cache = safeCacheOf<Key, TimelineStorage> {
            val scope = GlobalScope
            val mastodon = MastodonStorage.get(it.id)!!
            val db = get<DbFactory>()[mastodon.id]
            val posts = CompositeRepo(it.kind, db, scope)
            // val posts = MemoryRepo(it.kind, scope)
            val gaps = DbGapRepo(it.kind, db, scope)
            TimelineStorage(it.kind, posts, gaps, mastodon)
        }

        fun get(
            kind: TimelineKind,
            mastodon: Mastodon
        ): TimelineStorage {
            return cache[Key(mastodon.id, kind)]
        }
    }
}