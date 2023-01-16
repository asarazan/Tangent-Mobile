package social.tangent.mobile.android.html

import androidx.compose.ui.text.TextStyle
import junit.framework.TestCase.assertEquals
import org.junit.Test
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.extensions.deserialize

val funTwete = Status.deserialize("""{"id":"109688628725125377","uri":"https://circumstances.run/users/davidgerard/statuses/109688628587719828","created_at":"2023-01-14T17:00:46Z","account":{"id":"109331030968888969","username":"davidgerard","acct":"davidgerard@circumstances.run","url":"https://circumstances.run/@davidgerard","display_name":"David Gerard","note":"<p>your #1 source for absurdist true crime</p><p>I toot as I please</p>","avatar":"https://cdn.masto.host/mastodongamedevplace/cache/accounts/avatars/109/331/030/968/888/969/original/b2a9eb5e57f39063.jpg","avatar_static":"https://cdn.masto.host/mastodongamedevplace/cache/accounts/avatars/109/331/030/968/888/969/original/b2a9eb5e57f39063.jpg","header":"https://cdn.masto.host/mastodongamedevplace/cache/accounts/headers/109/331/030/968/888/969/original/15e4cb0f7c7696a3.png","header_static":"https://cdn.masto.host/mastodongamedevplace/cache/accounts/headers/109/331/030/968/888/969/original/15e4cb0f7c7696a3.png","locked":false,"emojis":[],"discoverable":true,"created_at":"2022-11-12T00:00:00Z","last_status_at":"2023-01-14","statuses_count":5056,"followers_count":4250,"following_count":292,"fields":[{"name":"website","value":"<a href=\"https://davidgerard.co.uk/\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">davidgerard.co.uk/</span><span class=\"invisible\"></span></a>","verified_at":"2023-01-13T22:10:26.140Z"},{"name":"email","value":"dgerard@gmail.com"},{"name":"pronouns","value":"he/him"},{"name":"music","value":"<a href=\"https://rocknerd.co.uk/\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">rocknerd.co.uk/</span><span class=\"invisible\"></span></a>","verified_at":"2023-01-13T22:10:26.251Z"}],"bot":false},"content":"<p>cyberbeans.org, 2013</p><p><a href=\"https://youtu.be/v6A5a-yN6QY\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"\">youtu.be/v6A5a-yN6QY</span><span class=\"invisible\"></span></a></p><p><a href=\"https://web.archive.org/web/20130427052442/http://www.cyberbeans.org/\" rel=\"nofollow noopener noreferrer\" target=\"_blank\"><span class=\"invisible\">https://</span><span class=\"ellipsis\">web.archive.org/web/2013042705</span><span class=\"invisible\">2442/http://www.cyberbeans.org/</span></a></p>","visibility":"public","sensitive":false,"spoiler_text":"","media_attachments":[],"mentions":[],"tags":[],"emojis":[],"reblogs_count":0,"favourites_count":0,"replies_count":0,"url":"https://circumstances.run/@davidgerard/109688628587719828","card":{"url":"https://www.youtube.com/watch?v=v6A5a-yN6QY&feature=youtu.be","title":"CyberBeans™ Endorsement™","description":"","type":"video","author_name":"752634513242134","author_url":"https://www.youtube.com/@752634513242134","provider_name":"YouTube","provider_url":"https://www.youtube.com/","html":"<iframe width=\"200\" height=\"113\" src=\"https://www.youtube.com/embed/v6A5a-yN6QY?feature=oembed\" frameborder=\"0\" allowfullscreen=\"\" title=\"CyberBeans™ Endorsement™\"></iframe>","width":200,"height":113,"image":"https://cdn.masto.host/mastodongamedevplace/cache/preview_cards/images/003/662/541/original/f9f97e3379a44eac.jpg","embed_url":"","blurhash":"UOEyoR?ccEf+~qbwRkoy9YD%nio0D%nhjYRj"},"language":"en","favourited":false,"reblogged":false,"muted":false,"bookmarked":false}""".trim())
val expected = """cyberbeans.org, 2013

youtu.be/v6A5a-yN6QY

web.archive.org/web/2013042705…""".trim()

val html = """<p>cyberbeans.org, 2013</p><p><a href="https://youtu.be/v6A5a-yN6QY" rel="nofollow noopener noreferrer" target="_blank"><span class="invisible">https://</span><span class="">youtu.be/v6A5a-yN6QY</span><span class="invisible"></span></a></p><p><a href="https://web.archive.org/web/20130427052442/http://www.cyberbeans.org/" rel="nofollow noopener noreferrer" target="_blank"><span class="invisible">https://</span><span class="ellipsis">web.archive.org/web/2013042705</span><span class="invisible">2442/http://www.cyberbeans.org/</span></a></p>""".trim()

class HtmlTests {

    @Test
    fun basicHtml() {
        val annotated = funTwete.parsedContent(TextStyle(), TextStyle())
        val asString = annotated.text
        assertEquals(expected, asString)
    }

}
