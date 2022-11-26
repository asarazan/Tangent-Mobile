package social.tangent.mobile.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.KoinComponent
import social.tangent.mobile.viewmodel.base.BaseViewModel
import social.tangent.mobile.viewmodel.base.SharedViewModel

typealias SharedLoginViewModel = SharedViewModel<LoginViewModel.State, LoginViewModel.Event, LoginViewModel.Effect>

class LoginViewModel(scope: CoroutineScope) :
    BaseViewModel<LoginViewModel.State, LoginViewModel.Event, LoginViewModel.Effect>(scope),
    KoinComponent {
    override val _stateFlow: MutableStateFlow<State> = MutableStateFlow(State(""))

    override suspend fun reduce(intent: Event, currentState: State): State {
        return when (intent) {
            is Event.SetTextEvent -> currentState.copy(text = intent.text)
            is Event.SelectInstance -> {
                onSelect()
                currentState
            }
        }
    }

    private suspend fun onSelect() {
        var host = state.text
        if (host.isEmpty()) host = "https://mastodon.social"
        if (!host.startsWith("https://") && !host.startsWith("http://")) {
            host = "https://$host"
        }
        println("Connect to host $host")

        // TODO
        // val app = get<Api>().authenticateApp(host, "Tangent", )
    }

    data class State(
        val text: String = ""
    )
    sealed class Event {
        class SetTextEvent(val text: String) : Event()
        object SelectInstance : Event()
    }
    sealed class Effect
}
