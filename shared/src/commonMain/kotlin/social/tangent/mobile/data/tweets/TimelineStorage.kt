package social.tangent.mobile.data.tweets

import kotlinx.coroutines.CoroutineScope
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

class TimelineStorage(
    private val kind: TimelineKind,
    private val posts: PostRepo,
    private val gaps: GapRepo,
    private val mastodon: Mastodon,
) {
    val timeline
        get() = posts.posts.map {
            Timeline(it.map(Status::toContent))
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
        fun create(
            kind: TimelineKind,
            mastodon: Mastodon,
            scope: CoroutineScope
        ): TimelineStorage {
            val db = get<DbFactory>()[mastodon.id]
            val posts = CompositeRepo(kind, db, scope)
            val gaps = DbGapRepo(kind, db, scope)
            return TimelineStorage(kind, posts, gaps, mastodon)
        }
    }
}