package dev.helight.kotale

import com.hypixel.hytale.server.core.plugin.JavaPluginInit

class KotaleExamplePlugin(init: JavaPluginInit) : KotlinPlugin(init) {

    override fun setup() {
        super.setup()
        this.commandRegistry.registerCommand(ExampleCommand(this))
    }
}

