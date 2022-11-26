package social.tangent.mobile

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.create
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module
import social.tangent.mobile.api.Api

private fun ktorfit(debug: Boolean) = Ktorfit.Builder()
    .baseUrl("https://mastodon.gamedev.place/")
    .httpClient {
        install(Logging) {
            logger = Logger.DEFAULT
            level = if (debug) LogLevel.ALL else LogLevel.INFO
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
        }
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    prettyPrint = true
                }
            )
        }
    }
    .build()

fun initKoin(): KoinApplication {
    val ktorfit = ktorfit(true) // TODO

    return startKoin {
        module {
            single<Api> { ktorfit.create() }
            // single<Mastodon> { Mastodon.create() }
        }
    }
}
