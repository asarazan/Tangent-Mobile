package social.tangent.mobile.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import social.tangent.mobile.api.entities.Account
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.tweets.StatusContent
import social.tangent.mobile.data.tweets.timelines.TimelineKind
import social.tangent.mobile.data.tweets.TimelineStorage
import social.tangent.mobile.sdk.storage.MastodonStorage
import social.tangent.mobile.viewmodel.TimelineViewModel.Effect
import social.tangent.mobile.viewmodel.TimelineViewModel.Effect.Screenshot
import social.tangent.mobile.viewmodel.TimelineViewModel.Event
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Click
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Comment
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Fave
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.LoadMore
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Profile
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Reblog
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Refresh
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.ScrollToTop
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Share
import social.tangent.mobile.viewmodel.TimelineViewModel.State
import social.tangent.mobile.viewmodel.base.MobileViewModel
import social.tangent.mobile.viewmodel.base.SharedViewModel
import kotlin.random.Random

typealias SharedTimelineViewModel = SharedViewModel<State, Event, Effect>

class TimelineViewModel(
    scope: CoroutineScope,
    private val timelineKind: TimelineKind,
    private val me: String
) : MobileViewModel<State, Event, Effect>(scope), KoinComponent {

    private val mastodon = MastodonStorage.get(me)!!
    private val storage = TimelineStorage.create(timelineKind, mastodon, scope)

    init {
        init()
    }

    override fun initialState() = State(
        me = me,
        canLoadMore = timelineKind.canLoadMore
    )

    override suspend fun reduce(event: Event, currentState: State): State {
        return when (event) {
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
                if (event.screenshot) {
                    sendSideEffect(Screenshot(event.status))
                } else {
                    sendSideEffect(Effect.Share(event.status))
                }
                currentState
            }
            is ScrollToTop -> {
                sendSideEffect(Effect.ScrollToTop())
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
    }

    data class State(
        val me: String,
        val content: List<StatusContent> = listOf(),
        val loading: Boolean = false,
        val refreshing: Boolean = false,
        val canLoadMore: Boolean = true,
    )

    sealed class Event {
        data class Fave(val status: Status, val faved: Boolean) : Event()
        data class Reblog(val status: Status, val reblogged: Boolean) : Event()
        data class Comment(val status: Status) : Event()
        data class LoadMore(val lastStatus: Status) : Event()
        data class Click(val status: Status) : Event()
        data class Profile(val status: Status) : Event()
        data class Share(val status: Status, val screenshot: Boolean) : Event()
        object ScrollToTop : Event()
        object Refresh : Event()
    }
    sealed class Effect {
        data class Comment(val status: Status) : Effect()
        data class Click(val status: Status) : Effect()
        data class Profile(val account: Account) : Effect()
        data class Screenshot(val status: Status): Effect()
        data class Share(val status: Status): Effect()
        data class ScrollToTop(val uuid: String = "${Random.nextInt()}") : Effect()
    }
}

