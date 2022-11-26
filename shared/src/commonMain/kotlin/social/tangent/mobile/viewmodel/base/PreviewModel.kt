package social.tangent.mobile.viewmodel.base

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

data class PreviewModel<S, I, SE>(override val state: S) : SharedViewModel<S, I, SE> {
    override val stateFlow: StateFlow<S>
        get() = throw RuntimeException("Not implemented for preview")
    override val sideEffectFlow: SharedFlow<SE>
        get() = throw RuntimeException("Not implemented for preview")
    override fun send(intent: I) {}
}
