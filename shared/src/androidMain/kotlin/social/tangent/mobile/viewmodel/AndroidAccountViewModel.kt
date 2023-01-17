package social.tangent.mobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import social.tangent.mobile.api.entities.Account
import social.tangent.mobile.sdk.Mastodon
import social.tangent.mobile.viewmodel.AccountViewModel.Effect
import social.tangent.mobile.viewmodel.AccountViewModel.Event
import social.tangent.mobile.viewmodel.AccountViewModel.State
import social.tangent.mobile.viewmodel.base.AndroidViewModel

class AndroidAccountViewModel(mastodon: Mastodon, state: State) :
    AndroidViewModel<State, Event, Effect>({ AccountViewModel(it, mastodon, state) }) {
    class Factory(val mastodon: Mastodon, val account: Account) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val state = State(account)
            return AndroidAccountViewModel(mastodon, state) as T
        }
    }
}
