package social.tangent.mobile.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.viewmodel.TimelineViewModel.Effect
import social.tangent.mobile.viewmodel.TimelineViewModel.Event
import social.tangent.mobile.viewmodel.TimelineViewModel.State
import social.tangent.mobile.viewmodel.base.MobileViewModel
import social.tangent.mobile.viewmodel.base.SharedViewModel

typealias SharedTimelineViewModel = SharedViewModel<State, Event, Effect>

class TimelineViewModel(scope: CoroutineScope) :
    MobileViewModel<State, Event, Effect>(scope), KoinComponent {
    override fun initialState() = State(true, listOf())

    init {
        scope.launch {
            fetch()
        }
    }

    override suspend fun reduce(event: Event, currentState: State): State {
        return when (event) {
            is Event.Add -> {
                currentState.copy(
                    loading = false,
                    statuses = currentState.statuses + event.statuses
                )
            }
        }
    }

    data class State(
        val loading: Boolean,
        val statuses: List<Status>
    )

    sealed class Event {
        class Add(val statuses: List<Status>) : Event()
    }
    sealed class Effect

    private suspend fun fetch() {
        val timeline = debugMastodon!!.api.getPublicTimeline()
        this.send(Event.Add(timeline))
    }
}
