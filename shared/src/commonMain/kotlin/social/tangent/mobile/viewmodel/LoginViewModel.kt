package social.tangent.mobile.viewmodel

import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent
import social.tangent.mobile.sdk.Mastodon
import social.tangent.mobile.viewmodel.LoginViewModel.Event.SelectInstance
import social.tangent.mobile.viewmodel.LoginViewModel.Event.SetTextEvent
import social.tangent.mobile.viewmodel.base.MobileViewModel
import social.tangent.mobile.viewmodel.base.SharedViewModel

var debugMastodon: Mastodon? = null

typealias SharedLoginViewModel = SharedViewModel<LoginViewModel.State, LoginViewModel.Event, LoginViewModel.Effect>

class LoginViewModel(scope: CoroutineScope) :
    MobileViewModel<LoginViewModel.State, LoginViewModel.Event, LoginViewModel.Effect>(scope),
    KoinComponent {

    var mastodon: Mastodon? = null

    override fun initialState() = State()

    override suspend fun reduce(event: Event, currentState: State): State {
        return when (event) {
            is SetTextEvent -> currentState.copy(text = event.text)
            is SelectInstance -> {
                onSelect(event.onReady)
                currentState
            }
            is Event.ProvideOauthCode -> {
                onCode(event.code)
                currentState.copy(loading = true)
            }
        }
    }

    private suspend fun onCode(code: String) {
        mastodon!!.provideCode(code)
    }

    private suspend fun onSelect(onReady: (String) -> Unit) {
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

        mastodon = Mastodon.create(host)
        debugMastodon = mastodon!!
        // val timeline = Timeline(mastodon.api.getPublicTimeline(true, limit = 200))
        // val asJson = defaultJson.encodeToString(timeline)
        // print(asJson)

        val url = "${mastodon!!.domain}/oauth/authorize" +
            "?client_id=${mastodon!!.app?.clientId}" +
            "&scope=${mastodon!!.token?.scope}" +
            "&redirect_uri=${Mastodon.redirect}" +
            "&response_type=code"
        onReady(url)
    }

    data class State(
        val text: String = "",
        val loading: Boolean = false
    )
    sealed class Event {
        class SetTextEvent(val text: String) : Event()
        class SelectInstance(val onReady: (String) -> Unit) : Event()
        class ProvideOauthCode(val code: String): Event()
    }
    sealed class Effect
}
