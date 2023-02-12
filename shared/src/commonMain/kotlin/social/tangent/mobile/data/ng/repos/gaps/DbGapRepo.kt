package social.tangent.mobile.data.ng.repos.gaps

import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import social.tangent.mobile.TangentDatabase
import social.tangent.mobile.data.tweets.timelines.TimelineKind

class DbGapRepo(
    private val timeline: TimelineKind,
    private val db: TangentDatabase,
    override val scope: CoroutineScope
) : GapRepo {

    private val query = db.timelineQueries.allGaps(timeline())
    override val gaps: StateFlow<Set<String>> = query
        .asFlow()
        .map {
            it.executeAsList().toSet()
        }
        .stateIn(scope, SharingStarted.Eagerly, setOf())

    override fun requery(): Set<String> {
        return db.timelineQueries.allGaps(timeline()).executeAsList().toSet()
    }

    // Note that this never gets called presently
    // because the logic is handled directly in DbPostsRepo
    override fun addGap(gap: String) {
        db.timelineQueries.addGap(timeline(), gap)
    }

    override fun closeGap(gap: String) {
        db.timelineQueries.closeGap(timeline(), gap)
    }

    override fun hasGap(status: String): Boolean {
        return gaps.value.contains(status)
    }
}