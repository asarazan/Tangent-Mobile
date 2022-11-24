package social.tangent.mobile.viewmodel.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Base MVI ViewModel to be used across platforms
 *
 * [S] - State: Encapsulation of data that will be represented by UI
 * [I] - Intent: The mechanism through which state is updated through this viewmodel.
 *       Any asynchronous event should update state via intents.
 * [SE] - SideEffect: One-off events that are fire and forget and should not be repeated. Useful for animations.
*/

abstract class BaseViewModel<S, I, SE>(
    /**
     * When implementing this on the android side, it's important to use
     * the [ViewModel.viewModelScope]. Otherwise you need to make sure you
     * cancel the scope before the viewmodel gets destroyed/deinited to prevent
     * memory leaks and runaway coroutines.
     */
    private val scope: CoroutineScope
) : SharedViewModel<S, I, SE> {
    protected abstract val _stateFlow: MutableStateFlow<S>

    final override val stateFlow: StateFlow<S>
        get() = _stateFlow

    final override var state: S
        get() = stateFlow.value
        private set(value) {
            _stateFlow.value = value
        }

    private val _intentFlow: MutableSharedFlow<I> = MutableSharedFlow()

    protected val intentFlow: SharedFlow<I>
        get() = _intentFlow

    private val _sideEffectFlow: MutableSharedFlow<SE> = MutableSharedFlow()

    final override val sideEffectFlow: SharedFlow<SE>
        get() = _sideEffectFlow

    init {
        _intentFlow.onEach { intent ->
            val currentState = state
            state = reduce(intent, currentState)
        }.launchIn(scope)
    }

    /**
     * Reduces the state of the ViewModel based on the intent.
     * This is where state gets updated.
     * The [state] should only be updated via this function.
     * @param intent which will update the state
     * @param currentState viewmodel that will be updated by the intent
     * @return returns updated state
     */
    protected abstract fun reduce(intent: I, currentState: S): S

    /**
     * Sends an intent to the ViewModel which will trigger [reduce] function to update the state
     */
    final override fun send(intent: I) {
        scope.launch {
            _intentFlow.emit(intent)
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
