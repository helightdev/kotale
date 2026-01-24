package dev.helight.kotale

import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.plugin.JavaPluginInit
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class KotlinPlugin(init: JavaPluginInit) : JavaPlugin(init) {
    var supervisor = SupervisorJob()

    override fun setup() {
        super.setup()
        supervisor = SupervisorJob()
    }

    override fun shutdown() {
        super.shutdown()
        supervisor.cancel()
    }

    fun scope(dispatcher: CoroutineDispatcher = Dispatchers.Default): CoroutineScope {
        return CoroutineScope(supervisor + dispatcher)
    }

    fun launch(dispatcher: CoroutineDispatcher = Dispatchers.Default, block: suspend CoroutineScope.() -> Unit): Job {
        return scope(dispatcher).launch(block = block)
    }

    fun <T> async(dispatcher: CoroutineDispatcher = Dispatchers.Default, block: suspend CoroutineScope.() -> T): Deferred<T> {
        return scope(dispatcher).async(block = block)
    }
}