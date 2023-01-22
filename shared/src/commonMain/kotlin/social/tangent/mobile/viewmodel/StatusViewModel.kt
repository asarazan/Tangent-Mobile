package social.tangent.mobile.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.sdk.Mastodon
import social.tangent.mobile.viewmodel.StatusViewModel.Effect
import social.tangent.mobile.viewmodel.StatusViewModel.Event
import social.tangent.mobile.viewmodel.StatusViewModel.State
import social.tangent.mobile.viewmodel.base.MobileViewModel
import social.tangent.mobile.viewmodel.base.SharedViewModel

typealias SharedStatusViewModel = SharedViewModel<State, Event, Effect>

class StatusViewModel(
    scope: CoroutineScope,
    private val mastodon: Mastodon,
    private val status: Status
) :
    MobileViewModel<State, Event, Effect>(scope), KoinComponent {
    override fun initialState() = State(status = status)

    init {
        scope.launch { fetch() }
    }

    override suspend fun reduce(event: Event, currentState: State): State {
        return when (event) {
            else -> currentState
        }
    }

    private suspend fun fetch() {
        if (state.isLoading) return
        state = state.copy(isLoading = true)
        val context = this.mastodon.timeline.fetchThread(status.id)
        val list = context.ancestors + status + context.descendants
        state = state.copy(
            content = list,
            isLoading = false
        )
    }

    data class State(
        val status: Status,
        val content: List<Status> = listOf(),
        val isLoading: Boolean = false
    )

    sealed class Event
    sealed class Effect
}