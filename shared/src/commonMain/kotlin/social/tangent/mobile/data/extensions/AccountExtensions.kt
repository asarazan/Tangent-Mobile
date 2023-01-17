package social.tangent.mobile.data.extensions

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import social.tangent.mobile.api.entities.Account

fun Account.serialize(): String {
    return Json.encodeToString(this)
}

fun Account.Companion.deserialize(json: String): Account {
    return Json.decodeFromString(json)
}