package dev.helight.kotale

object KotaleInitializer {

    var plugin: KotlinPlugin? = null

    fun setupForPlugin(ktPlugin: KotlinPlugin) {
        plugin = ktPlugin
    }

    fun startForPlugin(ktPlugin: KotlinPlugin) {
    }

    fun disposeForPlugin(ktPlugin: KotlinPlugin) {
        plugin = null
    }

}