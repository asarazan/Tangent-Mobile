package social.tangent.mobile.koin

import com.russhwolf.settings.Settings
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import social.tangent.mobile.data.DbFactory

fun initKoin(vararg appModules: Module): KoinApplication {
    val mods = appModules.toList()
    return startKoin {
        modules(mods + module {
            single {
                get<Settings.Factory>().create()
            }
            single {
                DbFactory
            }
        })
    }
}
