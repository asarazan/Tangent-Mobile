package social.tangent.mobile.viewmodel

import social.tangent.mobile.viewmodel.base.AndroidViewModel

class AndroidLoginViewModel : AndroidViewModel<
    LoginViewModel.State,
    LoginViewModel.Event,
    LoginViewModel.Effect>
(::LoginViewModel)
