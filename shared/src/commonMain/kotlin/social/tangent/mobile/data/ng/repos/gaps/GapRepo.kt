package social.tangent.mobile.data.ng.repos.gaps

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface GapRepo {

    val gaps: StateFlow<Set<String>>
    val scope: CoroutineScope

    fun requery(): Set<String>
    fun addGap(gap: String)
    fun closeGap(gap: String)

    fun hasGap(status: String): Boolean
}