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
    me: String
) :
    AndroidViewModel<State, Event, Effect>({ TimelineViewModel(it, timelineId, me) }) {
    class Factory(
        val timelineId: TimelineId,
        val me: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AndroidTimelineViewModel(timelineId, me) as T
        }
    }
}
