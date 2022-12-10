package social.tangent.mobile.viewmodel

import social.tangent.mobile.viewmodel.StatusViewModel.Effect
import social.tangent.mobile.viewmodel.StatusViewModel.Event
import social.tangent.mobile.viewmodel.StatusViewModel.State
import social.tangent.mobile.viewmodel.base.AndroidViewModel

class AndroidStatusViewModel : AndroidViewModel<State, Event, Effect>(::StatusViewModel)
