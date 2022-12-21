package social.tangent.mobile.viewmodel.base

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/***
 * Public functions we need to expose for use with [MobileViewModel]
 */
interface SharedViewModel<State, Event, SideEffect> {
    val state: State
    val stateFlow: StateFlow<State>
    val sideEffectFlow: SharedFlow<SideEffect>
    fun send(event: Event)
}
