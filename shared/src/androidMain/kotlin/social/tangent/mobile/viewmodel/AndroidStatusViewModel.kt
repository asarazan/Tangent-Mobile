package social.tangent.mobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.sdk.Mastodon
import social.tangent.mobile.viewmodel.StatusViewModel.Effect
import social.tangent.mobile.viewmodel.StatusViewModel.Event
import social.tangent.mobile.viewmodel.StatusViewModel.State
import social.tangent.mobile.viewmodel.base.AndroidViewModel

class AndroidStatusViewModel(
    mastodon: Mastodon,
    status: Status
) :
    AndroidViewModel<State, Event, Effect>({ StatusViewModel(it, mastodon, status) })
{
    class Factory(
        val mastodon: Mastodon,
        val status: Status
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AndroidStatusViewModel(mastodon, status) as T
        }
    }
}
