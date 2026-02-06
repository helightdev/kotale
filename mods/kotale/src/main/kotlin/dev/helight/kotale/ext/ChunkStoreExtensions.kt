package dev.helight.kotale.ext

import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore

val Store<ChunkStore>.world get() = this.externalData.world
val Ref<ChunkStore>.world: World get() = this.store.externalData.world