package dev.helight.kotale

import com.hypixel.hytale.server.core.plugin.JavaPluginInit

class KotalePluginImpl(init: JavaPluginInit) : KotlinPlugin(init) {

    override fun setup() {
        KotaleInitializer.setupForPlugin(this)
    }

    override fun start() {
        KotaleInitializer.startForPlugin(this)
    }

    override fun shutdown() {
        KotaleInitializer.disposeForPlugin(this)
    }
}

