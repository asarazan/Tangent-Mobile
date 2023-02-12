package social.tangent.mobile.data.tweets.timelines

import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.sdk.Mastodon

class AccountTimelineId(val account: String) : TimelineKind("account:$account") {
    override suspend fun fetch(mastodon: Mastodon, from: String?): List<Status> {
        return mastodon.accounts.fetchFrom(account, from)
    }
}