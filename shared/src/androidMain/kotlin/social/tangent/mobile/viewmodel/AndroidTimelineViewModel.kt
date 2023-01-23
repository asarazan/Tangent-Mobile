package social.tangent.mobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import social.tangent.mobile.data.tweets.TimelineId
import social.tangent.mobile.viewmodel.TimelineViewModel.Effect
import social.tangent.mobile.viewmodel.TimelineViewModel.Event
import social.tangent.mobile.viewmodel.TimelineViewModel.State
import social.tangent.mobile.viewmodel.base.AndroidViewModel

class AndroidTimelineViewModel(
    timelineId: TimelineId,
    accountId: String
) :
    AndroidViewModel<State, Event, Effect>({ TimelineViewModel(it, timelineId, accountId) }) {
    class Factory(
        val timelineId: TimelineId,
        val accountId: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AndroidTimelineViewModel(timelineId, accountId) as T
        }
    }
}
