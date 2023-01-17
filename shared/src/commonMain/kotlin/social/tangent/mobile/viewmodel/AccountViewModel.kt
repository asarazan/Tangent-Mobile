package social.tangent.mobile.viewmodel

import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent
import social.tangent.mobile.api.entities.Account
import social.tangent.mobile.sdk.Mastodon
import social.tangent.mobile.viewmodel.AccountViewModel.Effect
import social.tangent.mobile.viewmodel.AccountViewModel.Event
import social.tangent.mobile.viewmodel.AccountViewModel.State
import social.tangent.mobile.viewmodel.base.MobileViewModel
import social.tangent.mobile.viewmodel.base.SharedViewModel

typealias SharedAccountViewModel = SharedViewModel<State, Event, Effect>

class AccountViewModel(scope: CoroutineScope, private val mastodon: Mastodon, private val initialState: State) :
    MobileViewModel<State, Event, Effect>(scope), KoinComponent
{
    override fun initialState() = initialState

    override suspend fun reduce(event: Event, currentState: State): State {
        return TODO()
    }

    data class State(
        val account: Account
    )

    sealed class Event
    sealed class Effect
}