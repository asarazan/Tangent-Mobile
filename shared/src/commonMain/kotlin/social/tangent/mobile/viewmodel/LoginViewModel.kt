package social.tangent.mobile.viewmodel

import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent
import social.tangent.mobile.sdk.Mastodon
import social.tangent.mobile.viewmodel.base.MobileViewModel
import social.tangent.mobile.viewmodel.base.SharedViewModel

typealias SharedLoginViewModel = SharedViewModel<LoginViewModel.State, LoginViewModel.Event, LoginViewModel.Effect>

class LoginViewModel(scope: CoroutineScope) :
    MobileViewModel<LoginViewModel.State, LoginViewModel.Event, LoginViewModel.Effect>(scope),
    KoinComponent {

    override fun initialState() = State("")

    override suspend fun reduce(event: Event, currentState: State): State {
        return when (event) {
            is Event.SetTextEvent -> currentState.copy(text = event.text)
            is Event.SelectInstance -> {
                onSelect(event.onReady)
                currentState
            }
        }
    }

    private suspend fun onSelect(onReady: (String) -> Unit) {
        var host = state.text
        if (host.isEmpty()) host = "https://mastodon.social/" +
            ""
        if (!host.startsWith("https://") && !host.startsWith("http://")) {
            host = "https://$host"
        }
        if (!host.endsWith("/")) {
            host = "$host/"
        }
        println("Connect to host $host")

        val mastodon = Mastodon.create(host)
        val url = "${mastodon.domain}/oauth/authorize" +
            "?client_id=${mastodon.app.clientId}" +
            "&scope=${mastodon.token.scope}" +
            "&redirect_uri=${Mastodon.redirect}" +
            "&response_type=code"
        onReady(url)
    }

    data class State(
        val text: String = ""
    )
    sealed class Event {
        class SetTextEvent(val text: String) : Event()
        class SelectInstance(val onReady: (String) -> Unit) : Event()
    }
    sealed class Effect
}
