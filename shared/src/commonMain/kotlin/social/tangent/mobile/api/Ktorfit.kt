package social.tangent.mobile.api

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.create
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val defaultJson = Json {
    isLenient = true
    ignoreUnknownKeys = true
    prettyPrint = true // TODO
}

fun Api.Companion.server(domain: String): Api {
    val ktorfit = Ktorfit.Builder()
        .baseUrl(domain)
        .httpClient {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
            }
            install(ContentNegotiation) {
                json(defaultJson)
            }
        }
        .build()
    return ktorfit.create()
}
