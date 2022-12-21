package social.tangent.mobile.viewmodel.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

abstract class AndroidViewModel<S, E, SE>(
    creator: (CoroutineScope) -> SharedViewModel<S, E, SE>
) : ViewModel(), SharedViewModel<S, E, SE> {
    private val vm = creator(viewModelScope)
    override val state get() = vm.state
    override val stateFlow get() = vm.stateFlow
    override val sideEffectFlow get() = vm.sideEffectFlow
    override fun send(event: E) = vm.send(event)
}
