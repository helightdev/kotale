package dev.helight.kotale.ext

import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.server.core.entity.Frozen
import com.hypixel.hytale.server.core.entity.UUIDComponent
import com.hypixel.hytale.server.core.entity.damage.DamageDataComponent
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.entity.nameplate.Nameplate
import com.hypixel.hytale.server.core.modules.entity.component.*
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent
import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent
import com.hypixel.hytale.server.core.modules.entity.repulsion.Repulsion
import com.hypixel.hytale.server.core.modules.physics.component.Velocity
import com.hypixel.hytale.server.core.modules.projectile.config.StandardPhysicsProvider
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import java.util.*

val Ref<EntityStore>.player: Player get() = this.store.player(this)
val Ref<EntityStore>.playerRef: PlayerRef get() = this.store.playerRef(this)
val Ref<EntityStore>.playerRefOrNull: PlayerRef? get() = this.store.maybePlayerRef(this)

fun ComponentAccessor<EntityStore>.playerRef(ref: Ref<EntityStore>): PlayerRef {
    return this.getComponent(ref, PlayerRef.getComponentType()) as PlayerRef
}

fun ComponentAccessor<EntityStore>.maybePlayerRef(ref: Ref<EntityStore>): PlayerRef? {
    return this.getComponent(ref, PlayerRef.getComponentType())
}

fun ComponentAccessor<EntityStore>.player(ref: Ref<EntityStore>): Player {
    return this.getComponent(ref, Player.getComponentType()) as Player
}

fun ComponentAccessor<EntityStore>.maybePlayer(ref: Ref<EntityStore>): Player? {
    return this.getComponent(ref, Player.getComponentType())
}

fun ComponentAccessor<EntityStore>.transform(ref: Ref<EntityStore>): TransformComponent {
    return this.getComponent(ref, TransformComponent.getComponentType()) as TransformComponent
}

fun ComponentAccessor<EntityStore>.uuid(ref: Ref<EntityStore>): UUID {
    return (this.getComponent(ref, UUIDComponent.getComponentType()) as UUIDComponent).uuid
}

fun ComponentAccessor<EntityStore>.scale(ref: Ref<EntityStore>): Float {
    return (this.getComponent(ref, EntityScaleComponent.getComponentType()) as EntityScaleComponent).scale
}

fun ComponentAccessor<EntityStore>.velocity(ref: Ref<EntityStore>): Velocity {
    return this.getComponent(ref, Velocity.getComponentType()) as Velocity
}

fun ComponentAccessor<EntityStore>.standardPhysics(ref: Ref<EntityStore>): StandardPhysicsProvider {
    return this.getComponent(ref, StandardPhysicsProvider.getComponentType()) as StandardPhysicsProvider
}

fun ComponentAccessor<EntityStore>.boundingBox(ref: Ref<EntityStore>): BoundingBox {
    return this.getComponent(ref, BoundingBox.getComponentType()) as BoundingBox
}

fun ComponentAccessor<EntityStore>.headRotation(ref: Ref<EntityStore>): HeadRotation {
    return this.getComponent(ref, HeadRotation.getComponentType()) as HeadRotation
}

fun ComponentAccessor<EntityStore>.audio(ref: Ref<EntityStore>): AudioComponent {
    return this.getComponent(ref, AudioComponent.getComponentType()) as AudioComponent
}

fun ComponentAccessor<EntityStore>.model(ref: Ref<EntityStore>): ModelComponent {
    return this.getComponent(ref, ModelComponent.getComponentType()) as ModelComponent
}

fun ComponentAccessor<EntityStore>.persistentModel(ref: Ref<EntityStore>): PersistentModel {
    return this.getComponent(ref, PersistentModel.getComponentType()) as PersistentModel
}

fun ComponentAccessor<EntityStore>.item(ref: Ref<EntityStore>): ItemComponent {
    return this.getComponent(ref, ItemComponent.getComponentType()) as ItemComponent
}

fun ComponentAccessor<EntityStore>.nameplate(ref: Ref<EntityStore>): Nameplate {
    return this.getComponent(ref, Nameplate.getComponentType()) as Nameplate
}

fun ComponentAccessor<EntityStore>.repulsion(ref: Ref<EntityStore>): Repulsion {
    return this.getComponent(ref, Repulsion.getComponentType()) as Repulsion
}

fun ComponentAccessor<EntityStore>.damageData(ref: Ref<EntityStore>): DamageDataComponent {
    return this.getComponent(ref, DamageDataComponent.getComponentType()) as DamageDataComponent
}

fun ComponentAccessor<EntityStore>.maybeDeath(ref: Ref<EntityStore>): DeathComponent? {
    return this.getComponent(ref, DeathComponent.getComponentType())
}

fun ComponentAccessor<EntityStore>.intangible(ref: Ref<EntityStore>): Boolean {
    return this.getComponent(ref, Intangible.getComponentType()) != null
}

fun ComponentAccessor<EntityStore>.invulnerable(ref: Ref<EntityStore>): Boolean {
    return this.getComponent(ref, Invulnerable.getComponentType()) != null
}

fun ComponentAccessor<EntityStore>.interactable(ref: Ref<EntityStore>): Boolean {
    return this.getComponent(ref, Interactable.getComponentType()) != null
}

fun ComponentAccessor<EntityStore>.frozen(ref: Ref<EntityStore>): Boolean {
    return this.getComponent(ref, Frozen.getComponentType()) != null
}

fun ComponentAccessor<EntityStore>.prop(ref: Ref<EntityStore>): Boolean {
    return this.getComponent(ref, PropComponent.getComponentType()) != null
}