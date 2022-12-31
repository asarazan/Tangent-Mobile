package social.tangent.mobile.viewmodel

import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent
import social.tangent.mobile.sdk.Mastodon
import social.tangent.mobile.viewmodel.HomeViewModel.Effect
import social.tangent.mobile.viewmodel.HomeViewModel.Event
import social.tangent.mobile.viewmodel.HomeViewModel.Event.Init
import social.tangent.mobile.viewmodel.HomeViewModel.State
import social.tangent.mobile.viewmodel.base.MobileViewModel
import social.tangent.mobile.viewmodel.base.SharedViewModel

typealias SharedHomeViewModel = SharedViewModel<State, Event, Effect>

class HomeViewModel(scope: CoroutineScope) :
    MobileViewModel<State, Event, Effect>(scope), KoinComponent {

    override fun initialState() = State()

    override suspend fun reduce(event: Event, currentState: State): State {
        return when (event) {
            is Init -> {
                currentState.copy(mastodon = event.mastodon)
            }
        }
    }

    data class State(
        val mastodon: Mastodon? = null
    )

    sealed class Event {
        class Init(val mastodon: Mastodon) : Event()
    }
    sealed class Effect
}