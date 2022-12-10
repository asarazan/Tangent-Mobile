package social.tangent.mobile.fakes

import social.tangent.mobile.api.entities.Status

val fakeToot1 = Status(
    id = "foobar",
    uri = "",
    createdAt = datetimes.december2022,
    account = accounts.sarazanLocal,
    content = content.lorem,
    visibility = Status.Visibility.PUBLIC,
    sensitive = false,
    spoilerText = "",
    mediaAttachments = listOf(),
    mentions = listOf(),
    tags = listOf(),
    emojis = listOf(),
    reblogsCount = 1,
    favouritesCount = 5,
    repliesCount = 0
)