package social.tangent.mobile.sdk.storage

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import social.tangent.mobile.sdk.Mastodon
import social.tangent.mobile.sdk.MastodonServer

object MastodonStorage : KoinComponent {

    private val settings by lazy {
        get<Settings>()
    }

    private val map = linkedMapOf<String, Mastodon>()

    fun all(): List<Mastodon> {
        return getIds().map { get(it)!! }
    }

    @Deprecated("This is temporary")
    val default get() = all().first()

    fun get(id: String): Mastodon? {
        val cached = map[id]
        if (cached != null) return cached
        val str: String = settings["__mastodon_id_$id"] ?: return null
        val json = Json.decodeFromString<MastodonJson>(str)
        val server = MastodonServer.cached(json.domain) ?: return null
        val result = Mastodon(id, server, json.token)
        map[id] = result
        return result
    }

    fun set(mastodon: Mastodon) {
        val id = mastodon.id
        map[id] = mastodon
        val json = MastodonJson(id, mastodon.token, mastodon.server.domain)
        val str = Json.encodeToString(json)
        settings["__mastodon_id_$id"] = str
        setIds((getIds() + id).distinct())
    }

    private fun getIds(): List<String> {
        val str: String = settings["__mastodon_ids"] ?: return listOf()
        return str.split("|")
    }

    private fun setIds(ids: List<String>) {
        settings["__mastodon_ids"] = ids.joinToString("|")
    }
}