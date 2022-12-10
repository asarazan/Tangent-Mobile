package social.tangent.mobile.viewmodel

import social.tangent.mobile.viewmodel.TimelineViewModel.Effect
import social.tangent.mobile.viewmodel.TimelineViewModel.Event
import social.tangent.mobile.viewmodel.TimelineViewModel.State
import social.tangent.mobile.viewmodel.base.AndroidViewModel

class AndroidTimelineViewModel : AndroidViewModel<State, Event, Effect>(::TimelineViewModel)
