package social.tangent.mobile.viewmodel

import social.tangent.mobile.viewmodel.LoginViewModel.Effect
import social.tangent.mobile.viewmodel.LoginViewModel.Event
import social.tangent.mobile.viewmodel.LoginViewModel.State
import social.tangent.mobile.viewmodel.base.AndroidViewModel

class AndroidLoginViewModel : AndroidViewModel<State, Event, Effect>(::LoginViewModel)
