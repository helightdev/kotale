package dev.helight.kotale

import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.plugin.JavaPluginInit

class KotalePlugin(init: JavaPluginInit) : JavaPlugin(init) {

    override fun setup() {
        super.setup()
        logger.atInfo().log("Kotale Plugin setup!")
    }
}