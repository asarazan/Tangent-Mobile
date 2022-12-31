package social.tangent.mobile.viewmodel

import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent
import social.tangent.mobile.sdk.Mastodon
import social.tangent.mobile.sdk.MastodonServer
import social.tangent.mobile.sdk.storage.MastodonStorage
import social.tangent.mobile.viewmodel.LoginViewModel.Effect.Complete
import social.tangent.mobile.viewmodel.LoginViewModel.Effect.GoToUrl
import social.tangent.mobile.viewmodel.LoginViewModel.Event.ProvideOauthCode
import social.tangent.mobile.viewmodel.LoginViewModel.Event.SelectInstance
import social.tangent.mobile.viewmodel.LoginViewModel.Event.SetTextEvent
import social.tangent.mobile.viewmodel.base.MobileViewModel
import social.tangent.mobile.viewmodel.base.SharedViewModel

typealias SharedLoginViewModel = SharedViewModel<LoginViewModel.State, LoginViewModel.Event, LoginViewModel.Effect>

class LoginViewModel(scope: CoroutineScope) :
    MobileViewModel<LoginViewModel.State, LoginViewModel.Event, LoginViewModel.Effect>(scope),
    KoinComponent {

    var server: MastodonServer? = null

    override fun initialState() = State()

    override suspend fun reduce(event: Event, currentState: State): State {
        return when (event) {
            is SetTextEvent -> currentState.copy(text = event.text)
            is SelectInstance -> {
                onSelect()
                currentState
            }
            is ProvideOauthCode -> {
                onCode(event.code)
                currentState.copy(loading = true)
            }
        }
    }

    private suspend fun onCode(code: String) {
        val mastodon = server!!.authenticate(code)
        MastodonStorage.set(mastodon)
        sendSideEffect(Complete(mastodon))
    }

    private suspend fun onSelect() {
        var host = state.text
        if (host.isEmpty()) {
            host = "https://mastodon.social/"
        }
        if (!host.startsWith("https://") && !host.startsWith("http://")) {
            host = "https://$host"
        }
        if (!host.endsWith("/")) {
            host = "$host/"
        }

        println("Connect to host $host")
        server = MastodonServer.acquire(host)

        val url = "${server!!.domain}/oauth/authorize" +
            "?client_id=${server!!.app.clientId}" +
            "&scope=${server!!.token.scope}" +
            "&redirect_uri=${MastodonServer.redirect}" +
            "&response_type=code"
        sendSideEffect(GoToUrl(url))
    }

    data class State(
        val text: String = "",
        val loading: Boolean = false
    )
    sealed class Event {
        object SelectInstance : Event()
        class SetTextEvent(val text: String) : Event()
        class ProvideOauthCode(val code: String): Event()
    }
    sealed class Effect {
        class GoToUrl(val url: String) : Effect()
        class Complete(val mastodon: Mastodon) : Effect()
    }
}
