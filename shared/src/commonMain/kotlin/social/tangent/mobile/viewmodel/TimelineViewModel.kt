package social.tangent.mobile.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import social.tangent.mobile.api.entities.Account
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.tweets.StatusContent
import social.tangent.mobile.data.tweets.TimelineStorage
import social.tangent.mobile.sdk.Mastodon
import social.tangent.mobile.viewmodel.TimelineViewModel.Effect
import social.tangent.mobile.viewmodel.TimelineViewModel.Effect.Screenshot
import social.tangent.mobile.viewmodel.TimelineViewModel.Event
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Click
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Comment
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Fave
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Init
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.LoadMore
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Profile
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Reblog
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Refresh
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Share
import social.tangent.mobile.viewmodel.TimelineViewModel.State
import social.tangent.mobile.viewmodel.base.MobileViewModel
import social.tangent.mobile.viewmodel.base.SharedViewModel

typealias SharedTimelineViewModel = SharedViewModel<State, Event, Effect>

class TimelineViewModel(scope: CoroutineScope) :
    MobileViewModel<State, Event, Effect>(scope), KoinComponent {

    private var init = false
    private lateinit var storage: TimelineStorage

    val mastodon get() = storage.mastodon

    override fun initialState() = State()

    override suspend fun reduce(event: Event, currentState: State): State {
        return when (event) {
            is Init -> {
                if (!init) {
                    init = true
                    storage = TimelineStorage.create(event.mastodon, scope)
                    init()
                }
                currentState
            }
            is Fave -> {
                scope.launch {
                    storage.fave(event.status, event.faved)
                }
                currentState
            }
            is Reblog -> {
                scope.launch {
                    storage.reblog(event.status, event.reblogged)
                }
                currentState
            }
            is Comment -> {
                sendSideEffect(Effect.Comment(event.status))
                currentState
            }
            is LoadMore -> {
                scope.launch { storage.fetchFrom(event.lastStatus) }
                currentState
            }
            is Refresh -> {
                scope.launch { storage.fetch() }
                currentState
            }
            is Click -> {
                sendSideEffect(Effect.Click(event.status))
                currentState
            }
            is Profile -> {
                sendSideEffect(Effect.Profile(event.status.account))
                currentState
            }
            is Share -> {
                sendSideEffect(Screenshot(event.status))
                currentState
            }
        }
    }

    private fun init() {
        println("Initialize new TimelineViewModel")
        scope.launch {
            storage.timeline.collectLatest { timeline ->
                state = state.copy(
                    loading = false,
                    content = timeline.content
                )
            }
        }
        scope.launch {
            storage.isLoading.collectLatest {
                state = state.copy(refreshing = it)
            }
        }
        scope.launch {
            storage.fetch()
        }
        // stateFlow.onEach {
        //     println("Set State: ${it.content.count()} | ${it.loading} | ${it.refreshing}")
        // }.launchIn(scope)
    }

    data class State(
        val content: List<StatusContent> = listOf(),
        val loading: Boolean = false,
        val refreshing: Boolean = false,
    )

    sealed class Event {
        data class Fave(val status: Status, val faved: Boolean) : Event()
        data class Reblog(val status: Status, val reblogged: Boolean) : Event()
        data class Comment(val status: Status) : Event()
        data class Init(val mastodon: Mastodon) : Event()
        data class LoadMore(val lastStatus: Status) : Event()
        data class Click(val status: Status) : Event()
        data class Profile(val status: Status) : Event()
        data class Share(val status: Status) : Event()
        object Refresh : Event()
    }
    sealed class Effect {
        data class Comment(val status: Status) : Effect()
        data class Click(val status: Status) : Effect()
        data class Profile(val account: Account) : Effect()
        data class Screenshot(val status: Status): Effect()
    }
}

