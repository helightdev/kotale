package dev.helight.kotale

import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.server.core.entity.Entity
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
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import java.util.*

fun Store<EntityStore>.player(ref: Ref<EntityStore>): Player {
    return this.getComponent(ref, Player.getComponentType()) as Player
}

fun Store<EntityStore>.maybePlayer(ref: Ref<EntityStore>): Player? {
    return this.getComponent(ref, Player.getComponentType())
}

fun Store<EntityStore>.transform(ref: Ref<EntityStore>): TransformComponent {
    return this.getComponent(ref, TransformComponent.getComponentType()) as TransformComponent
}

fun Store<EntityStore>.uuid(ref: Ref<EntityStore>): UUID {
    return (this.getComponent(ref, UUIDComponent.getComponentType()) as UUIDComponent).uuid
}

fun Store<EntityStore>.scale(ref: Ref<EntityStore>): Float {
    return (this.getComponent(ref, EntityScaleComponent.getComponentType()) as EntityScaleComponent).scale
}

fun Store<EntityStore>.velocity(ref: Ref<EntityStore>): Velocity {
    return this.getComponent(ref, Velocity.getComponentType()) as Velocity
}

fun Store<EntityStore>.standardPhysics(ref: Ref<EntityStore>): StandardPhysicsProvider {
    return this.getComponent(ref, StandardPhysicsProvider.getComponentType()) as StandardPhysicsProvider
}

fun Store<EntityStore>.boundingBox(ref: Ref<EntityStore>): BoundingBox {
    return this.getComponent(ref, BoundingBox.getComponentType()) as BoundingBox
}

fun Store<EntityStore>.headRotation(ref: Ref<EntityStore>): HeadRotation {
    return this.getComponent(ref, HeadRotation.getComponentType()) as HeadRotation
}

fun Store<EntityStore>.audio(ref: Ref<EntityStore>): AudioComponent {
    return this.getComponent(ref, AudioComponent.getComponentType()) as AudioComponent
}

fun Store<EntityStore>.model(ref: Ref<EntityStore>): ModelComponent {
    return this.getComponent(ref, ModelComponent.getComponentType()) as ModelComponent
}

fun Store<EntityStore>.persistentModel(ref: Ref<EntityStore>): PersistentModel {
    return this.getComponent(ref, PersistentModel.getComponentType()) as PersistentModel
}

fun Store<EntityStore>.item(ref: Ref<EntityStore>): ItemComponent {
    return this.getComponent(ref, ItemComponent.getComponentType()) as ItemComponent
}

fun Store<EntityStore>.nameplate(ref: Ref<EntityStore>): Nameplate {
    return this.getComponent(ref, Nameplate.getComponentType()) as Nameplate
}

fun Store<EntityStore>.repulsion(ref: Ref<EntityStore>): Repulsion {
    return this.getComponent(ref, Repulsion.getComponentType()) as Repulsion
}

fun Store<EntityStore>.damageData(ref: Ref<EntityStore>): DamageDataComponent {
    return this.getComponent(ref, DamageDataComponent.getComponentType()) as DamageDataComponent
}

fun Store<EntityStore>.maybeDeath(ref: Ref<EntityStore>): DeathComponent? {
    return this.getComponent(ref, DeathComponent.getComponentType())
}

fun Store<EntityStore>.intangible(ref: Ref<EntityStore>): Boolean {
    return this.getComponent(ref, Intangible.getComponentType()) != null
}

fun Store<EntityStore>.invulnerable(ref: Ref<EntityStore>): Boolean {
    return this.getComponent(ref, Invulnerable.getComponentType()) != null
}

fun Store<EntityStore>.interactable(ref: Ref<EntityStore>): Boolean {
    return this.getComponent(ref, Interactable.getComponentType()) != null
}

fun Store<EntityStore>.frozen(ref: Ref<EntityStore>): Boolean {
    return this.getComponent(ref, Frozen.getComponentType()) != null
}

fun Store<EntityStore>.prop(ref: Ref<EntityStore>): Boolean {
    return this.getComponent(ref, PropComponent.getComponentType()) != null
}