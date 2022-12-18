package social.tangent.mobile.viewmodel.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Base MVI ViewModel to be used across platforms
 *
 * [S] - State: Encapsulation of data that will be represented by UI
 * [E] - Intent: The mechanism through which state is updated through this viewmodel.
 *       Any asynchronous event should update state via intents.
 * [SE] - SideEffect: One-off events that are fire and forget and should not be repeated. Useful for animations.
*/

abstract class MobileViewModel<S, E, SE>(
    /**
     * When implementing this on the android side, it's important to use
     * the [ViewModel.viewModelScope]. Otherwise you need to make sure you
     * cancel the scope before the viewmodel gets destroyed/deinited to prevent
     * memory leaks and runaway coroutines.
     */
    protected val scope: CoroutineScope
) : SharedViewModel<S, E, SE> {
    companion object {}

    private val _stateFlow: MutableStateFlow<S> by lazy {
        MutableStateFlow(initialState())
    }

    final override val stateFlow: StateFlow<S>
        get() = _stateFlow

    final override var state: S
        get() = stateFlow.value
        protected set(value) {
            _stateFlow.value = value
        }

    private val _eventFlow: MutableSharedFlow<E> = MutableSharedFlow()

    protected val intentFlow: SharedFlow<E>
        get() = _eventFlow

    private val _sideEffectFlow: MutableSharedFlow<SE> = MutableSharedFlow()

    final override val sideEffectFlow: SharedFlow<SE>
        get() = _sideEffectFlow

    init {
        _eventFlow.onEach { intent ->
            val currentState = state
            state = reduce(intent, currentState)
        }.launchIn(scope)
    }

    /**
     * Reduces the state of the ViewModel based on the intent.
     * This is where state gets updated.
     * The [state] should only be updated via this function.
     * @param event which will update the state
     * @param currentState viewmodel that will be updated by the intent
     * @return returns updated state
     */
    protected abstract suspend fun reduce(event: E, currentState: S): S

    /**
     * Create first instance of state
     */
    protected abstract fun initialState(): S

    /**
     * Sends an intent to the ViewModel which will trigger [reduce] function to update the state
     */
    final override fun send(event: E) {
        scope.launch {
            _eventFlow.emit(event)
        }
    }

    protected fun sendSideEffect(sideEffect: SE) {
        scope.launch {
            _sideEffectFlow.emit(sideEffect)
        }
    }

    fun onDestroy() {
        scope.cancel()
    }
}
