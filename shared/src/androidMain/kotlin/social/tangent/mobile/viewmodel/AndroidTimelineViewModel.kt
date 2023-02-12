package social.tangent.mobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import social.tangent.mobile.data.tweets.timelines.TimelineKind
import social.tangent.mobile.viewmodel.TimelineViewModel.Effect
import social.tangent.mobile.viewmodel.TimelineViewModel.Event
import social.tangent.mobile.viewmodel.TimelineViewModel.State
import social.tangent.mobile.viewmodel.base.AndroidViewModel

class AndroidTimelineViewModel(
    timelineKind: TimelineKind,
    me: String
) :
    AndroidViewModel<State, Event, Effect>({ TimelineViewModel(it, timelineKind, me) }) {
    class Factory(
        val timelineKind: TimelineKind,
        val me: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AndroidTimelineViewModel(timelineKind, me) as T
        }
    }
}
