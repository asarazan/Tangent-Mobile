package social.tangent.mobile.android.html

import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.text.buildAnnotatedString
import org.junit.Assert.assertEquals
import org.junit.Test
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.extensions.deserialize

val status = Status.deserialize("""{"id":"109695054849069181","uri":"https://mastodon.world/users/HoustonDog/statuses/109695054869189890","created_at":"2023-01-15T20:15:03Z","account":{"id":"109310366384771186","username":"HoustonDog","acct":"HoustonDog@mastodon.world","url":"https://mastodon.world/@HoustonDog","display_name":"HoustonDog :mastodonworld:","note":"<p>a dog and his boy —  was twitter.com/LostPets and <span class=\"h-card\"><a href=\"https://mastodon.world/@petabites\" class=\"u-url mention\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">@<span>petabites</span></a></span> </p>","avatar":"https://cdn.masto.host/mastodongamedevplace/cache/accounts/avatars/109/310/366/384/771/186/original/ef75317102ed239e.png","avatar_static":"https://cdn.masto.host/mastodongamedevplace/cache/accounts/avatars/109/310/366/384/771/186/original/ef75317102ed239e.png","header":"https://cdn.masto.host/mastodongamedevplace/cache/accounts/headers/109/310/366/384/771/186/original/cdb4f3f1e34da28b.jpg","header_static":"https://cdn.masto.host/mastodongamedevplace/cache/accounts/headers/109/310/366/384/771/186/original/cdb4f3f1e34da28b.jpg","locked":false,"emojis":[{"shortcode":"mastodonworld","url":"https://cdn.masto.host/mastodongamedevplace/cache/custom_emojis/images/000/194/683/original/86652f0c50f26044.png","static_url":"https://cdn.masto.host/mastodongamedevplace/cache/custom_emojis/images/000/194/683/static/86652f0c50f26044.png","visible_in_picker":true}],"discoverable":true,"created_at":"2022-11-03T00:00:00Z","last_status_at":"2023-01-15","statuses_count":2461,"followers_count":393,"following_count":947,"fields":[{"name":"#Introduction","value":"Please be kind isn’t asking too much."},{"name":"Just My Toots","value":"<a href=\"https://justmytoots.com/@HoustonDog@mastodon.world\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"ellipsis\">justmytoots.com/@HoustonDog@ma</span><span class=\"invisible\">stodon.world</span></a>"}],"bot":false},"content":"<p>I want them to make a <a href=\"https://mastodon.world/tags/SleepNumber\" class=\"mention hashtag\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>SleepNumber</span></a> “chair” for Zoom and Team meetings.</p>","visibility":"public","sensitive":false,"spoiler_text":"","media_attachments":[],"mentions":[],"tags":[{"name":"sleepnumber","url":"https://mastodon.gamedev.place/tags/sleepnumber"}],"emojis":[],"reblogs_count":1,"favourites_count":0,"replies_count":0,"url":"https://mastodon.world/@HoustonDog/109695054869189890","language":"en","favourited":false,"reblogged":false,"muted":false,"bookmarked":false}""")

class EmojiTests {

    @Test
    fun plainTextEmoji() {
        val base = status.account.displayName
        val emojified = base.emojify(status.account.emojis)
        val expected = buildAnnotatedString {
            append("HoustonDog ")
            appendInlineContent("https://cdn.masto.host/mastodongamedevplace/cache/custom_emojis/images/000/194/683/original/86652f0c50f26044.png")
        }
        assertEquals(expected, emojified)
    }

    @Test
    fun testStatusContent() {
        val status = Status.deserialize("""{"id":"109539067142452360","uri":"https://jorts.horse/users/killeveryhetero/statuses/109539066326180785","created_at":"2022-12-19T07:05:07Z","account":{"id":"52766","username":"killeveryhetero","acct":"killeveryhetero@jorts.horse","url":"https://jorts.horse/@killeveryhetero","display_name":"attractive nuisance","note":"<p>☭⚢☭ useless lesbian ＊ certified nuclear waste technician ＊ insane cat hag ＊ anarcho-communist scum ＊ power abusing trash admin ＊ very spooky ☭⚢☭</p>","avatar":"https://cdn.masto.host/mastodongamedevplace/accounts/avatars/000/052/766/original/1c2579befec120f3.gif","avatar_static":"https://cdn.masto.host/mastodongamedevplace/accounts/avatars/000/052/766/static/1c2579befec120f3.png","header":"https://cdn.masto.host/mastodongamedevplace/accounts/headers/000/052/766/original/971b13223c640b95.png","header_static":"https://cdn.masto.host/mastodongamedevplace/accounts/headers/000/052/766/original/971b13223c640b95.png","locked":false,"emojis":[],"discoverable":false,"created_at":"2018-10-05T00:00:00Z","last_status_at":"2023-01-16","statuses_count":6229,"followers_count":2450,"following_count":1436,"fields":[{"name":"☭⚢ Zoey ⚢☭","value":"<a href=\"https://jorts.horse\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">jorts.horse</span><span class=\"invisible\"></span></a>"},{"name":"JORTS","value":"HORSE"},{"name":"fight","value":"me"},{"name":"you","value":"cowards"}],"bot":false},"content":"<p>:drake_dislike: sit around and think<br>:drake_like: shit around and stink</p>","visibility":"public","sensitive":false,"spoiler_text":"","media_attachments":[],"mentions":[],"tags":[],"emojis":[{"shortcode":"drake_dislike","url":"https://cdn.masto.host/mastodongamedevplace/custom_emojis/images/000/018/602/original/b3fc008178b8d413.png","static_url":"https://cdn.masto.host/mastodongamedevplace/custom_emojis/images/000/018/602/static/b3fc008178b8d413.png","visible_in_picker":true},{"shortcode":"drake_like","url":"https://cdn.masto.host/mastodongamedevplace/custom_emojis/images/000/018/603/original/2cee4ec660ff64f6.png","static_url":"https://cdn.masto.host/mastodongamedevplace/custom_emojis/images/000/018/603/static/2cee4ec660ff64f6.png","visible_in_picker":true}],"reblogs_count":2,"favourites_count":0,"replies_count":0,"url":"https://jorts.horse/@killeveryhetero/109539066326180785","language":"sn","favourited":false,"reblogged":false,"muted":false,"bookmarked":false}""")
        val emojified = status.content.emojify(status.emojis)
        val expected = buildAnnotatedString {
            append("<p>")
            appendInlineContent("https://cdn.masto.host/mastodongamedevplace/custom_emojis/images/000/018/602/original/b3fc008178b8d413.png")
            append(" sit around and think<br>")
            appendInlineContent("https://cdn.masto.host/mastodongamedevplace/custom_emojis/images/000/018/603/original/2cee4ec660ff64f6.png")
            append(" shit around and stink</p>")
        }
        assertEquals(expected, emojified)
    }
}