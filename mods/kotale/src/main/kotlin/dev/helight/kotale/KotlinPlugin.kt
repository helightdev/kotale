package dev.helight.kotale

import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.plugin.JavaPluginInit
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
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

    fun launch(dispatcher: CoroutineDispatcher = Dispatchers.Default, block: suspend CoroutineScope.() -> Unit): Job {
        return CoroutineScope(supervisor + dispatcher).launch(block = block)
    }
}