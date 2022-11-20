package social.tangent.mobile

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.create
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import social.tangent.mobile.api.Api

class Greeting {
    private val platform: Platform = getPlatform()

    fun greeting(): String {
        return "Hello, ${platform.name}!"
    }

    suspend fun foo() {
        val ktorfit = Ktorfit.Builder()
            .baseUrl("https://mastodon.gamedev.place/")
            .httpClient {
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.ALL
                    logger = object: Logger {
                        override fun log(message: String) {
                            println(message)
                        }
                    }
                }
                install(ContentNegotiation) {
                    json(Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                        prettyPrint = true // TODO
                    })
                }
            }
            .build()
        val api = ktorfit.create<Api>()
        val application = api.authenticateApp(
            "https://mastodon.gamedev.place/",
            "Tangent",
            "oauth2redirect://social.tangent.mobile",
            "read"
        )
        val token = api.fetchOAuthToken(
            "https://mastodon.gamedev.place/",
            application.clientId!!,
            application.clientSecret!!,
            "oauth2redirect://social.tangent.mobile",
            "client_credentials"
        )
        val timeline = api.getPublicTimeline(
            "Bearer ${token.accessToken}",
            local = true
        )
        val offenders = timeline.filter { it.account.discoverable == null }
        println("Hey: $timeline ($offenders)")
    }
}