package dev.helight.kotale

import com.hypixel.hytale.server.core.HytaleServer
import com.hypixel.hytale.server.core.universe.Universe

val universe get() = requireNotNull(Universe.get()) { "Universe is not available" }
val universeOrNull: Universe? get() = Universe.get()

val hytaleServer get() = requireNotNull(HytaleServer.get()) { "Hytale server is not available" }
val hytaleServerOrNull: HytaleServer? get() = HytaleServer.get()
