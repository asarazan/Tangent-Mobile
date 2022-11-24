package social.tangent.mobile.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import social.tangent.mobile.viewmodel.base.BaseViewModel

class InstanceSelectViewModel(scope: CoroutineScope) :
    BaseViewModel<InstanceSelectViewModel.State, InstanceSelectViewModel.Event, InstanceSelectViewModel.Effect>(scope) {
    override val _stateFlow: MutableStateFlow<State> = MutableStateFlow(State(""))

    override fun reduce(intent: Event, currentState: State): State {
        return when (intent) {
            is Event.SetTextEvent -> currentState.copy(text = intent.text)
        }
    }

    data class State(
        val text: String
    )
    sealed class Event {
        class SetTextEvent(val text: String) : Event()
    }
    sealed class Effect {
        class SelectInstance(val host: String) : Effect()
    }
}
