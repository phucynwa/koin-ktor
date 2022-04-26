package org.koin.ktor.ext

import io.ktor.server.application.ApplicationStopped
import io.ktor.server.application.createApplicationPlugin
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.core.module.Module

class KoinConfig {

    val modules = mutableListOf<Module>()

    operator fun Module.unaryPlus() {
        modules.add(this)
    }

    operator fun List<Module>.unaryPlus() {
        modules.addAll(this)
    }
}

val KoinPlugin = createApplicationPlugin(name = "Koin", createConfiguration = ::KoinConfig) {
    println("Koin is installed!")
    val koinApplication = startKoin {
        modules(pluginConfig.modules)
    }
    environment?.monitor?.run {
        raise(KoinApplicationStarted, koinApplication)
        subscribe(ApplicationStopped) {
            raise(KoinApplicationStopPreparing, koinApplication)
            stopKoin()
            raise(KoinApplicationStopped, koinApplication)
        }
    }
}
