package social.tangent.mobile

import com.russhwolf.settings.Settings
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initKoin(vararg appModules: Module): KoinApplication {
    val mods = appModules.toList()
    return startKoin {
        modules(mods + module {
            single {
                val factory = get<Settings.Factory>()
                val settings = factory.create()
                settings
            }
        })
    }
}
