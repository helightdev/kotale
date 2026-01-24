package dev.helight.kotale

import com.hypixel.hytale.server.core.universe.world.events.RemoveWorldEvent

object KotaleInitializer {

    var plugin: KotlinPlugin? = null

    fun setupForPlugin(ktPlugin: KotlinPlugin) {
        plugin = ktPlugin

        plugin!!.eventRegistry.registerGlobal(RemoveWorldEvent::class.java) {
            WorldThreadDispatcher.remove(it.world)
        }
    }

    fun startForPlugin(ktPlugin: KotlinPlugin) {
    }

    fun disposeForPlugin(ktPlugin: KotlinPlugin) {
        plugin = null
    }

}