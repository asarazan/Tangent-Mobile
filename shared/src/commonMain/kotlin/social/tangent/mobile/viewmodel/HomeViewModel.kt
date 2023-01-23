package social.tangent.mobile.viewmodel

import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent
import social.tangent.mobile.viewmodel.HomeViewModel.Effect
import social.tangent.mobile.viewmodel.HomeViewModel.Effect.TabReclicked
import social.tangent.mobile.viewmodel.HomeViewModel.Event
import social.tangent.mobile.viewmodel.HomeViewModel.Event.ClickTab
import social.tangent.mobile.viewmodel.HomeViewModel.State
import social.tangent.mobile.viewmodel.base.MobileViewModel
import social.tangent.mobile.viewmodel.base.SharedViewModel

typealias SharedHomeViewModel = SharedViewModel<State, Event, Effect>

class HomeViewModel(scope: CoroutineScope) :
    MobileViewModel<State, Event, Effect>(scope), KoinComponent {

    override fun initialState() = State()

    override suspend fun reduce(event: Event, currentState: State): State {
        return when (event) {
            is ClickTab -> {
                currentState.copy(tab = event.tab).also {
                    if (event.tab == currentState.tab) {
                        sendSideEffect(TabReclicked(event.tab))
                    }
                }
            }
        }
    }

    data class State(
        val tab: Tab = Tab.Home
    )

    sealed class Event {
        class ClickTab(val tab: Tab) : Event()
    }
    sealed class Effect {
        class TabReclicked(val tab: Tab) : Effect()
    }

    enum class Tab {
        Home,
        Search
    }
}