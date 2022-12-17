package social.tangent.mobile.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.sdk.Mastodon
import social.tangent.mobile.sdk.extensions.replace
import social.tangent.mobile.viewmodel.TimelineViewModel.Effect
import social.tangent.mobile.viewmodel.TimelineViewModel.Event
import social.tangent.mobile.viewmodel.TimelineViewModel.State
import social.tangent.mobile.viewmodel.base.MobileViewModel
import social.tangent.mobile.viewmodel.base.SharedViewModel

typealias SharedTimelineViewModel = SharedViewModel<State, Event, Effect>

class TimelineViewModel(scope: CoroutineScope) :
    MobileViewModel<State, Event, Effect>(scope), KoinComponent {

    private val mastodon by inject<Deferred<Mastodon>>()

    override fun initialState() = State(listOf(), true)

    init {
        scope.launch {
            fetch()
        }
    }

    override suspend fun reduce(event: Event, currentState: State): State {
        return when (event) {
            is Event.Fave -> currentState.copy(
                statuses = currentState.statuses.replace(mastodon.await().fave(event.status, event.faved))
            )
            is Event.Reblog -> currentState.copy(
                statuses = currentState.statuses.replace(mastodon.await().reblog(event.status, event.reblogged))
            )
        }
    }

    data class State(
        val statuses: List<Status>,
        val loading: Boolean = false
    )

    sealed class Event {
        class Fave(val status: Status, val faved: Boolean): Event()
        class Reblog(val status: Status, val reblogged: Boolean): Event()
    }
    sealed class Effect

    private suspend fun fetch() {
        val timeline = mastodon.await().api.getPublicTimeline()
        this.state = state.copy(
            loading = false,
            statuses = state.statuses + timeline
        )
    }
}
