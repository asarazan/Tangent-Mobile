package social.tangent.mobile.data.tweets

import kotlinx.coroutines.flow.SharedFlow

interface TweetRepo {
    val isFetching: Boolean
    suspend fun tweets(): SharedFlow<TweetSection>
    suspend fun fetch()
}