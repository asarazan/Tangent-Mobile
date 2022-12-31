package social.tangent.mobile.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.sdk.Mastodon
import social.tangent.mobile.sdk.extensions.replace
import social.tangent.mobile.viewmodel.TimelineViewModel.Effect
import social.tangent.mobile.viewmodel.TimelineViewModel.Event
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Fave
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Init
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Reblog
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Refresh
import social.tangent.mobile.viewmodel.TimelineViewModel.State
import social.tangent.mobile.viewmodel.base.MobileViewModel
import social.tangent.mobile.viewmodel.base.SharedViewModel

typealias SharedTimelineViewModel = SharedViewModel<State, Event, Effect>

class TimelineViewModel(scope: CoroutineScope) :
    MobileViewModel<State, Event, Effect>(scope), KoinComponent {

    private var init = false
    private lateinit var mastodon: Mastodon

    override fun initialState() = State(listOf(), loading = true, refreshing = false)

    override suspend fun reduce(event: Event, currentState: State): State {
        return when (event) {
            is Init -> {
                if (!init) {
                    init = true
                    mastodon = event.mastodon
                    scope.launch { fetch() }
                }
                currentState
            }
            is Fave -> currentState.copy(
                statuses = currentState.statuses.replace(mastodon.fave(event.status, event.faved))
            )
            is Reblog -> currentState.copy(
                statuses = currentState.statuses.replace(mastodon.reblog(event.status, event.reblogged))
            )
            Refresh -> {
                scope.launch { fetch() }
                currentState.copy(refreshing = true)
            }
        }
    }

    data class State(
        val statuses: List<Status>,
        val loading: Boolean = true,
        val refreshing: Boolean = false,
    )

    sealed class Event {
        class Fave(val status: Status, val faved: Boolean) : Event()
        class Reblog(val status: Status, val reblogged: Boolean) : Event()
        class Init(val mastodon: Mastodon) : Event()
        object Refresh : Event()
    }
    sealed class Effect

    private suspend fun fetch() {
        val timeline = mastodon.timeline.head()
        val json = Json.encodeToString(timeline)
        println(json)
        this.state = state.copy(
            loading = false,
            refreshing = false,
            statuses = timeline
        )
    }
}
