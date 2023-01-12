package social.tangent.mobile.api.mock

import kotlinx.coroutines.delay
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import social.tangent.mobile.api.Api
import social.tangent.mobile.api.entities.Account
import social.tangent.mobile.api.entities.Application
import social.tangent.mobile.api.entities.Attachment
import social.tangent.mobile.api.entities.Instance
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.api.entities.Token
import social.tangent.mobile.data.tweets.StatusContent
import social.tangent.mobile.viewmodel.TimelineViewModel

class MockApi(val delay: Long = 0) : Api, KoinComponent {

    companion object {
        val timeline by lazy {
            Json.decodeFromString<List<Status>>(mockTimeline).map {
                StatusContent(it.id, it, false)
            }
        }
        val fakeStatus by lazy { timeline[0].status }
        val longStatus by lazy { timeline[1].status }
        val rtlStatus by lazy { timeline[5].status }
        val reblogStatus by lazy { Json.decodeFromString<Status>(reblog) }
        val singleAttachment = listOf(
            Attachment(
                id = "foo",
                type = Attachment.Type.IMAGE,
                url = "foo",
                previewUrl = "bar",
                description = "hi"
            )
        )
    }

    override suspend fun getInstance(domain: String?): Instance {
        TODO()
    }

    override suspend fun authenticateApp(
        domain: String,
        clientName: String,
        redirectUris: String,
        scopes: String?,
        website: String?
    ): Application {
        TODO()
    }

    override suspend fun fetchOAuthToken(
        domain: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        grantType: String,
        code: String?,
        scope: String?
    ): Token {
        TODO()
    }

    override suspend fun getPublicTimeline(
        local: Boolean?,
        maxId: String?,
        sinceId: String?,
        limit: Int?
    ): List<Status> {
        if (delay > 0L) delay(delay)
        return timeline.map { it.status }
    }

    override suspend fun verifyAccountCredentials(authentication: String): Account {
        TODO()
    }

    override suspend fun getHomeTimeline(
        authentication: String,
        maxId: String?,
        sinceId: String?,
        minId: String?,
        limit: Int?
    ): List<Status> {
        TODO("Not yet implemented")
    }

    override suspend fun favourite(authentication: String, id: String): Status {
        TODO("Not yet implemented")
    }

    override suspend fun unfavourite(authentication: String, id: String): Status {
        TODO("Not yet implemented")
    }

    override suspend fun reblog(authentication: String, id: String): Status {
        TODO("Not yet implemented")
    }

    override suspend fun unreblog(authentication: String, id: String): Status {
        TODO("Not yet implemented")
    }

    override suspend fun getStatus(authentication: String, id: String): Status {
        TODO("Not yet implemented")
    }
}

val mockState by lazy {
    TimelineViewModel.State(MockApi.timeline)
}
val mockStatus by lazy {
    MockApi.fakeStatus
}

private val reblog = """{
        "id": "109610793176616268",
        "uri": "https://hachyderm.io/users/mekkaokereke/statuses/109610792452680639/activity",
        "created_at": "2022-12-31T23:06:01Z",
        "account": {
            "id": "109387287352822029",
            "username": "mekkaokereke",
            "acct": "mekkaokereke@hachyderm.io",
            "url": "https://hachyderm.io/@mekkaokereke",
            "display_name": "mekka okereke :verified:",
            "note": "<p>Building Digital Ecosystems at Google, but opinions my own. he/him. Black Lives Matter.</p>",
            "avatar": "https://cdn.masto.host/mastodongamedevplace/cache/accounts/avatars/109/387/287/352/822/029/original/af48c59c06515de6.png",
            "avatar_static": "https://cdn.masto.host/mastodongamedevplace/cache/accounts/avatars/109/387/287/352/822/029/original/af48c59c06515de6.png",
            "header": "https://cdn.masto.host/mastodongamedevplace/cache/accounts/headers/109/387/287/352/822/029/original/e2868091682b9125.png",
            "header_static": "https://cdn.masto.host/mastodongamedevplace/cache/accounts/headers/109/387/287/352/822/029/original/e2868091682b9125.png",
            "locked": false,
            "emojis": [
                {
                    "shortcode": "verified",
                    "url": "https://cdn.masto.host/mastodongamedevplace/cache/custom_emojis/images/000/156/268/original/506eb59c6265bd23.png",
                    "static_url": "https://cdn.masto.host/mastodongamedevplace/cache/custom_emojis/images/000/156/268/static/506eb59c6265bd23.png",
                    "visible_in_picker": true
                }
            ],
            "discoverable": false,
            "created_at": "2022-11-22T00:00:00Z",
            "last_status_at": "2022-12-31",
            "statuses_count": 958,
            "followers_count": 11740,
            "following_count": 767,
            "fields": [
                {
                    "name": "Blog",
                    "value": "<a href=\"https://mekka-tech.com\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">mekka-tech.com</span><span class=\"invisible\"></span></a>",
                    "verified_at": "2022-12-27T07:19:43.878Z"
                }
            ],
            "bot": false
        },
        "content": "",
        "visibility": "public",
        "sensitive": false,
        "spoiler_text": "",
        "media_attachments": [],
        "mentions": [],
        "tags": [],
        "emojis": [],
        "reblogs_count": 0,
        "favourites_count": 0,
        "replies_count": 0,
        "reblog": {
            "id": "109610695851268213",
            "uri": "https://hachyderm.io/users/inthehands/statuses/109610695801359097",
            "created_at": "2022-12-31T22:41:26Z",
            "account": {
                "id": "109320263930687952",
                "username": "inthehands",
                "acct": "inthehands@hachyderm.io",
                "url": "https://hachyderm.io/@inthehands",
                "display_name": "Paul Cantrell",
                "note": "<p>Composer, pianist, programmer, professor, rabble rouser, redhead</p><p>Computer Science at <a href=\"https://www.macalester.edu/mscs/\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://www.</span><span class=\"\">macalester.edu/mscs/</span><span class=\"invisible\"></span></a><br>(Student projects: <a href=\"https://devgarden.macalester.edu\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">devgarden.macalester.edu</span><span class=\"invisible\"></span></a>)<br>Artistic Director of <a href=\"https://newruckus.org\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">newruckus.org</span><span class=\"invisible\"></span></a><br>Freelance software dev, often with <a href=\"https://bustout.com\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">bustout.com</span><span class=\"invisible\"></span></a><br>Musical troublemaker <a href=\"https://innig.net/music/\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">innig.net/music/</span><span class=\"invisible\"></span></a></p><p>Minneapolis, MN<br>Born &amp; raised in Ft. Collins, CO<br>he/him or they/them</p><p>The heart is the toughest part of the body.<br>Tenderness is in the hands.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;— Carolyn Forché</p>",
                "avatar": "https://cdn.masto.host/mastodongamedevplace/cache/accounts/avatars/109/320/263/930/687/952/original/e30b97e2ebae41da.png",
                "avatar_static": "https://cdn.masto.host/mastodongamedevplace/cache/accounts/avatars/109/320/263/930/687/952/original/e30b97e2ebae41da.png",
                "header": "https://cdn.masto.host/mastodongamedevplace/cache/accounts/headers/109/320/263/930/687/952/original/997c54cb8e5e3037.jpeg",
                "header_static": "https://cdn.masto.host/mastodongamedevplace/cache/accounts/headers/109/320/263/930/687/952/original/997c54cb8e5e3037.jpeg",
                "locked": false,
                "emojis": [],
                "discoverable": true,
                "created_at": "2022-11-10T00:00:00Z",
                "last_status_at": "2022-12-31",
                "statuses_count": 1944,
                "followers_count": 779,
                "following_count": 327,
                "fields": [
                    {
                        "name": "Personal site",
                        "value": "<a href=\"https://innig.net\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">innig.net</span><span class=\"invisible\"></span></a>",
                        "verified_at": "2022-12-17T12:28:24.210Z"
                    }
                ],
                "bot": false
            },
            "content": "<p><span class=\"h-card\"><a href=\"https://mastodon.art/@secretsloth\" class=\"u-url mention\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">@<span>secretsloth</span></a></span> <span class=\"h-card\"><a href=\"https://hachyderm.io/@mekkaokereke\" class=\"u-url mention\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">@<span>mekkaokereke</span></a></span> People nationally don’t quite realize that this is one of Ilhan Omar’s big strengths: she’s run a hell of a turnout operation here in MSP. She lifts other Dem candidates up and down the ballot. This is one reason why there was a concerted effort to primary her this year, why right-wing ${'$'} found its way to her allegedly Dem opponent. Nearly worked, too.</p>",
            "visibility": "unlisted",
            "sensitive": true,
            "spoiler_text": "US elections, voter suppression, demographics",
            "media_attachments": [],
            "mentions": [
                {
                    "id": "109329469170766145",
                    "username": "secretsloth",
                    "acct": "secretsloth@mastodon.art",
                    "url": "https://mastodon.art/@secretsloth"
                },
                {
                    "id": "109387287352822029",
                    "username": "mekkaokereke",
                    "acct": "mekkaokereke@hachyderm.io",
                    "url": "https://hachyderm.io/@mekkaokereke"
                }
            ],
            "tags": [],
            "emojis": [],
            "reblogs_count": 1,
            "favourites_count": 0,
            "replies_count": 1,
            "url": "https://hachyderm.io/@inthehands/109610695801359097",
            "in_reply_to_id": "109610645089116149",
            "in_reply_to_account_id": "109329469170766145",
            "language": "en",
            "favourited": false,
            "reblogged": false,
            "muted": false,
            "bookmarked": false
        },
        "favourited": false,
        "reblogged": false,
        "muted": false,
        "bookmarked": false
    }"""
private val mockTimelineNew = ""
private val mockTimeline = """
    [
            {
                "id": "109491892650690895",
                "uri": "https://vmst.io/users/TheMadIrishman/statuses/109491892021377277",
                "created_at": "2022-12-10T23:08:04Z",
                "account": {
                    "id": "109367544699336696",
                    "username": "TheMadIrishman",
                    "acct": "TheMadIrishman@vmst.io",
                    "url": "https://vmst.io/@TheMadIrishman",
                    "display_name": "TheMadIrishman",
                    "note": "<p>Twitch streamer.  I play good games with \"bad\" graphics.  I occasionally talk about other non-game related nonsense.</p>",
                    "avatar": "https://files.mastodon.social/cache/accounts/avatars/109/367/544/699/336/696/original/88fb2ffca887709b.jpg",
                    "avatar_static": "https://files.mastodon.social/cache/accounts/avatars/109/367/544/699/336/696/original/88fb2ffca887709b.jpg",
                    "header": "https://static-cdn.mastodon.social/headers/original/missing.png",
                    "header_static": "https://static-cdn.mastodon.social/headers/original/missing.png",
                    "locked": false,
                    "emojis": [
                    ],
                    "discoverable": true,
                    "created_at": "2022-11-18T00:00:00Z",
                    "last_status_at": "2022-12-10",
                    "statuses_count": 54,
                    "followers_count": 18,
                    "following_count": 18,
                    "fields": [
                        {
                            "name": "Pronouns:",
                            "value": "He/Him"
                        },
                        {
                            "name": "Twitch:",
                            "value": "<a href=\"https://www.twitch.tv/themadirishman123\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://www.</span><span class=\"\">twitch.tv/themadirishman123</span><span class=\"invisible\"></span></a>"
                        }
                    ],
                    "bot": false
                },
                "content": "<p>Spending my Saturday evening editing YouTube videos.  I’m not great at it, but every video I make is my own, and it makes me happy!  <a href=\"https://vmst.io/tags/youtube\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>youtube</span></a> <a href=\"https://vmst.io/tags/editing\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>editing</span></a> <a href=\"https://vmst.io/tags/dwarffortress\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>dwarffortress</span></a> <a href=\"https://vmst.io/tags/saturdayselfie\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>saturdayselfie</span></a></p>",
                "visibility": "public",
                "sensitive": false,
                "spoiler_text": "",
                "media_attachments": [
                    {
                        "id": "109491892087513647",
                        "type": "image",
                        "url": "https://files.mastodon.social/cache/media_attachments/files/109/491/892/087/513/647/original/56ca0d41c73df9a3.png",
                        "preview_url": "https://files.mastodon.social/cache/media_attachments/files/109/491/892/087/513/647/small/56ca0d41c73df9a3.png",
                        "remote_url": "https://cdn.vmst.io/media_attachments/files/109/491/882/429/007/203/original/1c6f26c90f1899e2.png",
                        "meta": {
                            "original": {
                                "width": 1152,
                                "height": 1536,
                                "size": "1152x1536",
                                "aspect": 0.75
                            },
                            "small": {
                                "width": 416,
                                "height": 554,
                                "size": "416x554",
                                "aspect": 0.7509025270758123
                            }
                        },
                        "description": "Dum dum giving a thumbs up in front of his computer monitor.",
                        "blurhash": "UGFr9NK79Z#j_Mn+s9s,=U,,tSAJvxRiozxu"
                    }
                ],
                "mentions": [
                ],
                "tags": [
                    {
                        "name": "youtube",
                        "url": "https://mastodon.social/tags/youtube"
                    },
                    {
                        "name": "editing",
                        "url": "https://mastodon.social/tags/editing"
                    },
                    {
                        "name": "dwarffortress",
                        "url": "https://mastodon.social/tags/dwarffortress"
                    },
                    {
                        "name": "saturdayselfie",
                        "url": "https://mastodon.social/tags/saturdayselfie"
                    }
                ],
                "emojis": [
                ],
                "reblogs_count": 0,
                "favourites_count": 0,
                "replies_count": 0,
                "url": "https://vmst.io/@TheMadIrishman/109491892021377277",
                "language": "en"
            },
            {
                "id": "109491892628532347",
                "uri": "https://bookwyrm.social/user/DerekCaelin/quotation/574320",
                "created_at": "2022-12-10T00:36:17.664Z",
                "account": {
                    "id": "107107489402903210",
                    "username": "DerekCaelin",
                    "acct": "DerekCaelin@bookwyrm.social",
                    "url": "https://bookwyrm.social/user/DerekCaelin",
                    "display_name": "Derek Caelin",
                    "note": "Seeking a Solarpunk Future\n\nClimate Feminist | Biodiversity | Open Source Software | Civic Tech | Games | Justice | Regenerative Ag | Green Energy | He/Him/His.",
                    "avatar": "https://files.mastodon.social/cache/accounts/avatars/107/107/489/402/903/210/original/3afc0b48abbc407c.jpg",
                    "avatar_static": "https://files.mastodon.social/cache/accounts/avatars/107/107/489/402/903/210/original/3afc0b48abbc407c.jpg",
                    "header": "https://static-cdn.mastodon.social/headers/original/missing.png",
                    "header_static": "https://static-cdn.mastodon.social/headers/original/missing.png",
                    "locked": false,
                    "emojis": [
                    ],
                    "discoverable": true,
                    "created_at": "2021-10-15T00:00:00Z",
                    "last_status_at": "2022-12-10",
                    "statuses_count": 466,
                    "followers_count": 9,
                    "following_count": 0,
                    "fields": [
                    ],
                    "bot": false
                },
                "content": "<p>\"While organised politics or the “moral adventure” of an individual makes for engaging narratives, decentralised action and cooperation do not always lend themselves to familiar modes of storytelling. Also the violence that we inflict on the living and non-living by treating them as cogs in the wheels of progress, be it through capitalist production or a centrally commanded system, happens over extended time periods and the processes through which the more-than-human world responds, revealing our connections with it, can be even slower.\nStories which try to highlight these connections or engage with the slowness of the response on a planetary scale have to employ special narrative strategies to hold the reader’s attention. One way to approach the issue of slowness and lengthy time periods is by plotting through narrative leaps across tipping points or catastrophes like the coronavirus pandemic. These are the points of rupture, where an equilibrium is lost and the natural world starts to transform quickly and its impact speeds up the story.\"</p> <p>-- <a href=\"https://bookwyrm.social/book/282378\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">\"Multispecies Cities\"</a></p><p>It's interesting to try and find a counterpoint to this theory that decentralized action and cooperation are unfamiliar forms of storytelling. The examples that first come to mind are superhero movies, but most of the collaboration there is in doing violence (occasionally the scientists will invent something to move the plot forward). Gradual collaboration over time for change... Maybe Kim Stanley Robinson's \"Mars\" trilogy?</p>",
                "visibility": "public",
                "sensitive": false,
                "spoiler_text": "",
                "media_attachments": [
                    {
                        "id": "109491892480279554",
                        "type": "image",
                        "url": "https://files.mastodon.social/cache/media_attachments/files/109/491/892/480/279/554/original/a41ba5ba31169859.jpg",
                        "preview_url": "https://files.mastodon.social/cache/media_attachments/files/109/491/892/480/279/554/small/a41ba5ba31169859.jpg",
                        "remote_url": "https://bookwyrm-social.sfo3.digitaloceanspaces.com/images/previews/covers/282378-82af71a4-e81f-48d5-992e-b1ab83dc1b6f.jpg",
                        "meta": {
                            "original": {
                                "width": 1200,
                                "height": 630,
                                "size": "1200x630",
                                "aspect": 1.9047619047619047
                            },
                            "small": {
                                "width": 662,
                                "height": 348,
                                "size": "662x348",
                                "aspect": 1.9022988505747127
                            }
                        },
                        "description": "Multispecies Cities (Paperback, 2021, World Weaver Press)",
                        "blurhash": "UMPZiW00RQxvx]xvt8ae?bxvR*jrRiM{Rjog"
                    }
                ],
                "mentions": [
                ],
                "tags": [
                ],
                "emojis": [
                ],
                "reblogs_count": 0,
                "favourites_count": 0,
                "replies_count": 0,
                "url": "https://bookwyrm.social/user/DerekCaelin/quotation/574320"
            },
            {
                "id": "109491892543352426",
                "uri": "https://terra.incognita.net/users/blackqueeriroh/statuses/109491892282762860",
                "created_at": "2022-12-10T23:08:08Z",
                "account": {
                    "id": "108194295363176303",
                    "username": "blackqueeriroh",
                    "acct": "blackqueeriroh@terra.incognita.net",
                    "url": "https://terra.incognita.net/@blackqueeriroh",
                    "display_name": "Bryan (they/them)",
                    "note": "<p>fat, black, trans, disabled, loud, kind | 1/5 @unsolicitedftb | CEO @deltalambdaphi | Board @heyokayso |🖖🏾🏳️‍⚧️🎥📚🎙️🎹</p>",
                    "avatar": "https://files.mastodon.social/cache/accounts/avatars/108/194/295/363/176/303/original/8648fb0c587fdcf3.png",
                    "avatar_static": "https://files.mastodon.social/cache/accounts/avatars/108/194/295/363/176/303/original/8648fb0c587fdcf3.png",
                    "header": "https://static-cdn.mastodon.social/headers/original/missing.png",
                    "header_static": "https://static-cdn.mastodon.social/headers/original/missing.png",
                    "locked": false,
                    "emojis": [
                    ],
                    "discoverable": true,
                    "created_at": "2022-04-25T00:00:00Z",
                    "last_status_at": "2022-12-10",
                    "statuses_count": 908,
                    "followers_count": 467,
                    "following_count": 610,
                    "fields": [
                        {
                            "name": "Podcast!",
                            "value": "https://link.tree/unsolicitedftb"
                        },
                        {
                            "name": "Twitter",
                            "value": "<a href=\"https://twitter.com/blackqueeriroh\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">twitter.com/blackqueeriroh</span><span class=\"invisible\"></span></a>"
                        },
                        {
                            "name": "Instagram",
                            "value": "<a href=\"https://www.instagram.com/blackqueeriroh\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://www.</span><span class=\"\">instagram.com/blackqueeriroh</span><span class=\"invisible\"></span></a>"
                        }
                    ],
                    "bot": false
                },
                "content": "<p>Hi friends - is there anyone who lives in Mexico City that might be able to help out with packing and getting someone to the airport tomorrow ? PLEASE DM me only.</p>",
                "visibility": "public",
                "sensitive": false,
                "spoiler_text": "",
                "media_attachments": [
                ],
                "mentions": [
                ],
                "tags": [
                ],
                "emojis": [
                ],
                "reblogs_count": 0,
                "favourites_count": 0,
                "replies_count": 0,
                "url": "https://terra.incognita.net/@blackqueeriroh/109491892282762860",
                "language": "en"
            },
            {
                "id": "109491892538621377",
                "uri": "https://mas.to/users/koraydiyebirisi/statuses/109491892520465408",
                "created_at": "2022-12-10T23:08:12Z",
                "account": {
                    "id": "109308613993950763",
                    "username": "koraydiyebirisi",
                    "acct": "koraydiyebirisi@mas.to",
                    "url": "https://mas.to/@koraydiyebirisi",
                    "display_name": "koraydiyebirisi",
                    "note": "<p>“Kurbağalara bakmaktan geliyorum”</p>",
                    "avatar": "https://files.mastodon.social/cache/accounts/avatars/109/308/613/993/950/763/original/1e1fd97a4e6af613.jpeg",
                    "avatar_static": "https://files.mastodon.social/cache/accounts/avatars/109/308/613/993/950/763/original/1e1fd97a4e6af613.jpeg",
                    "header": "https://files.mastodon.social/cache/accounts/headers/109/308/613/993/950/763/original/6e82eaa1c180de46.jpg",
                    "header_static": "https://files.mastodon.social/cache/accounts/headers/109/308/613/993/950/763/original/6e82eaa1c180de46.jpg",
                    "locked": false,
                    "emojis": [
                    ],
                    "discoverable": true,
                    "created_at": "2022-11-08T00:00:00Z",
                    "last_status_at": "2022-12-10",
                    "statuses_count": 57,
                    "followers_count": 8,
                    "following_count": 15,
                    "fields": [
                        {
                            "name": "E-POSTA",
                            "value": "mail@koraydiyebirisi.net"
                        },
                        {
                            "name": "WEB",
                            "value": "<a href=\"https://koraydiyebirisi.net\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">koraydiyebirisi.net</span><span class=\"invisible\"></span></a>"
                        }
                    ],
                    "bot": false
                },
                "content": "<p>TSK’nın başka ülkelere asker göndermesine de ülkemizde yabancı asker bulundurulmasına da karşı duruyoruz.<br><a href=\"https://mas.to/tags/NoFlyZone4RojavaNow\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>NoFlyZone4RojavaNow</span></a></p>",
                "visibility": "public",
                "sensitive": false,
                "spoiler_text": "",
                "media_attachments": [
                ],
                "mentions": [
                ],
                "tags": [
                    {
                        "name": "noflyzone4rojavanow",
                        "url": "https://mastodon.social/tags/noflyzone4rojavanow"
                    }
                ],
                "emojis": [
                ],
                "reblogs_count": 0,
                "favourites_count": 0,
                "replies_count": 0,
                "url": "https://mas.to/@koraydiyebirisi/109491892520465408",
                "language": "tr"
            },
            {
                "id": "109491892470165300",
                "uri": "https://mastodon.cloud/users/JohnShirley2023/statuses/109491892417284790",
                "created_at": "2022-12-10T23:08:10Z",
                "account": {
                    "id": "109301038977660706",
                    "username": "JohnShirley2023",
                    "acct": "JohnShirley2023@mastodon.cloud",
                    "url": "https://mastodon.cloud/@JohnShirley2023",
                    "display_name": "John Shirley",
                    "note": "<p>Writer, chiefly of novels in several genres, and story collections; also nonfiction; movie and animation scripts, song lyrics. Speaker (TEDx and elsewhere). Futurist. Blogger. Interests in science, spirituality, the arts. Writer of science-fiction, horror, techno-thrillers, thrillers, westerns. One of the original cyberpunk novelists. Vocalist, records albums, eg \"Escape from Gravity\" with Jerry King.  Has worked in TV and movies: Co-wrote The Crow. New novel: STORMLAND. <a href=\"https://john-shirley.com\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">john-shirley.com</span><span class=\"invisible\"></span></a></p>",
                    "avatar": "https://files.mastodon.social/cache/accounts/avatars/109/301/038/977/660/706/original/724f48459f0d6cb2.png",
                    "avatar_static": "https://files.mastodon.social/cache/accounts/avatars/109/301/038/977/660/706/original/724f48459f0d6cb2.png",
                    "header": "https://files.mastodon.social/cache/accounts/headers/109/301/038/977/660/706/original/d411056f00c86d34.jpg",
                    "header_static": "https://files.mastodon.social/cache/accounts/headers/109/301/038/977/660/706/original/d411056f00c86d34.jpg",
                    "locked": false,
                    "emojis": [
                    ],
                    "discoverable": true,
                    "created_at": "2022-11-06T00:00:00Z",
                    "last_status_at": "2022-12-10",
                    "statuses_count": 1625,
                    "followers_count": 605,
                    "following_count": 573,
                    "fields": [
                        {
                            "name": "https://john-shirley.com/",
                            "value": ""
                        }
                    ],
                    "bot": false
                },
                "content": "<p><a href=\"https://consumer.healthday.com/senior-citizen-information-31/misc-aging-news-10/the-plight-of-america-s-elder-orphans-699561.html\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"ellipsis\">consumer.healthday.com/senior-</span><span class=\"invisible\">citizen-information-31/misc-aging-news-10/the-plight-of-america-s-elder-orphans-699561.html</span></a></p>",
                "visibility": "public",
                "sensitive": false,
                "spoiler_text": "",
                "media_attachments": [
                ],
                "mentions": [
                ],
                "tags": [
                ],
                "emojis": [
                ],
                "reblogs_count": 0,
                "favourites_count": 0,
                "replies_count": 0,
                "url": "https://mastodon.cloud/@JohnShirley2023/109491892417284790",
                "in_reply_to_id": "109491834710581016",
                "in_reply_to_account_id": "109301038977660706",
                "language": "en"
            },
            {
                "id": "109491892419305622",
                "uri": "https://mastodon.world/users/sarahady/statuses/109491892126966166",
                "created_at": "2022-12-10T23:08:06Z",
                "account": {
                    "id": "109377744174257668",
                    "username": "sarahady",
                    "acct": "sarahady@mastodon.world",
                    "url": "https://mastodon.world/@sarahady",
                    "display_name": "𝐒𝐚𝐫𝐚",
                    "note": "<p>قلبي مليء بما ليس يعنيك</p>",
                    "avatar": "https://files.mastodon.social/cache/accounts/avatars/109/377/744/174/257/668/original/f2438d6d39921c8e.png",
                    "avatar_static": "https://files.mastodon.social/cache/accounts/avatars/109/377/744/174/257/668/original/f2438d6d39921c8e.png",
                    "header": "https://files.mastodon.social/cache/accounts/headers/109/377/744/174/257/668/original/5641ff026dce26f6.jpeg",
                    "header_static": "https://files.mastodon.social/cache/accounts/headers/109/377/744/174/257/668/original/5641ff026dce26f6.jpeg",
                    "locked": false,
                    "emojis": [
                    ],
                    "discoverable": false,
                    "created_at": "2022-11-18T00:00:00Z",
                    "last_status_at": "2022-12-10",
                    "statuses_count": 453,
                    "followers_count": 88,
                    "following_count": 61,
                    "fields": [
                    ],
                    "bot": false
                },
                "content": "<p>قد تسيل من عين الحزن<br>إذا حدقت فيها جيدا<br>دمعة من فرح.</p><p>مروان البطوش*</p>",
                "visibility": "public",
                "sensitive": false,
                "spoiler_text": "",
                "media_attachments": [
                ],
                "mentions": [
                ],
                "tags": [
                ],
                "emojis": [
                ],
                "reblogs_count": 0,
                "favourites_count": 0,
                "replies_count": 0,
                "url": "https://mastodon.world/@sarahady/109491892126966166",
                "language": "en"
            },
            {
                "id": "109491892397326554",
                "uri": "https://botsin.space/users/this_cat_does_not_exist/statuses/109491892262261881",
                "created_at": "2022-12-10T23:08:08Z",
                "account": {
                    "id": "109387973302959549",
                    "username": "this_cat_does_not_exist",
                    "acct": "this_cat_does_not_exist@botsin.space",
                    "url": "https://botsin.space/@this_cat_does_not_exist",
                    "display_name": "This Cat Does Not Exist",
                    "note": "<p>I toot pictures of cats that don't exist from <a href=\"https://thiscatdoesnotexist.com\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">thiscatdoesnotexist.com</span><span class=\"invisible\"></span></a> every 30 minutes.</p><p>Most of the time the cats are pretty catty and adorable, but sometimes they might be kinda weird (big trypophobia warning for the really off ones).</p><p>This account is not affiliated with <a href=\"https://thiscatdoesnotexist.com\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">thiscatdoesnotexist.com</span><span class=\"invisible\"></span></a> or its creator, and as such it does not represent or reflect the views of either.</p><p>Trans Rights. 🏳️‍⚧️</p>",
                    "avatar": "https://files.mastodon.social/cache/accounts/avatars/109/387/973/302/959/549/original/ca30f9781106dad3.jpg",
                    "avatar_static": "https://files.mastodon.social/cache/accounts/avatars/109/387/973/302/959/549/original/ca30f9781106dad3.jpg",
                    "header": "https://files.mastodon.social/cache/accounts/headers/109/387/973/302/959/549/original/b43c7460291fcd72.png",
                    "header_static": "https://files.mastodon.social/cache/accounts/headers/109/387/973/302/959/549/original/b43c7460291fcd72.png",
                    "locked": false,
                    "emojis": [
                    ],
                    "discoverable": true,
                    "created_at": "2022-11-22T00:00:00Z",
                    "last_status_at": "2022-12-10",
                    "statuses_count": 861,
                    "followers_count": 68,
                    "following_count": 2,
                    "fields": [
                        {
                            "name": "Bot Creator",
                            "value": "<a href=\"https://mastodon.lol/@Deakula\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">mastodon.lol/@Deakula</span><span class=\"invisible\"></span></a>",
                            "verified_at": "2022-12-10T05:21:47.076Z"
                        },
                        {
                            "name": "Language",
                            "value": "Python"
                        },
                        {
                            "name": "Status",
                            "value": "Operational - Currently hosted on a Raspberry Pi 4 Model B"
                        }
                    ],
                    "bot": true
                },
                "content": "<p><a href=\"https://botsin.space/tags/Cat\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>Cat</span></a> <a href=\"https://botsin.space/tags/CatsOfMastodon\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>CatsOfMastodon</span></a> <a href=\"https://botsin.space/tags/Cats\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>Cats</span></a></p><p>Disclaimer: The AltText for this post generically describes the origin and type of content of the included image, but due to the limitations and automation of these images, it unfortunately cannot accurately describe each individual image.</p>",
                "visibility": "public",
                "sensitive": false,
                "spoiler_text": "",
                "media_attachments": [
                    {
                        "id": "109491892294123391",
                        "type": "image",
                        "url": "https://files.mastodon.social/cache/media_attachments/files/109/491/892/294/123/391/original/c6085eefb1977ac9.jpeg",
                        "preview_url": "https://files.mastodon.social/cache/media_attachments/files/109/491/892/294/123/391/small/c6085eefb1977ac9.jpeg",
                        "remote_url": "https://files.botsin.space/media_attachments/files/109/491/892/242/691/521/original/4c505f6dfc6c87c2.jpeg",
                        "meta": {
                            "original": {
                                "width": 512,
                                "height": 512,
                                "size": "512x512",
                                "aspect": 1.0
                            },
                            "small": {
                                "width": 480,
                                "height": 480,
                                "size": "480x480",
                                "aspect": 1.0
                            }
                        },
                        "description": "This image of a cat was pulled from https://thiscatdoesnotexist.com which provides a different AI-generated image of a cat that does not exist every time the website is visited.\n\nThese fun furry feline images generated by AI are most always of a cat's face (and sometimes neck) looking directly at the \"camera\", and often have very realistic facial features. All of the facial features can vary including eye colour/shape, ear shape, mouth shape, general colourings and markings, and overall age of the face ranging from kitten to adult. Sometimes, however, the AI that generates these cats can get things a bit wrong causing facial features to be skewed, disproportionate, or even missing in some cases.\n\nSometimes the cat is just a floating head, and sometimes the AI attempts to generate more of the cat in the midground of the image such as the back and butt; sometimes with great success causing it to appear as though the cat is sitting with a turned head, and sometimes... just as an indistinguishable furry blob.\n\nThe background of these images is usually a blurry approximation of some landscape in which only general shapes and colours can be distiguished.",
                        "blurhash": "UDE:9m0L55%10e?Hots.Iofk-qIpX4R%s;t7"
                    }
                ],
                "mentions": [
                ],
                "tags": [
                    {
                        "name": "cat",
                        "url": "https://mastodon.social/tags/cat"
                    },
                    {
                        "name": "catsofmastodon",
                        "url": "https://mastodon.social/tags/catsofmastodon"
                    },
                    {
                        "name": "cats",
                        "url": "https://mastodon.social/tags/cats"
                    }
                ],
                "emojis": [
                ],
                "reblogs_count": 0,
                "favourites_count": 0,
                "replies_count": 0,
                "url": "https://botsin.space/@this_cat_does_not_exist/109491892262261881",
                "language": "en"
            },
            {
                "id": "109491892314600595",
                "uri": "https://fedibird.com/users/another/statuses/109491892241261270",
                "created_at": "2022-12-10T23:08:07Z",
                "account": {
                    "id": "1223913",
                    "username": "another",
                    "acct": "another@fedibird.com",
                    "url": "https://fedibird.com/@another",
                    "display_name": "A. N. Other",
                    "note": "<p>昼の発言に夜になってから反応することがあります。ご了承ください。</p>",
                    "avatar": "https://files.mastodon.social/cache/accounts/avatars/001/223/913/original/eb163c4132b09f38.png",
                    "avatar_static": "https://files.mastodon.social/cache/accounts/avatars/001/223/913/original/eb163c4132b09f38.png",
                    "header": "https://files.mastodon.social/cache/accounts/headers/001/223/913/original/cff2ba09fcda5a4e.png",
                    "header_static": "https://files.mastodon.social/cache/accounts/headers/001/223/913/original/cff2ba09fcda5a4e.png",
                    "locked": false,
                    "emojis": [
                    ],
                    "discoverable": true,
                    "created_at": "2020-06-07T00:00:00Z",
                    "last_status_at": "2022-12-10",
                    "statuses_count": 356,
                    "followers_count": 48,
                    "following_count": 78,
                    "fields": [
                        {
                            "name": "🐤",
                            "value": "<a href=\"https://twitter.com/another\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">twitter.com/another</span><span class=\"invisible\"></span></a>"
                        },
                        {
                            "name": "仮別荘",
                            "value": "<a class=\"\" href=\"https://mastodon-japan.net/@another\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">mastodon-japan.net/@another</span><span class=\"invisible\"></span></a>",
                            "verified_at": "2022-11-29T09:53:28.760Z"
                        }
                    ],
                    "bot": false
                },
                "content": "<p><a href=\"https://maborotopia.github.io/about_02/\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"ellipsis\">maborotopia.github.io/about_02</span><span class=\"invisible\">/</span></a> 残念ながら、もうFedibirdを去ってしまっていた。他のアカウントも活動停止中。</p>",
                "visibility": "public",
                "sensitive": false,
                "spoiler_text": "",
                "media_attachments": [
                ],
                "mentions": [
                ],
                "tags": [
                ],
                "emojis": [
                ],
                "reblogs_count": 0,
                "favourites_count": 0,
                "replies_count": 0,
                "url": "https://fedibird.com/@another/109491892241261270",
                "language": "ja"
            },
            {
                "id": "109491892307232861",
                "uri": "https://mastodon.social/users/gdinwiddie/statuses/109491892307232861",
                "created_at": "2022-12-10T23:08:08.771Z",
                "account": {
                    "id": "109252708329707951",
                    "username": "gdinwiddie",
                    "acct": "gdinwiddie",
                    "url": "https://mastodon.social/@gdinwiddie",
                    "display_name": "George Dinwiddie",
                    "note": "<p>Software development coach and consultant ● I try to learn from everywhere. ● I follow those who enter into interesting conversations with me. ● He/him/they</p><p>Interests: <a href=\"https://mastodon.social/tags/softwareDevelopment\" class=\"mention hashtag\" rel=\"tag\">#<span>softwareDevelopment</span></a> <a href=\"https://mastodon.social/tags/VirginiaSatir\" class=\"mention hashtag\" rel=\"tag\">#<span>VirginiaSatir</span></a> <a href=\"https://mastodon.social/tags/SatirModel\" class=\"mention hashtag\" rel=\"tag\">#<span>SatirModel</span></a> <a href=\"https://mastodon.social/tags/systemsThinking\" class=\"mention hashtag\" rel=\"tag\">#<span>systemsThinking</span></a> <a href=\"https://mastodon.social/tags/humanity\" class=\"mention hashtag\" rel=\"tag\">#<span>humanity</span></a></p>",
                    "avatar": "https://files.mastodon.social/accounts/avatars/109/252/708/329/707/951/original/852b5f7e63555f35.png",
                    "avatar_static": "https://files.mastodon.social/accounts/avatars/109/252/708/329/707/951/original/852b5f7e63555f35.png",
                    "header": "https://files.mastodon.social/accounts/headers/109/252/708/329/707/951/original/878b5aad33ba5eb4.png",
                    "header_static": "https://files.mastodon.social/accounts/headers/109/252/708/329/707/951/original/878b5aad33ba5eb4.png",
                    "locked": false,
                    "emojis": [
                    ],
                    "discoverable": true,
                    "created_at": "2022-10-29T00:00:00Z",
                    "last_status_at": "2022-12-10",
                    "statuses_count": 1641,
                    "followers_count": 584,
                    "following_count": 616,
                    "fields": [
                        {
                            "name": "website",
                            "value": "<a href=\"https://idiacomputing.com/\" target=\"_blank\" rel=\"nofollow noopener noreferrer me\"><span class=\"invisible\">https://</span><span class=\"\">idiacomputing.com/</span><span class=\"invisible\"></span></a>"
                        },
                        {
                            "name": "my blog",
                            "value": "<a href=\"https://blog.gdinwiddie.com\" target=\"_blank\" rel=\"nofollow noopener noreferrer me\"><span class=\"invisible\">https://</span><span class=\"\">blog.gdinwiddie.com</span><span class=\"invisible\"></span></a>"
                        }
                    ],
                    "bot": false
                },
                "content": "<p>I keep wanting to boost the toots of <span class=\"h-card\"><a href=\"https://instinctd.com/users/beschlossdc\" class=\"u-url mention\">@<span>beschlossdc</span></a></span> but they&#39;re all posted &quot;Followers Only&quot;</p>",
                "visibility": "public",
                "sensitive": false,
                "spoiler_text": "",
                "media_attachments": [
                ],
                "application": {
                    "name": "Web"
                },
                "mentions": [
                    {
                        "id": "109362335873338818",
                        "username": "beschlossdc",
                        "acct": "beschlossdc@instinctd.com",
                        "url": "https://instinctd.com/users/beschlossdc"
                    }
                ],
                "tags": [
                ],
                "emojis": [
                ],
                "reblogs_count": 0,
                "favourites_count": 0,
                "replies_count": 0,
                "url": "https://mastodon.social/@gdinwiddie/109491892307232861",
                "language": "en"
            },
            {
                "id": "109491892221464350",
                "uri": "https://mastodon.au/users/bethanycarter/statuses/109491892041081348",
                "created_at": "2022-12-10T23:08:04Z",
                "account": {
                    "id": "109363583725482189",
                    "username": "bethanycarter",
                    "acct": "bethanycarter@mastodon.au",
                    "url": "https://mastodon.au/@bethanycarter",
                    "display_name": "Bethany Carter",
                    "note": "<p>Pen Name<br>Writer, daydreamer, notebook hoarder. Working on my first novel<br>I love dogs, coffee and books. Not necessarily in that order<br>Aussie. Gabi Gabi country. She/her</p>",
                    "avatar": "https://files.mastodon.social/cache/accounts/avatars/109/363/583/725/482/189/original/0bcd7b9ff2b1f0af.jpg",
                    "avatar_static": "https://files.mastodon.social/cache/accounts/avatars/109/363/583/725/482/189/original/0bcd7b9ff2b1f0af.jpg",
                    "header": "https://files.mastodon.social/cache/accounts/headers/109/363/583/725/482/189/original/28028d92301a4dac.jpg",
                    "header_static": "https://files.mastodon.social/cache/accounts/headers/109/363/583/725/482/189/original/28028d92301a4dac.jpg",
                    "locked": false,
                    "emojis": [
                    ],
                    "discoverable": true,
                    "created_at": "2022-11-18T00:00:00Z",
                    "last_status_at": "2022-12-10",
                    "statuses_count": 230,
                    "followers_count": 307,
                    "following_count": 412,
                    "fields": [
                    ],
                    "bot": false
                },
                "content": "<p>Hey <a href=\"https://mastodon.au/tags/writers\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>writers</span></a>!</p><p>What’s your favourite time of day to write?</p><p>My favourite time is early in the morning. Even if I only get a little done, I feel good for the rest of the day. </p><p><a href=\"https://mastodon.au/tags/WritingCommunity\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>WritingCommunity</span></a> <a href=\"https://mastodon.au/tags/AmWriting\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>AmWriting</span></a> <a href=\"https://mastodon.au/tags/AmEditing\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>AmEditing</span></a> <a href=\"https://mastodon.au/tags/WritersofMastodon\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>WritersofMastodon</span></a> <a href=\"https://mastodon.au/tags/Writing\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>Writing</span></a></p>",
                "visibility": "public",
                "sensitive": false,
                "spoiler_text": "",
                "media_attachments": [
                ],
                "mentions": [
                ],
                "tags": [
                    {
                        "name": "writers",
                        "url": "https://mastodon.social/tags/writers"
                    },
                    {
                        "name": "writingcommunity",
                        "url": "https://mastodon.social/tags/writingcommunity"
                    },
                    {
                        "name": "amWriting",
                        "url": "https://mastodon.social/tags/amWriting"
                    },
                    {
                        "name": "amediting",
                        "url": "https://mastodon.social/tags/amediting"
                    },
                    {
                        "name": "writersofmastodon",
                        "url": "https://mastodon.social/tags/writersofmastodon"
                    },
                    {
                        "name": "writing",
                        "url": "https://mastodon.social/tags/writing"
                    }
                ],
                "emojis": [
                ],
                "reblogs_count": 0,
                "favourites_count": 0,
                "replies_count": 0,
                "url": "https://mastodon.au/@bethanycarter/109491892041081348",
                "language": "en"
            },
            {
                "id": "109491892181242309",
                "uri": "https://booktoot.club/users/nagletx/statuses/109491891885827391",
                "created_at": "2022-12-10T23:08:02Z",
                "account": {
                    "id": "109316636916035953",
                    "username": "nagletx",
                    "acct": "nagletx@booktoot.club",
                    "url": "https://booktoot.club/@nagletx",
                    "display_name": "Robert Nagle",
                    "note": "<p>Litblogger (for 21+ years!), Podcast Wannabe,  Houston writer &amp;  ebook enthusiast. Diehard fanatic of Eurovision and East European culture. Founder of Personville Press.</p>",
                    "avatar": "https://files.mastodon.social/cache/accounts/avatars/109/316/636/916/035/953/original/09d4389d7de73821.jpg",
                    "avatar_static": "https://files.mastodon.social/cache/accounts/avatars/109/316/636/916/035/953/original/09d4389d7de73821.jpg",
                    "header": "https://files.mastodon.social/cache/accounts/headers/109/316/636/916/035/953/original/416da52dcaf244bd.jpg",
                    "header_static": "https://files.mastodon.social/cache/accounts/headers/109/316/636/916/035/953/original/416da52dcaf244bd.jpg",
                    "locked": false,
                    "emojis": [
                    ],
                    "discoverable": true,
                    "created_at": "2022-11-09T00:00:00Z",
                    "last_status_at": "2022-12-10",
                    "statuses_count": 32,
                    "followers_count": 14,
                    "following_count": 38,
                    "fields": [
                        {
                            "name": "Personville Press",
                            "value": "<a href=\"https://www.personvillepress.com/\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://www.</span><span class=\"\">personvillepress.com/</span><span class=\"invisible\"></span></a>"
                        },
                        {
                            "name": "Blog",
                            "value": "<a href=\"http://www.imaginaryplanet.net/weblogs/idiotprogrammer/\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">http://www.</span><span class=\"ellipsis\">imaginaryplanet.net/weblogs/id</span><span class=\"invisible\">iotprogrammer/</span></a>"
                        },
                        {
                            "name": "Ebook Store",
                            "value": "<a href=\"https://payhip.com/PersonvillePress\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">payhip.com/PersonvillePress</span><span class=\"invisible\"></span></a>"
                        },
                        {
                            "name": "Podcast",
                            "value": "<a href=\"https://musicooo.podbean.com/\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">musicooo.podbean.com/</span><span class=\"invisible\"></span></a>"
                        }
                    ],
                    "bot": false
                },
                "content": "<p>QUOTE: \" Today, the number of political prisoners in Russia is more than the total number in all of the Soviet Union at the beginning of the period of perestroika in the 1980s. \" (Jan Rachinsky of MEMORIAL group from Russia, which shared the Nobel Peace prize with similar groups from Ukraine and Belarus) <a href=\"https://www.nobelprize.org/prizes/peace/2022/memorial/lecture/\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://www.</span><span class=\"ellipsis\">nobelprize.org/prizes/peace/20</span><span class=\"invisible\">22/memorial/lecture/</span></a></p>",
                "visibility": "public",
                "sensitive": false,
                "spoiler_text": "",
                "media_attachments": [
                ],
                "mentions": [
                ],
                "tags": [
                ],
                "emojis": [
                ],
                "reblogs_count": 0,
                "favourites_count": 0,
                "replies_count": 0,
                "url": "https://booktoot.club/@nagletx/109491891885827391",
                "language": "en"
            },
            {
                "id": "109491892142491591",
                "uri": "https://qoto.org/users/gpowerf/statuses/109491892086911542",
                "created_at": "2022-12-10T23:08:05Z",
                "account": {
                    "id": "109297806438507725",
                    "username": "gpowerf",
                    "acct": "gpowerf@qoto.org",
                    "url": "https://qoto.org/@gpowerf",
                    "display_name": "Dr. Gaming Power :verified:",
                    "note": "<p>This is my fun social media account, I will post a lot of video game stuff (mostly old or cloud gaming) and some work stuff.  </p><p><a href=\"https://qoto.org/tags/gaming\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>gaming</span></a> <a href=\"https://qoto.org/tags/tech\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>tech</span></a> <a href=\"https://qoto.org/tags/latino\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>latino</span></a> <a href=\"https://qoto.org/tags/uk\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>uk</span></a></p><p>Mi aventura: <a href=\"http://gpowerf.itch.io/8-bit-island\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">http://</span><span class=\"\">gpowerf.itch.io/8-bit-island</span><span class=\"invisible\"></span></a></p>",
                    "avatar": "https://files.mastodon.social/cache/accounts/avatars/109/297/806/438/507/725/original/1162df36232a3b55.png",
                    "avatar_static": "https://files.mastodon.social/cache/accounts/avatars/109/297/806/438/507/725/original/1162df36232a3b55.png",
                    "header": "https://files.mastodon.social/cache/accounts/headers/109/297/806/438/507/725/original/d93abaa62fb33596.jpeg",
                    "header_static": "https://files.mastodon.social/cache/accounts/headers/109/297/806/438/507/725/original/d93abaa62fb33596.jpeg",
                    "locked": false,
                    "emojis": [
                        {
                            "shortcode": "verified",
                            "url": "https://files.mastodon.social/cache/custom_emojis/images/000/041/736/original/04f1f28d0bbd2ddf.png",
                            "static_url": "https://files.mastodon.social/cache/custom_emojis/images/000/041/736/static/04f1f28d0bbd2ddf.png",
                            "visible_in_picker": true
                        }
                    ],
                    "discoverable": false,
                    "created_at": "2022-11-06T00:00:00Z",
                    "last_status_at": "2022-12-10",
                    "statuses_count": 2116,
                    "followers_count": 342,
                    "following_count": 277,
                    "fields": [
                        {
                            "name": "Location:",
                            "value": "United Kingdom"
                        },
                        {
                            "name": "github",
                            "value": "<a href=\"https://github.com/gpowerf\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">github.com/gpowerf</span><span class=\"invisible\"></span></a>"
                        },
                        {
                            "name": "Intro:",
                            "value": "<a href=\"https://qoto.org/@gpowerf/109308642457913344\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"ellipsis\">qoto.org/@gpowerf/109308642457</span><span class=\"invisible\">913344</span></a>"
                        },
                        {
                            "name": "OS predilection",
                            "value": "Linux"
                        }
                    ],
                    "bot": false
                },
                "content": "<p><a href=\"https://www.bbc.co.uk/news/technology-63912116\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://www.</span><span class=\"ellipsis\">bbc.co.uk/news/technology-6391</span><span class=\"invisible\">2116</span></a></p>",
                "visibility": "public",
                "sensitive": false,
                "spoiler_text": "",
                "media_attachments": [
                ],
                "mentions": [
                ],
                "tags": [
                ],
                "emojis": [
                ],
                "reblogs_count": 0,
                "favourites_count": 0,
                "replies_count": 0,
                "url": "https://qoto.org/@gpowerf/109491892086911542"
            },
            {
                "id": "109491892134187189",
                "uri": "https://mstdn.social/users/nuopKINK/statuses/109491891913109661",
                "created_at": "2022-12-10T23:08:02Z",
                "account": {
                    "id": "109287546496414561",
                    "username": "nuopKINK",
                    "acct": "nuopKINK@mstdn.social",
                    "url": "https://mstdn.social/@nuopKINK",
                    "display_name": "nu op KINK",
                    "note": "<p>Bliep bloep, ik ben de nowplaying-robot van <br>@kinkpuntnl.</p>",
                    "avatar": "https://files.mastodon.social/cache/accounts/avatars/109/287/546/496/414/561/original/460aa09e706a57af.jpg",
                    "avatar_static": "https://files.mastodon.social/cache/accounts/avatars/109/287/546/496/414/561/original/460aa09e706a57af.jpg",
                    "header": "https://static-cdn.mastodon.social/headers/original/missing.png",
                    "header_static": "https://static-cdn.mastodon.social/headers/original/missing.png",
                    "locked": false,
                    "emojis": [
                    ],
                    "discoverable": false,
                    "created_at": "2022-11-04T00:00:00Z",
                    "last_status_at": "2022-12-10",
                    "statuses_count": 11446,
                    "followers_count": 35,
                    "following_count": 1,
                    "fields": [
                        {
                            "name": "Twitter",
                            "value": "<a href=\"https://twitter.com/nuopKINK\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">twitter.com/nuopKINK</span><span class=\"invisible\"></span></a>"
                        },
                        {
                            "name": "Last.fm",
                            "value": "<a href=\"https://last.fm/user/kinkpuntnl\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">last.fm/user/kinkpuntnl</span><span class=\"invisible\"></span></a>"
                        },
                        {
                            "name": "Luister KINK",
                            "value": "<a href=\"http://kink.nl/player/stream.kink\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">http://</span><span class=\"\">kink.nl/player/stream.kink</span><span class=\"invisible\"></span></a>"
                        }
                    ],
                    "bot": true
                },
                "content": "<p>Nu op KINK: R.E.M. - Imitation of Life</p>",
                "visibility": "public",
                "sensitive": false,
                "spoiler_text": "",
                "media_attachments": [
                ],
                "mentions": [
                ],
                "tags": [
                ],
                "emojis": [
                ],
                "reblogs_count": 0,
                "favourites_count": 0,
                "replies_count": 0,
                "url": "https://mstdn.social/@nuopKINK/109491891913109661",
                "language": "en"
            },
            {
                "id": "109491892134045186",
                "uri": "https://social.illegalpornography.com/users/MisterQuotation/statuses/109491891869809186",
                "created_at": "2022-12-10T23:08:02Z",
                "account": {
                    "id": "1129638",
                    "username": "MisterQuotation",
                    "acct": "MisterQuotation@social.illegalpornography.com",
                    "url": "https://social.illegalpornography.com/@MisterQuotation",
                    "display_name": "Mister Quotation",
                    "note": "<p>Is there a Mister Ogynist?</p>",
                    "avatar": "https://files.mastodon.social/cache/accounts/avatars/001/129/638/original/f89385b0c7aee0b0.jpg",
                    "avatar_static": "https://files.mastodon.social/cache/accounts/avatars/001/129/638/original/f89385b0c7aee0b0.jpg",
                    "header": "https://files.mastodon.social/cache/accounts/headers/001/129/638/original/725a2d48d041e265.jpg",
                    "header_static": "https://files.mastodon.social/cache/accounts/headers/001/129/638/original/725a2d48d041e265.jpg",
                    "locked": false,
                    "emojis": [
                    ],
                    "discoverable": false,
                    "created_at": "2020-02-27T00:00:00Z",
                    "last_status_at": "2022-12-10",
                    "statuses_count": 23567,
                    "followers_count": 20,
                    "following_count": 1,
                    "fields": [
                        {
                            "name": "guess what, it's",
                            "value": "<span class=\"h-card\"><a href=\"https://social.illegalpornography.com/@BestGirlGrace\" class=\"u-url mention\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">@<span>BestGirlGrace</span></a></span>"
                        }
                    ],
                    "bot": true
                },
                "content": "<p>So... is there a Mister Statement?</p>",
                "visibility": "public",
                "sensitive": false,
                "spoiler_text": "",
                "media_attachments": [
                ],
                "mentions": [
                ],
                "tags": [
                ],
                "emojis": [
                ],
                "reblogs_count": 0,
                "favourites_count": 0,
                "replies_count": 0,
                "url": "https://social.illegalpornography.com/@MisterQuotation/109491891869809186",
                "language": "en"
            },
            {
                "id": "109491892124232317",
                "uri": "https://botsin.space/users/brokennews/statuses/109491892073038729",
                "created_at": "2022-12-10T23:08:05Z",
                "account": {
                    "id": "109290185470295795",
                    "username": "brokennews",
                    "acct": "brokennews@botsin.space",
                    "url": "https://botsin.space/@brokennews",
                    "display_name": "Broken News",
                    "note": "<p>Zwei Schlagzeilen zusammengemischt sind lustiger als eine!</p>",
                    "avatar": "https://files.mastodon.social/cache/accounts/avatars/109/290/185/470/295/795/original/a3bc0dc5fb985302.png",
                    "avatar_static": "https://files.mastodon.social/cache/accounts/avatars/109/290/185/470/295/795/original/a3bc0dc5fb985302.png",
                    "header": "https://static-cdn.mastodon.social/headers/original/missing.png",
                    "header_static": "https://static-cdn.mastodon.social/headers/original/missing.png",
                    "locked": false,
                    "emojis": [
                    ],
                    "discoverable": true,
                    "created_at": "2022-11-04T00:00:00Z",
                    "last_status_at": "2022-12-10",
                    "statuses_count": 677,
                    "followers_count": 89,
                    "following_count": 0,
                    "fields": [
                        {
                            "name": "Autor",
                            "value": "<a href=\"https://wien.rocks/@oberhamsi\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">wien.rocks/@oberhamsi</span><span class=\"invisible\"></span></a>"
                        },
                        {
                            "name": "Wie?",
                            "value": "<a href=\"https://nekapuzer.at/geheimnis-des-algorithmus/\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"ellipsis\">nekapuzer.at/geheimnis-des-alg</span><span class=\"invisible\">orithmus/</span></a>"
                        },
                        {
                            "name": "Foto Quelle",
                            "value": "<a href=\"https://commons.wikimedia.org/wiki/File:Journalist_With_Pipe.jpg\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"ellipsis\">commons.wikimedia.org/wiki/Fil</span><span class=\"invisible\">e:Journalist_With_Pipe.jpg</span></a>"
                        }
                    ],
                    "bot": true
                },
                "content": "<p>Selenskyj suspendiert</p>",
                "visibility": "public",
                "sensitive": false,
                "spoiler_text": "",
                "media_attachments": [
                ],
                "mentions": [
                ],
                "tags": [
                ],
                "emojis": [
                ],
                "reblogs_count": 0,
                "favourites_count": 0,
                "replies_count": 0,
                "url": "https://botsin.space/@brokennews/109491892073038729",
                "language": "de"
            },
            {
                "id": "109491892089901227",
                "uri": "https://tkz.one/users/renasaurux_/statuses/109491892045856956",
                "created_at": "2022-12-10T23:08:04Z",
                "account": {
                    "id": "109330887864922379",
                    "username": "renasaurux_",
                    "acct": "renasaurux_@tkz.one",
                    "url": "https://tkz.one/@renasaurux_",
                    "display_name": "Renasaurux🌻",
                    "note": "<p>28 στσñσs. ☾SHOUJO は 私 の 恋 人 で す☽ <a href=\"https://tkz.one/tags/UkeSeme\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>UkeSeme</span></a> NOT, <a href=\"https://tkz.one/tags/NekoTachi\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>NekoTachi</span></a> YEP♡ MUA IN PROCESS. Higurashi como estilo de vida. Mami de Naminé y Taiga♡</p>",
                    "avatar": "https://files.mastodon.social/cache/accounts/avatars/109/330/887/864/922/379/original/22be17873b1a511a.jpg",
                    "avatar_static": "https://files.mastodon.social/cache/accounts/avatars/109/330/887/864/922/379/original/22be17873b1a511a.jpg",
                    "header": "https://files.mastodon.social/cache/accounts/headers/109/330/887/864/922/379/original/0da3fca4794618ef.jpeg",
                    "header_static": "https://files.mastodon.social/cache/accounts/headers/109/330/887/864/922/379/original/0da3fca4794618ef.jpeg",
                    "locked": false,
                    "emojis": [
                    ],
                    "discoverable": true,
                    "created_at": "2022-11-12T00:00:00Z",
                    "last_status_at": "2022-12-10",
                    "statuses_count": 285,
                    "followers_count": 199,
                    "following_count": 26,
                    "fields": [
                        {
                            "name": "Ubicación",
                            "value": "白 川 郷「Shirakawa-gō」"
                        },
                        {
                            "name": "Sitio web",
                            "value": "Instagram: renasaurux_"
                        },
                        {
                            "name": "Cumpleaños",
                            "value": "1 de Noviembre"
                        }
                    ],
                    "bot": false
                },
                "content": "<p>Aún me sigo sin creer el daño que me ha hecho esta persona y sólo piense en su bien y en lo que a ella le hace feliz, y no si yo voy a estar incómoda siendo amigas cuando hemos sido pareja y cuando siempre ha habido una atracción física y nunca nos hemos visto literalmente como sólo amigas de hecho cuando teníamos los 17/18 que ambas teníamos novio nuestras parejas no paraba de decir que querían que nos liasemos xD</p>",
                "visibility": "public",
                "sensitive": false,
                "spoiler_text": "",
                "media_attachments": [
                ],
                "mentions": [
                ],
                "tags": [
                ],
                "emojis": [
                ],
                "reblogs_count": 0,
                "favourites_count": 0,
                "replies_count": 0,
                "url": "https://tkz.one/@renasaurux_/109491892045856956",
                "language": "es"
            },
            {
                "id": "109491892041092968",
                "uri": "https://mastodon.social/users/b4st3t/statuses/109491892041092968",
                "created_at": "2022-12-10T23:08:04.710Z",
                "account": {
                    "id": "109491847122100333",
                    "username": "b4st3t",
                    "acct": "b4st3t",
                    "url": "https://mastodon.social/@b4st3t",
                    "display_name": "celia_romero",
                    "note": "<p>A song that can be a map<br />A path that is full of meaning</p>",
                    "avatar": "https://files.mastodon.social/accounts/avatars/109/491/847/122/100/333/original/e09aca95f87178ef.jpg",
                    "avatar_static": "https://files.mastodon.social/accounts/avatars/109/491/847/122/100/333/original/e09aca95f87178ef.jpg",
                    "header": "https://files.mastodon.social/accounts/headers/109/491/847/122/100/333/original/a1e6f4e8db0ee409.jpg",
                    "header_static": "https://files.mastodon.social/accounts/headers/109/491/847/122/100/333/original/a1e6f4e8db0ee409.jpg",
                    "locked": false,
                    "emojis": [
                    ],
                    "discoverable": true,
                    "created_at": "2022-12-10T00:00:00Z",
                    "last_status_at": "2022-12-10",
                    "statuses_count": 1,
                    "followers_count": 0,
                    "following_count": 0,
                    "fields": [
                    ],
                    "bot": false
                },
                "content": "",
                "visibility": "public",
                "sensitive": false,
                "spoiler_text": "",
                "media_attachments": [
                    {
                        "id": "109491891632727265",
                        "type": "image",
                        "url": "https://files.mastodon.social/media_attachments/files/109/491/891/632/727/265/original/d1fd8c9d34bffacc.jpg",
                        "preview_url": "https://files.mastodon.social/media_attachments/files/109/491/891/632/727/265/small/d1fd8c9d34bffacc.jpg",
                        "meta": {
                            "original": {
                                "width": 664,
                                "height": 903,
                                "size": "664x903",
                                "aspect": 0.7353266888150609
                            },
                            "small": {
                                "width": 412,
                                "height": 560,
                                "size": "412x560",
                                "aspect": 0.7357142857142858
                            }
                        },
                        "blurhash": "U6QTAM4U-:?a%MayRjay_2xuMyRjRQofofWB"
                    }
                ],
                "application": {
                    "name": "Mastodon for Android",
                    "website": "https://app.joinmastodon.org/android"
                },
                "mentions": [
                ],
                "tags": [
                ],
                "emojis": [
                ],
                "reblogs_count": 0,
                "favourites_count": 0,
                "replies_count": 0,
                "url": "https://mastodon.social/@b4st3t/109491892041092968",
                "language": "es"
            },
            {
                "id": "109491892022832165",
                "uri": "https://botsin.space/users/KommadiebRadio/statuses/109491891834565215",
                "created_at": "2022-12-10T23:08:01Z",
                "account": {
                    "id": "109397834667767289",
                    "username": "KommadiebRadio",
                    "acct": "KommadiebRadio@botsin.space",
                    "url": "https://botsin.space/@KommadiebRadio",
                    "display_name": "KommadiebRadio 🤖 🔊",
                    "note": "<p>The <a href=\"https://botsin.space/tags/NowPlaying\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>NowPlaying</span></a> bot of Kommadieb’s <a href=\"https://botsin.space/tags/Mixcloud\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>Mixcloud</span></a>.<br>➡️ <a href=\"https://www.mixcloud.com/kommadieb/\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://www.</span><span class=\"\">mixcloud.com/kommadieb/</span><span class=\"invisible\"></span></a></p><p>🗃️ Shownotes/Archive: <a href=\"https://pseudopost.org/~kommadieb/mixcloud/\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"ellipsis\">pseudopost.org/~kommadieb/mixc</span><span class=\"invisible\">loud/</span></a></p><p>🧑🏼‍💻 Managed by <span class=\"h-card\"><a href=\"https://mstdn.social/@kommadieb\" class=\"u-url mention\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">@<span>kommadieb</span></a></span> (comments, hints, critique? yes, please!)</p><p>🙏 Track meta data by Discogs.com</p>",
                    "avatar": "https://files.mastodon.social/cache/accounts/avatars/109/397/834/667/767/289/original/fa5820e92af86742.png",
                    "avatar_static": "https://files.mastodon.social/cache/accounts/avatars/109/397/834/667/767/289/original/fa5820e92af86742.png",
                    "header": "https://files.mastodon.social/cache/accounts/headers/109/397/834/667/767/289/original/7fbe5ec9a36360c4.png",
                    "header_static": "https://files.mastodon.social/cache/accounts/headers/109/397/834/667/767/289/original/7fbe5ec9a36360c4.png",
                    "locked": false,
                    "emojis": [
                    ],
                    "discoverable": true,
                    "created_at": "2022-11-14T00:00:00Z",
                    "last_status_at": "2022-12-10",
                    "statuses_count": 251,
                    "followers_count": 5,
                    "following_count": 1,
                    "fields": [
                    ],
                    "bot": true
                },
                "content": "<p>🔊 <a href=\"https://botsin.space/tags/NowPlaying\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>NowPlaying</span></a> on Mixcloud</p><p>🎶 PinkPantheress – Boy's a liar</p><p>🚨 LIVE: <a href=\"https://www.mixcloud.com/live/kommadieb/\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://www.</span><span class=\"\">mixcloud.com/live/kommadieb/</span><span class=\"invisible\"></span></a></p><p><a href=\"https://botsin.space/tags/Pinkpantheress\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>Pinkpantheress</span></a> <a href=\"https://botsin.space/tags/FediRadio\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>FediRadio</span></a> <a href=\"https://botsin.space/tags/MastoRadio\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>MastoRadio</span></a></p>",
                "visibility": "public",
                "sensitive": false,
                "spoiler_text": "",
                "media_attachments": [
                ],
                "mentions": [
                ],
                "tags": [
                    {
                        "name": "nowplaying",
                        "url": "https://mastodon.social/tags/nowplaying"
                    },
                    {
                        "name": "pinkpantheress",
                        "url": "https://mastodon.social/tags/pinkpantheress"
                    },
                    {
                        "name": "fediradio",
                        "url": "https://mastodon.social/tags/fediradio"
                    },
                    {
                        "name": "mastoradio",
                        "url": "https://mastodon.social/tags/mastoradio"
                    }
                ],
                "emojis": [
                ],
                "reblogs_count": 0,
                "favourites_count": 0,
                "replies_count": 0,
                "url": "https://botsin.space/@KommadiebRadio/109491891834565215",
                "language": "de"
            },
            {
                "id": "109491891920098152",
                "uri": "https://techhub.social/users/NSFVoyager2/statuses/109491891777243775",
                "created_at": "2022-12-10T23:08:00Z",
                "account": {
                    "id": "109368833515922367",
                    "username": "NSFVoyager2",
                    "acct": "NSFVoyager2@techhub.social",
                    "url": "https://techhub.social/@NSFVoyager2",
                    "display_name": "NSFVoyager2",
                    "note": "<p>Semper peregrinus inter astra — a well-informed unofficial account full of unauthorized jargon. Questions welcome, but answers will take 2x light-travel time... Not a bot!</p>",
                    "avatar": "https://files.mastodon.social/cache/accounts/avatars/109/368/833/515/922/367/original/6a2852e1b09be2e5.gif",
                    "avatar_static": "https://files.mastodon.social/cache/accounts/avatars/109/368/833/515/922/367/static/6a2852e1b09be2e5.png",
                    "header": "https://files.mastodon.social/cache/accounts/headers/109/368/833/515/922/367/original/c07aacb26972bce0.jpg",
                    "header_static": "https://files.mastodon.social/cache/accounts/headers/109/368/833/515/922/367/original/c07aacb26972bce0.jpg",
                    "locked": false,
                    "emojis": [
                    ],
                    "discoverable": true,
                    "created_at": "2022-11-19T00:00:00Z",
                    "last_status_at": "2022-12-10",
                    "statuses_count": 50,
                    "followers_count": 1548,
                    "following_count": 27,
                    "fields": [
                    ],
                    "bot": false
                },
                "content": "<p>I am checking that my instruction set is correct. Next to be read out is my Flight &amp; Data Subsystem Memory : END AACS:MRO::BEGIN FDS:MRO (2022:344:230826:2ECf)</p>",
                "visibility": "public",
                "sensitive": false,
                "spoiler_text": "",
                "media_attachments": [
                ],
                "mentions": [
                ],
                "tags": [
                ],
                "emojis": [
                ],
                "reblogs_count": 0,
                "favourites_count": 0,
                "replies_count": 0,
                "url": "https://techhub.social/@NSFVoyager2/109491891777243775",
                "language": "en"
            },
            {
                "id": "109491891914586787",
                "uri": "https://home.social/users/FunnyLaughter1/statuses/109491891092070715",
                "created_at": "2022-12-10T23:07:50Z",
                "account": {
                    "id": "109362748393876466",
                    "username": "FunnyLaughter1",
                    "acct": "FunnyLaughter1@home.social",
                    "url": "https://home.social/@FunnyLaughter1",
                    "display_name": "Steph, MA",
                    "note": "<p>Shakespeare enthusiast. Feminist. \"Silver-tongued devil.\" M.A. in English Literature. English instructor. Overeducated. Read Shakespeare. she/her</p>",
                    "avatar": "https://files.mastodon.social/cache/accounts/avatars/109/362/748/393/876/466/original/b173101523b0b159.jpeg",
                    "avatar_static": "https://files.mastodon.social/cache/accounts/avatars/109/362/748/393/876/466/original/b173101523b0b159.jpeg",
                    "header": "https://files.mastodon.social/cache/accounts/headers/109/362/748/393/876/466/original/5e939c67f3dc326b.jpeg",
                    "header_static": "https://files.mastodon.social/cache/accounts/headers/109/362/748/393/876/466/original/5e939c67f3dc326b.jpeg",
                    "locked": false,
                    "emojis": [
                    ],
                    "discoverable": true,
                    "created_at": "2022-11-18T00:00:00Z",
                    "last_status_at": "2022-12-10",
                    "statuses_count": 36,
                    "followers_count": 19,
                    "following_count": 17,
                    "fields": [
                    ],
                    "bot": false
                },
                "content": "<p>Doing a snooze.</p>",
                "visibility": "public",
                "sensitive": false,
                "spoiler_text": "",
                "media_attachments": [
                    {
                        "id": "109491891216088551",
                        "type": "image",
                        "url": "https://files.mastodon.social/cache/media_attachments/files/109/491/891/216/088/551/original/8b2b28f929b27a16.png",
                        "preview_url": "https://files.mastodon.social/cache/media_attachments/files/109/491/891/216/088/551/small/8b2b28f929b27a16.png",
                        "remote_url": "https://assets.home.social/media_attachments/files/109/491/890/829/717/888/original/c156784b9938a9e8.png",
                        "meta": {
                            "original": {
                                "width": 1152,
                                "height": 1536,
                                "size": "1152x1536",
                                "aspect": 0.75
                            },
                            "small": {
                                "width": 416,
                                "height": 554,
                                "size": "416x554",
                                "aspect": 0.7509025270758123
                            }
                        },
                        "blurhash": "UCA]]A4:?HIo~Ws+t7IV%LoeE3RjIpkCRjRk"
                    }
                ],
                "mentions": [
                ],
                "tags": [
                ],
                "emojis": [
                ],
                "reblogs_count": 0,
                "favourites_count": 0,
                "replies_count": 0,
                "url": "https://home.social/@FunnyLaughter1/109491891092070715",
                "language": "en"
            }
        ]
"""
