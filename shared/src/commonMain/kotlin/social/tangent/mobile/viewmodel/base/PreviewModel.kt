package social.tangent.mobile.viewmodel.base

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

data class PreviewModel<S, I, SE>(override val state: S) : SharedViewModel<S, I, SE> {
    override val stateFlow: StateFlow<S>
        get() = MutableStateFlow(state)
    override val sideEffectFlow: SharedFlow<SE>
        get() = MutableSharedFlow()
    override fun send(event: I) {}
}
