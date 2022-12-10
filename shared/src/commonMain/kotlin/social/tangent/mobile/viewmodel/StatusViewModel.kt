package social.tangent.mobile.viewmodel

import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.viewmodel.StatusViewModel.Effect
import social.tangent.mobile.viewmodel.StatusViewModel.Event
import social.tangent.mobile.viewmodel.StatusViewModel.State
import social.tangent.mobile.viewmodel.base.MobileViewModel
import social.tangent.mobile.viewmodel.base.SharedViewModel

typealias SharedStatusViewModel = SharedViewModel<State, Event, Effect>

class StatusViewModel(scope: CoroutineScope) :
    MobileViewModel<State, Event, Effect>(scope), KoinComponent {
    override fun initialState() = State(null)

    override suspend fun reduce(event: Event, currentState: State): State {
        return when(event) {
            is Event.SetStatus -> {
                currentState.copy(status = event.status)
            }
        }
    }

    data class State(
        val status: Status?
    )

    sealed class Event {
        class SetStatus(val status: Status) : Event()
    }
    sealed class Effect
}