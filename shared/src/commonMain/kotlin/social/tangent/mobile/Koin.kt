package social.tangent.mobile

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin(appModule: Module): KoinApplication {
    return startKoin {
        modules(appModule)
    }
}
