package dev.helight.kotale.ext

import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.math.vector.Transform
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore

val Ref<EntityStore>.player: Player get() = this.store.player(this)
val Ref<EntityStore>.playerRef: PlayerRef get() = this.store.playerRef(this)
val Ref<EntityStore>.playerRefOrNull: PlayerRef? get() = this.store.playerRefOrNull(this)
val PlayerRef.playerOrNull: Player? get() = this.reference?.player
val PlayerRef.world: World? get() = this.reference?.store?.externalData?.world
val Store<EntityStore>.world: World get() = this.externalData.world
val Ref<EntityStore>.world: World get() = this.store.externalData.world

fun Store<EntityStore>.heal(ref: Ref<EntityStore>) {
    val comp = getComponent(ref, EntityStatMap.getComponentType())
    comp?.resetStatValue(DefaultEntityStatTypes.getHealth())
}

fun Store<EntityStore>.teleport(ref: Ref<EntityStore>, world: World, transform: Transform) {
    this.addComponent(
        ref,
        Teleport.getComponentType(),
        Teleport(world, transform.position.clone(), transform.rotation.clone())
    )
}

fun ComponentAccessor<EntityStore>.playerRef(ref: Ref<EntityStore>): PlayerRef {
    return this.getComponent(ref, PlayerRef.getComponentType()) as PlayerRef
}

@Deprecated("Name change for consistency", replaceWith = ReplaceWith("playerRefOrNull"))
fun ComponentAccessor<EntityStore>.maybePlayerRef(ref: Ref<EntityStore>): PlayerRef? {
    return this.getComponent(ref, PlayerRef.getComponentType())
}

fun ComponentAccessor<EntityStore>.playerRefOrNull(ref: Ref<EntityStore>): PlayerRef? {
    return this.getComponent(ref, PlayerRef.getComponentType())
}

fun ComponentAccessor<EntityStore>.player(ref: Ref<EntityStore>): Player {
    return this.getComponent(ref, Player.getComponentType()) as Player
}

@Deprecated("Name change for consistency", replaceWith = ReplaceWith("playerOrNull"))
fun ComponentAccessor<EntityStore>.maybePlayer(ref: Ref<EntityStore>): Player? {
    return this.getComponent(ref, Player.getComponentType())
}

fun ComponentAccessor<EntityStore>.playerOrNull(ref: Ref<EntityStore>): Player? {
    return this.getComponent(ref, Player.getComponentType())
}