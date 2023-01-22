package social.tangent.mobile.data.ng.storage

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import social.tangent.mobile.TangentDatabase
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.StatusQueries
import social.tangent.mobile.data.tweets.StatusContent
import social.tangent.mobile.data.tweets.timelineMapper

private typealias Mapper = (Status, Boolean) -> StatusContent

class SqlStorage(
    override val scope: CoroutineScope,
    val db: TangentDatabase,
    val query: StatusQueries.(Mapper) -> Query<StatusContent>
) : PostStorage {

    override val posts: StateFlow<List<StatusContent>> get() = db.statusQueries
        .query(::timelineMapper)
        .asFlow()
        .map { it.executeAsList() }
        .stateIn(scope, SharingStarted.Eagerly, listOf())

    override suspend fun add(posts: List<StatusContent>) {
        val statuses = posts.map { it.status }
        db.transaction {
            statuses.forEachIndexed {
                index, it ->
                db.statusQueries.insert(
                    it.id,
                    it,
                    it.account.id,
                    Json.encodeToString(it.createdAt),
                    it.reblog?.id,
                    posts[index].loadMore
                )
            }
        }
    }

    override suspend fun remove(posts: List<String>) {
        db.statusQueries.deleteIds(posts)
    }

    override suspend fun has(post: String): Boolean {
        return db.statusQueries.checkExists(post).execute().next()
    }

    private fun clearLoadMore(id: String) {
        db.statusQueries.clearLoadMore(id)
    }
}