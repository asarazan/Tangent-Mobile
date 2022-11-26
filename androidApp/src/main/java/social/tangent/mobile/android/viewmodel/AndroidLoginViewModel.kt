package social.tangent.mobile.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import social.tangent.mobile.viewmodel.LoginViewModel
import social.tangent.mobile.viewmodel.base.SharedViewModel

class AndroidLoginViewModel :
    ViewModel(),
    KoinComponent,
    SharedViewModel<LoginViewModel.State, LoginViewModel.Event, LoginViewModel.Effect> {
    private val vm = LoginViewModel(viewModelScope)

    override val state: LoginViewModel.State
        get() = vm.state
    override val stateFlow: StateFlow<LoginViewModel.State>
        get() = vm.stateFlow
    override val sideEffectFlow: SharedFlow<LoginViewModel.Effect>
        get() = vm.sideEffectFlow

    override fun send(intent: LoginViewModel.Event) {
        vm.send(intent)
    }
}
