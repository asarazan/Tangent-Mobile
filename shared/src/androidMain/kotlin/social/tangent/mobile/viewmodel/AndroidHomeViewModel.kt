package social.tangent.mobile.viewmodel

import social.tangent.mobile.viewmodel.HomeViewModel.Effect
import social.tangent.mobile.viewmodel.HomeViewModel.Event
import social.tangent.mobile.viewmodel.HomeViewModel.State
import social.tangent.mobile.viewmodel.base.AndroidViewModel

class AndroidHomeViewModel : AndroidViewModel<State, Event, Effect?>(::HomeViewModel)
