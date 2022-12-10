package social.tangent.mobile.fakes

import social.tangent.mobile.api.entities.Account

object accounts {

    val sarazanLocal = Account(
        id = "foobz",
        username = "asarazan",
        acct = "asarazan",
        url = "${urls.mastodon}/@SpewanMcGregor",
        displayName = "Aaron Sarazan",
        note = "",
        avatar = urls.avatar,
        avatarStatic = urls.avatar,
        header = urls.header,
        headerStatic = urls.header,
        locked = false,
        emojis = listOf(),
        createdAt = datetimes.april1984,
        lastStatusAt = dates.december2022,
        statusesCount = 1,
        followersCount = 100,
        followingCount = 101
    )

    val sarazan2 = Account(
        id = "foobzsdfdsf",
        username = "asarazan2",
        acct = "asarazan2",
        url = "${urls.mastodon}/@SpewanMcGregor2",
        displayName = "Aaron Spookyzan",
        note = "",
        avatar = urls.githubAvatar,
        avatarStatic = urls.githubAvatar,
        header = urls.header,
        headerStatic = urls.header,
        locked = false,
        emojis = listOf(),
        createdAt = datetimes.april1984,
        lastStatusAt = dates.december2022,
        statusesCount = 1,
        followersCount = 100,
        followingCount = 101
    )
}
