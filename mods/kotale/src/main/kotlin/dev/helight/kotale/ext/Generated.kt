@file:Suppress("unused")

package dev.helight.kotale.ext

import com.hypixel.hytale.component.*
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.hypixel.hytale.server.core.modules.entity.player.PlayerSkinComponent
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId
import com.hypixel.hytale.server.core.modules.entity.DespawnComponent
import com.hypixel.hytale.server.core.modules.entity.component.ActiveAnimationComponent
import com.hypixel.hytale.server.core.modules.entity.component.AudioComponent
import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox
import com.hypixel.hytale.server.core.modules.entity.component.DisplayNameComponent
import com.hypixel.hytale.server.core.modules.entity.component.DynamicLight
import com.hypixel.hytale.server.core.modules.entity.component.EntityScaleComponent
import com.hypixel.hytale.server.core.modules.entity.component.FromPrefab
import com.hypixel.hytale.server.core.modules.entity.component.FromWorldGen
import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation
import com.hypixel.hytale.server.core.modules.entity.component.HiddenFromAdventurePlayers
import com.hypixel.hytale.server.core.modules.entity.component.Intangible
import com.hypixel.hytale.server.core.modules.entity.component.Invulnerable
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent
import com.hypixel.hytale.server.core.modules.entity.component.MovementAudioComponent
import com.hypixel.hytale.server.core.modules.entity.component.NewSpawnComponent
import com.hypixel.hytale.server.core.modules.entity.component.PersistentDynamicLight
import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel
import com.hypixel.hytale.server.core.modules.entity.component.PositionDataComponent
import com.hypixel.hytale.server.core.modules.entity.component.PropComponent
import com.hypixel.hytale.server.core.modules.entity.component.RespondToHit
import com.hypixel.hytale.server.core.modules.entity.component.RotateObjectComponent
import com.hypixel.hytale.server.core.modules.entity.component.SnapshotBuffer
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.modules.entity.component.WorldGenId
import com.hypixel.hytale.server.core.entity.Frozen
import com.hypixel.hytale.server.core.entity.UUIDComponent
import com.hypixel.hytale.server.core.entity.knockback.KnockbackComponent
import com.hypixel.hytale.server.core.entity.nameplate.Nameplate
import com.hypixel.hytale.server.core.entity.group.EntityGroup
import com.hypixel.hytale.server.core.modules.physics.component.PhysicsValues
import com.hypixel.hytale.server.core.modules.physics.component.Velocity
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent
import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent
import com.hypixel.hytale.server.core.modules.entity.item.PickupItemComponent
import com.hypixel.hytale.server.core.modules.entity.item.PreventItemMerging
import com.hypixel.hytale.server.core.modules.entity.item.PreventPickup
import com.hypixel.hytale.server.core.modules.entity.repulsion.Repulsion
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap
import com.hypixel.hytale.server.core.modules.projectile.component.PredictedProjectile
import com.hypixel.hytale.server.core.entity.entities.ProjectileComponent
import com.hypixel.hytale.server.core.modules.projectile.component.Projectile
import com.hypixel.hytale.server.core.modules.projectile.config.StandardPhysicsProvider


// PlayerSkinComponent
fun ComponentAccessor<EntityStore>.playerSkin(ref: Ref<EntityStore>): PlayerSkinComponent = this.getComponent(ref, PlayerSkinComponent.getComponentType())!!
fun ComponentAccessor<EntityStore>.playerSkinOrNull(ref: Ref<EntityStore>): PlayerSkinComponent? = this.getComponent(ref, PlayerSkinComponent.getComponentType())
fun ComponentAccessor<EntityStore>.ensurePlayerSkin(ref: Ref<EntityStore>): PlayerSkinComponent = this.ensureAndGetComponent(ref, PlayerSkinComponent.getComponentType())
fun ComponentAccessor<EntityStore>.addPlayerSkin(ref: Ref<EntityStore>, component: PlayerSkinComponent) = this.addComponent(ref, PlayerSkinComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putPlayerSkin(ref: Ref<EntityStore>, component: PlayerSkinComponent) = this.putComponent(ref, PlayerSkinComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removePlayerSkin(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, PlayerSkinComponent.getComponentType())
fun ArchetypeChunk<EntityStore>.playerSkin(index: Int): PlayerSkinComponent = this.getComponent(index, PlayerSkinComponent.getComponentType())!!
fun ArchetypeChunk<EntityStore>.playerSkinOrNull(index: Int): PlayerSkinComponent? = this.getComponent(index, PlayerSkinComponent.getComponentType())
val Ref<EntityStore>.playerSkinOrNull: PlayerSkinComponent?
get() = this.store.getComponent(this, PlayerSkinComponent.getComponentType())
val Ref<EntityStore>.playerSkin: PlayerSkinComponent
    get() = this.store.getComponent(this, PlayerSkinComponent.getComponentType())!!
val Holder<EntityStore>.playerSkinOrNull: PlayerSkinComponent?
get() = this.getComponent(PlayerSkinComponent.getComponentType())
var Holder<EntityStore>.playerSkin: PlayerSkinComponent
get() = this.getComponent(PlayerSkinComponent.getComponentType())!!
set(value) = this.putComponent(PlayerSkinComponent.getComponentType(), value)
fun Holder<EntityStore>.ensurePlayerSkin(): PlayerSkinComponent = this.ensureAndGetComponent(PlayerSkinComponent.getComponentType())
fun Holder<EntityStore>.addPlayerSkin(component: PlayerSkinComponent) = this.addComponent(PlayerSkinComponent.getComponentType(), component)
fun Holder<EntityStore>.removePlayerSkin() = this.tryRemoveComponent(PlayerSkinComponent.getComponentType())

// NetworkId
fun ComponentAccessor<EntityStore>.networkId(ref: Ref<EntityStore>): NetworkId = this.getComponent(ref, NetworkId.getComponentType())!!
fun ComponentAccessor<EntityStore>.networkIdOrNull(ref: Ref<EntityStore>): NetworkId? = this.getComponent(ref, NetworkId.getComponentType())
fun ComponentAccessor<EntityStore>.ensureNetworkId(ref: Ref<EntityStore>): NetworkId = this.ensureAndGetComponent(ref, NetworkId.getComponentType())
fun ComponentAccessor<EntityStore>.addNetworkId(ref: Ref<EntityStore>, component: NetworkId) = this.addComponent(ref, NetworkId.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putNetworkId(ref: Ref<EntityStore>, component: NetworkId) = this.putComponent(ref, NetworkId.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeNetworkId(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, NetworkId.getComponentType())
fun ArchetypeChunk<EntityStore>.networkId(index: Int): NetworkId = this.getComponent(index, NetworkId.getComponentType())!!
fun ArchetypeChunk<EntityStore>.networkIdOrNull(index: Int): NetworkId? = this.getComponent(index, NetworkId.getComponentType())
val Ref<EntityStore>.networkIdOrNull: NetworkId?
get() = this.store.getComponent(this, NetworkId.getComponentType())
val Ref<EntityStore>.networkId: NetworkId
    get() = this.store.getComponent(this, NetworkId.getComponentType())!!
val Holder<EntityStore>.networkIdOrNull: NetworkId?
get() = this.getComponent(NetworkId.getComponentType())
var Holder<EntityStore>.networkId: NetworkId
get() = this.getComponent(NetworkId.getComponentType())!!
set(value) = this.putComponent(NetworkId.getComponentType(), value)
fun Holder<EntityStore>.ensureNetworkId(): NetworkId = this.ensureAndGetComponent(NetworkId.getComponentType())
fun Holder<EntityStore>.addNetworkId(component: NetworkId) = this.addComponent(NetworkId.getComponentType(), component)
fun Holder<EntityStore>.removeNetworkId() = this.tryRemoveComponent(NetworkId.getComponentType())

// DespawnComponent
fun ComponentAccessor<EntityStore>.despawn(ref: Ref<EntityStore>): DespawnComponent = this.getComponent(ref, DespawnComponent.getComponentType())!!
fun ComponentAccessor<EntityStore>.despawnOrNull(ref: Ref<EntityStore>): DespawnComponent? = this.getComponent(ref, DespawnComponent.getComponentType())
fun ComponentAccessor<EntityStore>.ensureDespawn(ref: Ref<EntityStore>): DespawnComponent = this.ensureAndGetComponent(ref, DespawnComponent.getComponentType())
fun ComponentAccessor<EntityStore>.addDespawn(ref: Ref<EntityStore>, component: DespawnComponent) = this.addComponent(ref, DespawnComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putDespawn(ref: Ref<EntityStore>, component: DespawnComponent) = this.putComponent(ref, DespawnComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeDespawn(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, DespawnComponent.getComponentType())
fun ArchetypeChunk<EntityStore>.despawn(index: Int): DespawnComponent = this.getComponent(index, DespawnComponent.getComponentType())!!
fun ArchetypeChunk<EntityStore>.despawnOrNull(index: Int): DespawnComponent? = this.getComponent(index, DespawnComponent.getComponentType())
val Ref<EntityStore>.despawnOrNull: DespawnComponent?
get() = this.store.getComponent(this, DespawnComponent.getComponentType())
val Ref<EntityStore>.despawn: DespawnComponent
    get() = this.store.getComponent(this, DespawnComponent.getComponentType())!!
val Holder<EntityStore>.despawnOrNull: DespawnComponent?
get() = this.getComponent(DespawnComponent.getComponentType())
var Holder<EntityStore>.despawn: DespawnComponent
get() = this.getComponent(DespawnComponent.getComponentType())!!
set(value) = this.putComponent(DespawnComponent.getComponentType(), value)
fun Holder<EntityStore>.ensureDespawn(): DespawnComponent = this.ensureAndGetComponent(DespawnComponent.getComponentType())
fun Holder<EntityStore>.addDespawn(component: DespawnComponent) = this.addComponent(DespawnComponent.getComponentType(), component)
fun Holder<EntityStore>.removeDespawn() = this.tryRemoveComponent(DespawnComponent.getComponentType())

// ActiveAnimationComponent
fun ComponentAccessor<EntityStore>.activeAnimation(ref: Ref<EntityStore>): ActiveAnimationComponent = this.getComponent(ref, ActiveAnimationComponent.getComponentType())!!
fun ComponentAccessor<EntityStore>.activeAnimationOrNull(ref: Ref<EntityStore>): ActiveAnimationComponent? = this.getComponent(ref, ActiveAnimationComponent.getComponentType())
fun ComponentAccessor<EntityStore>.ensureActiveAnimation(ref: Ref<EntityStore>): ActiveAnimationComponent = this.ensureAndGetComponent(ref, ActiveAnimationComponent.getComponentType())
fun ComponentAccessor<EntityStore>.addActiveAnimation(ref: Ref<EntityStore>, component: ActiveAnimationComponent) = this.addComponent(ref, ActiveAnimationComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putActiveAnimation(ref: Ref<EntityStore>, component: ActiveAnimationComponent) = this.putComponent(ref, ActiveAnimationComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeActiveAnimation(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, ActiveAnimationComponent.getComponentType())
fun ArchetypeChunk<EntityStore>.activeAnimation(index: Int): ActiveAnimationComponent = this.getComponent(index, ActiveAnimationComponent.getComponentType())!!
fun ArchetypeChunk<EntityStore>.activeAnimationOrNull(index: Int): ActiveAnimationComponent? = this.getComponent(index, ActiveAnimationComponent.getComponentType())
val Ref<EntityStore>.activeAnimationOrNull: ActiveAnimationComponent?
get() = this.store.getComponent(this, ActiveAnimationComponent.getComponentType())
val Ref<EntityStore>.activeAnimation: ActiveAnimationComponent
    get() = this.store.getComponent(this, ActiveAnimationComponent.getComponentType())!!
val Holder<EntityStore>.activeAnimationOrNull: ActiveAnimationComponent?
get() = this.getComponent(ActiveAnimationComponent.getComponentType())
var Holder<EntityStore>.activeAnimation: ActiveAnimationComponent
get() = this.getComponent(ActiveAnimationComponent.getComponentType())!!
set(value) = this.putComponent(ActiveAnimationComponent.getComponentType(), value)
fun Holder<EntityStore>.ensureActiveAnimation(): ActiveAnimationComponent = this.ensureAndGetComponent(ActiveAnimationComponent.getComponentType())
fun Holder<EntityStore>.addActiveAnimation(component: ActiveAnimationComponent) = this.addComponent(ActiveAnimationComponent.getComponentType(), component)
fun Holder<EntityStore>.removeActiveAnimation() = this.tryRemoveComponent(ActiveAnimationComponent.getComponentType())

// AudioComponent
fun ComponentAccessor<EntityStore>.audio(ref: Ref<EntityStore>): AudioComponent = this.getComponent(ref, AudioComponent.getComponentType())!!
fun ComponentAccessor<EntityStore>.audioOrNull(ref: Ref<EntityStore>): AudioComponent? = this.getComponent(ref, AudioComponent.getComponentType())
fun ComponentAccessor<EntityStore>.ensureAudio(ref: Ref<EntityStore>): AudioComponent = this.ensureAndGetComponent(ref, AudioComponent.getComponentType())
fun ComponentAccessor<EntityStore>.addAudio(ref: Ref<EntityStore>, component: AudioComponent) = this.addComponent(ref, AudioComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putAudio(ref: Ref<EntityStore>, component: AudioComponent) = this.putComponent(ref, AudioComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeAudio(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, AudioComponent.getComponentType())
fun ArchetypeChunk<EntityStore>.audio(index: Int): AudioComponent = this.getComponent(index, AudioComponent.getComponentType())!!
fun ArchetypeChunk<EntityStore>.audioOrNull(index: Int): AudioComponent? = this.getComponent(index, AudioComponent.getComponentType())
val Ref<EntityStore>.audioOrNull: AudioComponent?
get() = this.store.getComponent(this, AudioComponent.getComponentType())
val Ref<EntityStore>.audio: AudioComponent
    get() = this.store.getComponent(this, AudioComponent.getComponentType())!!
val Holder<EntityStore>.audioOrNull: AudioComponent?
get() = this.getComponent(AudioComponent.getComponentType())
var Holder<EntityStore>.audio: AudioComponent
get() = this.getComponent(AudioComponent.getComponentType())!!
set(value) = this.putComponent(AudioComponent.getComponentType(), value)
fun Holder<EntityStore>.ensureAudio(): AudioComponent = this.ensureAndGetComponent(AudioComponent.getComponentType())
fun Holder<EntityStore>.addAudio(component: AudioComponent) = this.addComponent(AudioComponent.getComponentType(), component)
fun Holder<EntityStore>.removeAudio() = this.tryRemoveComponent(AudioComponent.getComponentType())

// BoundingBox
fun ComponentAccessor<EntityStore>.boundingBox(ref: Ref<EntityStore>): BoundingBox = this.getComponent(ref, BoundingBox.getComponentType())!!
fun ComponentAccessor<EntityStore>.boundingBoxOrNull(ref: Ref<EntityStore>): BoundingBox? = this.getComponent(ref, BoundingBox.getComponentType())
fun ComponentAccessor<EntityStore>.ensureBoundingBox(ref: Ref<EntityStore>): BoundingBox = this.ensureAndGetComponent(ref, BoundingBox.getComponentType())
fun ComponentAccessor<EntityStore>.addBoundingBox(ref: Ref<EntityStore>, component: BoundingBox) = this.addComponent(ref, BoundingBox.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putBoundingBox(ref: Ref<EntityStore>, component: BoundingBox) = this.putComponent(ref, BoundingBox.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeBoundingBox(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, BoundingBox.getComponentType())
fun ArchetypeChunk<EntityStore>.boundingBox(index: Int): BoundingBox = this.getComponent(index, BoundingBox.getComponentType())!!
fun ArchetypeChunk<EntityStore>.boundingBoxOrNull(index: Int): BoundingBox? = this.getComponent(index, BoundingBox.getComponentType())
val Ref<EntityStore>.boundingBoxOrNull: BoundingBox?
get() = this.store.getComponent(this, BoundingBox.getComponentType())
val Ref<EntityStore>.boundingBox: BoundingBox
    get() = this.store.getComponent(this, BoundingBox.getComponentType())!!
val Holder<EntityStore>.boundingBoxOrNull: BoundingBox?
get() = this.getComponent(BoundingBox.getComponentType())
var Holder<EntityStore>.boundingBox: BoundingBox
get() = this.getComponent(BoundingBox.getComponentType())!!
set(value) = this.putComponent(BoundingBox.getComponentType(), value)
fun Holder<EntityStore>.ensureBoundingBox(): BoundingBox = this.ensureAndGetComponent(BoundingBox.getComponentType())
fun Holder<EntityStore>.addBoundingBox(component: BoundingBox) = this.addComponent(BoundingBox.getComponentType(), component)
fun Holder<EntityStore>.removeBoundingBox() = this.tryRemoveComponent(BoundingBox.getComponentType())

// DisplayNameComponent
fun ComponentAccessor<EntityStore>.displayName(ref: Ref<EntityStore>): DisplayNameComponent = this.getComponent(ref, DisplayNameComponent.getComponentType())!!
fun ComponentAccessor<EntityStore>.displayNameOrNull(ref: Ref<EntityStore>): DisplayNameComponent? = this.getComponent(ref, DisplayNameComponent.getComponentType())
fun ComponentAccessor<EntityStore>.ensureDisplayName(ref: Ref<EntityStore>): DisplayNameComponent = this.ensureAndGetComponent(ref, DisplayNameComponent.getComponentType())
fun ComponentAccessor<EntityStore>.addDisplayName(ref: Ref<EntityStore>, component: DisplayNameComponent) = this.addComponent(ref, DisplayNameComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putDisplayName(ref: Ref<EntityStore>, component: DisplayNameComponent) = this.putComponent(ref, DisplayNameComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeDisplayName(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, DisplayNameComponent.getComponentType())
fun ArchetypeChunk<EntityStore>.displayName(index: Int): DisplayNameComponent = this.getComponent(index, DisplayNameComponent.getComponentType())!!
fun ArchetypeChunk<EntityStore>.displayNameOrNull(index: Int): DisplayNameComponent? = this.getComponent(index, DisplayNameComponent.getComponentType())
val Ref<EntityStore>.displayNameOrNull: DisplayNameComponent?
get() = this.store.getComponent(this, DisplayNameComponent.getComponentType())
val Ref<EntityStore>.displayName: DisplayNameComponent
    get() = this.store.getComponent(this, DisplayNameComponent.getComponentType())!!
val Holder<EntityStore>.displayNameOrNull: DisplayNameComponent?
get() = this.getComponent(DisplayNameComponent.getComponentType())
var Holder<EntityStore>.displayName: DisplayNameComponent
get() = this.getComponent(DisplayNameComponent.getComponentType())!!
set(value) = this.putComponent(DisplayNameComponent.getComponentType(), value)
fun Holder<EntityStore>.ensureDisplayName(): DisplayNameComponent = this.ensureAndGetComponent(DisplayNameComponent.getComponentType())
fun Holder<EntityStore>.addDisplayName(component: DisplayNameComponent) = this.addComponent(DisplayNameComponent.getComponentType(), component)
fun Holder<EntityStore>.removeDisplayName() = this.tryRemoveComponent(DisplayNameComponent.getComponentType())

// DynamicLight
fun ComponentAccessor<EntityStore>.dynamicLight(ref: Ref<EntityStore>): DynamicLight = this.getComponent(ref, DynamicLight.getComponentType())!!
fun ComponentAccessor<EntityStore>.dynamicLightOrNull(ref: Ref<EntityStore>): DynamicLight? = this.getComponent(ref, DynamicLight.getComponentType())
fun ComponentAccessor<EntityStore>.ensureDynamicLight(ref: Ref<EntityStore>): DynamicLight = this.ensureAndGetComponent(ref, DynamicLight.getComponentType())
fun ComponentAccessor<EntityStore>.addDynamicLight(ref: Ref<EntityStore>, component: DynamicLight) = this.addComponent(ref, DynamicLight.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putDynamicLight(ref: Ref<EntityStore>, component: DynamicLight) = this.putComponent(ref, DynamicLight.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeDynamicLight(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, DynamicLight.getComponentType())
fun ArchetypeChunk<EntityStore>.dynamicLight(index: Int): DynamicLight = this.getComponent(index, DynamicLight.getComponentType())!!
fun ArchetypeChunk<EntityStore>.dynamicLightOrNull(index: Int): DynamicLight? = this.getComponent(index, DynamicLight.getComponentType())
val Ref<EntityStore>.dynamicLightOrNull: DynamicLight?
get() = this.store.getComponent(this, DynamicLight.getComponentType())
val Ref<EntityStore>.dynamicLight: DynamicLight
    get() = this.store.getComponent(this, DynamicLight.getComponentType())!!
val Holder<EntityStore>.dynamicLightOrNull: DynamicLight?
get() = this.getComponent(DynamicLight.getComponentType())
var Holder<EntityStore>.dynamicLight: DynamicLight
get() = this.getComponent(DynamicLight.getComponentType())!!
set(value) = this.putComponent(DynamicLight.getComponentType(), value)
fun Holder<EntityStore>.ensureDynamicLight(): DynamicLight = this.ensureAndGetComponent(DynamicLight.getComponentType())
fun Holder<EntityStore>.addDynamicLight(component: DynamicLight) = this.addComponent(DynamicLight.getComponentType(), component)
fun Holder<EntityStore>.removeDynamicLight() = this.tryRemoveComponent(DynamicLight.getComponentType())

// EntityScaleComponent
fun ComponentAccessor<EntityStore>.entityScale(ref: Ref<EntityStore>): EntityScaleComponent = this.getComponent(ref, EntityScaleComponent.getComponentType())!!
fun ComponentAccessor<EntityStore>.entityScaleOrNull(ref: Ref<EntityStore>): EntityScaleComponent? = this.getComponent(ref, EntityScaleComponent.getComponentType())
fun ComponentAccessor<EntityStore>.ensureEntityScale(ref: Ref<EntityStore>): EntityScaleComponent = this.ensureAndGetComponent(ref, EntityScaleComponent.getComponentType())
fun ComponentAccessor<EntityStore>.addEntityScale(ref: Ref<EntityStore>, component: EntityScaleComponent) = this.addComponent(ref, EntityScaleComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putEntityScale(ref: Ref<EntityStore>, component: EntityScaleComponent) = this.putComponent(ref, EntityScaleComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeEntityScale(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, EntityScaleComponent.getComponentType())
fun ArchetypeChunk<EntityStore>.entityScale(index: Int): EntityScaleComponent = this.getComponent(index, EntityScaleComponent.getComponentType())!!
fun ArchetypeChunk<EntityStore>.entityScaleOrNull(index: Int): EntityScaleComponent? = this.getComponent(index, EntityScaleComponent.getComponentType())
val Ref<EntityStore>.entityScaleOrNull: EntityScaleComponent?
get() = this.store.getComponent(this, EntityScaleComponent.getComponentType())
val Ref<EntityStore>.entityScale: EntityScaleComponent
    get() = this.store.getComponent(this, EntityScaleComponent.getComponentType())!!
val Holder<EntityStore>.entityScaleOrNull: EntityScaleComponent?
get() = this.getComponent(EntityScaleComponent.getComponentType())
var Holder<EntityStore>.entityScale: EntityScaleComponent
get() = this.getComponent(EntityScaleComponent.getComponentType())!!
set(value) = this.putComponent(EntityScaleComponent.getComponentType(), value)
fun Holder<EntityStore>.ensureEntityScale(): EntityScaleComponent = this.ensureAndGetComponent(EntityScaleComponent.getComponentType())
fun Holder<EntityStore>.addEntityScale(component: EntityScaleComponent) = this.addComponent(EntityScaleComponent.getComponentType(), component)
fun Holder<EntityStore>.removeEntityScale() = this.tryRemoveComponent(EntityScaleComponent.getComponentType())

// FromPrefab
fun ComponentAccessor<EntityStore>.fromPrefab(ref: Ref<EntityStore>): Boolean = this.getComponent(ref, FromPrefab.getComponentType()) != null
fun ComponentAccessor<EntityStore>.setFromPrefab(ref: Ref<EntityStore>, value: Boolean) {
    if (value) this.ensureAndGetComponent(ref, FromPrefab.getComponentType())
    else this.tryRemoveComponent(ref, FromPrefab.getComponentType())
}
fun ArchetypeChunk<EntityStore>.fromPrefab(index: Int): Boolean = this.getComponent(index, FromPrefab.getComponentType()) != null
val Ref<EntityStore>.isFromPrefab: Boolean
    get() = this.store.getComponent(this, FromPrefab.getComponentType()) != null
var Holder<EntityStore>.isFromPrefab: Boolean
get() = this.getComponent(FromPrefab.getComponentType()) != null
set(value) {
    if (value) this.ensureComponent(FromPrefab.getComponentType())
    else this.tryRemoveComponent(FromPrefab.getComponentType())
}

// FromWorldGen
fun ComponentAccessor<EntityStore>.fromWorldGen(ref: Ref<EntityStore>): Boolean = this.getComponent(ref, FromWorldGen.getComponentType()) != null
fun ComponentAccessor<EntityStore>.setFromWorldGen(ref: Ref<EntityStore>, value: Boolean) {
    if (value) this.ensureAndGetComponent(ref, FromWorldGen.getComponentType())
    else this.tryRemoveComponent(ref, FromWorldGen.getComponentType())
}
fun ArchetypeChunk<EntityStore>.fromWorldGen(index: Int): Boolean = this.getComponent(index, FromWorldGen.getComponentType()) != null
val Ref<EntityStore>.isFromWorldGen: Boolean
    get() = this.store.getComponent(this, FromWorldGen.getComponentType()) != null
var Holder<EntityStore>.isFromWorldGen: Boolean
get() = this.getComponent(FromWorldGen.getComponentType()) != null
set(value) {
    if (value) this.ensureComponent(FromWorldGen.getComponentType())
    else this.tryRemoveComponent(FromWorldGen.getComponentType())
}

// HeadRotation
fun ComponentAccessor<EntityStore>.headRotation(ref: Ref<EntityStore>): HeadRotation = this.getComponent(ref, HeadRotation.getComponentType())!!
fun ComponentAccessor<EntityStore>.headRotationOrNull(ref: Ref<EntityStore>): HeadRotation? = this.getComponent(ref, HeadRotation.getComponentType())
fun ComponentAccessor<EntityStore>.ensureHeadRotation(ref: Ref<EntityStore>): HeadRotation = this.ensureAndGetComponent(ref, HeadRotation.getComponentType())
fun ComponentAccessor<EntityStore>.addHeadRotation(ref: Ref<EntityStore>, component: HeadRotation) = this.addComponent(ref, HeadRotation.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putHeadRotation(ref: Ref<EntityStore>, component: HeadRotation) = this.putComponent(ref, HeadRotation.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeHeadRotation(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, HeadRotation.getComponentType())
fun ArchetypeChunk<EntityStore>.headRotation(index: Int): HeadRotation = this.getComponent(index, HeadRotation.getComponentType())!!
fun ArchetypeChunk<EntityStore>.headRotationOrNull(index: Int): HeadRotation? = this.getComponent(index, HeadRotation.getComponentType())
val Ref<EntityStore>.headRotationOrNull: HeadRotation?
get() = this.store.getComponent(this, HeadRotation.getComponentType())
val Ref<EntityStore>.headRotation: HeadRotation
    get() = this.store.getComponent(this, HeadRotation.getComponentType())!!
val Holder<EntityStore>.headRotationOrNull: HeadRotation?
get() = this.getComponent(HeadRotation.getComponentType())
var Holder<EntityStore>.headRotation: HeadRotation
get() = this.getComponent(HeadRotation.getComponentType())!!
set(value) = this.putComponent(HeadRotation.getComponentType(), value)
fun Holder<EntityStore>.ensureHeadRotation(): HeadRotation = this.ensureAndGetComponent(HeadRotation.getComponentType())
fun Holder<EntityStore>.addHeadRotation(component: HeadRotation) = this.addComponent(HeadRotation.getComponentType(), component)
fun Holder<EntityStore>.removeHeadRotation() = this.tryRemoveComponent(HeadRotation.getComponentType())

// HiddenFromAdventurePlayers
fun ComponentAccessor<EntityStore>.hiddenFromAdventurePlayers(ref: Ref<EntityStore>): Boolean = this.getComponent(ref, HiddenFromAdventurePlayers.getComponentType()) != null
fun ComponentAccessor<EntityStore>.setHiddenFromAdventurePlayers(ref: Ref<EntityStore>, value: Boolean) {
    if (value) this.ensureAndGetComponent(ref, HiddenFromAdventurePlayers.getComponentType())
    else this.tryRemoveComponent(ref, HiddenFromAdventurePlayers.getComponentType())
}
fun ArchetypeChunk<EntityStore>.hiddenFromAdventurePlayers(index: Int): Boolean = this.getComponent(index, HiddenFromAdventurePlayers.getComponentType()) != null
val Ref<EntityStore>.isHiddenFromAdventurePlayers: Boolean
    get() = this.store.getComponent(this, HiddenFromAdventurePlayers.getComponentType()) != null
var Holder<EntityStore>.isHiddenFromAdventurePlayers: Boolean
get() = this.getComponent(HiddenFromAdventurePlayers.getComponentType()) != null
set(value) {
    if (value) this.ensureComponent(HiddenFromAdventurePlayers.getComponentType())
    else this.tryRemoveComponent(HiddenFromAdventurePlayers.getComponentType())
}

// Intangible
fun ComponentAccessor<EntityStore>.intangible(ref: Ref<EntityStore>): Boolean = this.getComponent(ref, Intangible.getComponentType()) != null
fun ComponentAccessor<EntityStore>.setIntangible(ref: Ref<EntityStore>, value: Boolean) {
    if (value) this.ensureAndGetComponent(ref, Intangible.getComponentType())
    else this.tryRemoveComponent(ref, Intangible.getComponentType())
}
fun ArchetypeChunk<EntityStore>.intangible(index: Int): Boolean = this.getComponent(index, Intangible.getComponentType()) != null
val Ref<EntityStore>.isIntangible: Boolean
    get() = this.store.getComponent(this, Intangible.getComponentType()) != null
var Holder<EntityStore>.isIntangible: Boolean
get() = this.getComponent(Intangible.getComponentType()) != null
set(value) {
    if (value) this.ensureComponent(Intangible.getComponentType())
    else this.tryRemoveComponent(Intangible.getComponentType())
}

// Invulnerable
fun ComponentAccessor<EntityStore>.invulnerable(ref: Ref<EntityStore>): Boolean = this.getComponent(ref, Invulnerable.getComponentType()) != null
fun ComponentAccessor<EntityStore>.setInvulnerable(ref: Ref<EntityStore>, value: Boolean) {
    if (value) this.ensureAndGetComponent(ref, Invulnerable.getComponentType())
    else this.tryRemoveComponent(ref, Invulnerable.getComponentType())
}
fun ArchetypeChunk<EntityStore>.invulnerable(index: Int): Boolean = this.getComponent(index, Invulnerable.getComponentType()) != null
val Ref<EntityStore>.isInvulnerable: Boolean
    get() = this.store.getComponent(this, Invulnerable.getComponentType()) != null
var Holder<EntityStore>.isInvulnerable: Boolean
get() = this.getComponent(Invulnerable.getComponentType()) != null
set(value) {
    if (value) this.ensureComponent(Invulnerable.getComponentType())
    else this.tryRemoveComponent(Invulnerable.getComponentType())
}

// ModelComponent
fun ComponentAccessor<EntityStore>.model(ref: Ref<EntityStore>): ModelComponent = this.getComponent(ref, ModelComponent.getComponentType())!!
fun ComponentAccessor<EntityStore>.modelOrNull(ref: Ref<EntityStore>): ModelComponent? = this.getComponent(ref, ModelComponent.getComponentType())
fun ComponentAccessor<EntityStore>.ensureModel(ref: Ref<EntityStore>): ModelComponent = this.ensureAndGetComponent(ref, ModelComponent.getComponentType())
fun ComponentAccessor<EntityStore>.addModel(ref: Ref<EntityStore>, component: ModelComponent) = this.addComponent(ref, ModelComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putModel(ref: Ref<EntityStore>, component: ModelComponent) = this.putComponent(ref, ModelComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeModel(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, ModelComponent.getComponentType())
fun ArchetypeChunk<EntityStore>.model(index: Int): ModelComponent = this.getComponent(index, ModelComponent.getComponentType())!!
fun ArchetypeChunk<EntityStore>.modelOrNull(index: Int): ModelComponent? = this.getComponent(index, ModelComponent.getComponentType())
val Ref<EntityStore>.modelOrNull: ModelComponent?
get() = this.store.getComponent(this, ModelComponent.getComponentType())
val Ref<EntityStore>.model: ModelComponent
    get() = this.store.getComponent(this, ModelComponent.getComponentType())!!
val Holder<EntityStore>.modelOrNull: ModelComponent?
get() = this.getComponent(ModelComponent.getComponentType())
var Holder<EntityStore>.model: ModelComponent
get() = this.getComponent(ModelComponent.getComponentType())!!
set(value) = this.putComponent(ModelComponent.getComponentType(), value)
fun Holder<EntityStore>.ensureModel(): ModelComponent = this.ensureAndGetComponent(ModelComponent.getComponentType())
fun Holder<EntityStore>.addModel(component: ModelComponent) = this.addComponent(ModelComponent.getComponentType(), component)
fun Holder<EntityStore>.removeModel() = this.tryRemoveComponent(ModelComponent.getComponentType())

// MovementAudioComponent
fun ComponentAccessor<EntityStore>.movementAudio(ref: Ref<EntityStore>): MovementAudioComponent = this.getComponent(ref, MovementAudioComponent.getComponentType())!!
fun ComponentAccessor<EntityStore>.movementAudioOrNull(ref: Ref<EntityStore>): MovementAudioComponent? = this.getComponent(ref, MovementAudioComponent.getComponentType())
fun ComponentAccessor<EntityStore>.ensureMovementAudio(ref: Ref<EntityStore>): MovementAudioComponent = this.ensureAndGetComponent(ref, MovementAudioComponent.getComponentType())
fun ComponentAccessor<EntityStore>.addMovementAudio(ref: Ref<EntityStore>, component: MovementAudioComponent) = this.addComponent(ref, MovementAudioComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putMovementAudio(ref: Ref<EntityStore>, component: MovementAudioComponent) = this.putComponent(ref, MovementAudioComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeMovementAudio(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, MovementAudioComponent.getComponentType())
fun ArchetypeChunk<EntityStore>.movementAudio(index: Int): MovementAudioComponent = this.getComponent(index, MovementAudioComponent.getComponentType())!!
fun ArchetypeChunk<EntityStore>.movementAudioOrNull(index: Int): MovementAudioComponent? = this.getComponent(index, MovementAudioComponent.getComponentType())
val Ref<EntityStore>.movementAudioOrNull: MovementAudioComponent?
get() = this.store.getComponent(this, MovementAudioComponent.getComponentType())
val Ref<EntityStore>.movementAudio: MovementAudioComponent
    get() = this.store.getComponent(this, MovementAudioComponent.getComponentType())!!
val Holder<EntityStore>.movementAudioOrNull: MovementAudioComponent?
get() = this.getComponent(MovementAudioComponent.getComponentType())
var Holder<EntityStore>.movementAudio: MovementAudioComponent
get() = this.getComponent(MovementAudioComponent.getComponentType())!!
set(value) = this.putComponent(MovementAudioComponent.getComponentType(), value)
fun Holder<EntityStore>.ensureMovementAudio(): MovementAudioComponent = this.ensureAndGetComponent(MovementAudioComponent.getComponentType())
fun Holder<EntityStore>.addMovementAudio(component: MovementAudioComponent) = this.addComponent(MovementAudioComponent.getComponentType(), component)
fun Holder<EntityStore>.removeMovementAudio() = this.tryRemoveComponent(MovementAudioComponent.getComponentType())

// NewSpawnComponent
fun ComponentAccessor<EntityStore>.newSpawn(ref: Ref<EntityStore>): NewSpawnComponent = this.getComponent(ref, NewSpawnComponent.getComponentType())!!
fun ComponentAccessor<EntityStore>.newSpawnOrNull(ref: Ref<EntityStore>): NewSpawnComponent? = this.getComponent(ref, NewSpawnComponent.getComponentType())
fun ComponentAccessor<EntityStore>.ensureNewSpawn(ref: Ref<EntityStore>): NewSpawnComponent = this.ensureAndGetComponent(ref, NewSpawnComponent.getComponentType())
fun ComponentAccessor<EntityStore>.addNewSpawn(ref: Ref<EntityStore>, component: NewSpawnComponent) = this.addComponent(ref, NewSpawnComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putNewSpawn(ref: Ref<EntityStore>, component: NewSpawnComponent) = this.putComponent(ref, NewSpawnComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeNewSpawn(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, NewSpawnComponent.getComponentType())
fun ArchetypeChunk<EntityStore>.newSpawn(index: Int): NewSpawnComponent = this.getComponent(index, NewSpawnComponent.getComponentType())!!
fun ArchetypeChunk<EntityStore>.newSpawnOrNull(index: Int): NewSpawnComponent? = this.getComponent(index, NewSpawnComponent.getComponentType())
val Ref<EntityStore>.newSpawnOrNull: NewSpawnComponent?
get() = this.store.getComponent(this, NewSpawnComponent.getComponentType())
val Ref<EntityStore>.newSpawn: NewSpawnComponent
    get() = this.store.getComponent(this, NewSpawnComponent.getComponentType())!!
val Holder<EntityStore>.newSpawnOrNull: NewSpawnComponent?
get() = this.getComponent(NewSpawnComponent.getComponentType())
var Holder<EntityStore>.newSpawn: NewSpawnComponent
get() = this.getComponent(NewSpawnComponent.getComponentType())!!
set(value) = this.putComponent(NewSpawnComponent.getComponentType(), value)
fun Holder<EntityStore>.ensureNewSpawn(): NewSpawnComponent = this.ensureAndGetComponent(NewSpawnComponent.getComponentType())
fun Holder<EntityStore>.addNewSpawn(component: NewSpawnComponent) = this.addComponent(NewSpawnComponent.getComponentType(), component)
fun Holder<EntityStore>.removeNewSpawn() = this.tryRemoveComponent(NewSpawnComponent.getComponentType())

// PersistentDynamicLight
fun ComponentAccessor<EntityStore>.persistentDynamicLight(ref: Ref<EntityStore>): PersistentDynamicLight = this.getComponent(ref, PersistentDynamicLight.getComponentType())!!
fun ComponentAccessor<EntityStore>.persistentDynamicLightOrNull(ref: Ref<EntityStore>): PersistentDynamicLight? = this.getComponent(ref, PersistentDynamicLight.getComponentType())
fun ComponentAccessor<EntityStore>.ensurePersistentDynamicLight(ref: Ref<EntityStore>): PersistentDynamicLight = this.ensureAndGetComponent(ref, PersistentDynamicLight.getComponentType())
fun ComponentAccessor<EntityStore>.addPersistentDynamicLight(ref: Ref<EntityStore>, component: PersistentDynamicLight) = this.addComponent(ref, PersistentDynamicLight.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putPersistentDynamicLight(ref: Ref<EntityStore>, component: PersistentDynamicLight) = this.putComponent(ref, PersistentDynamicLight.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removePersistentDynamicLight(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, PersistentDynamicLight.getComponentType())
fun ArchetypeChunk<EntityStore>.persistentDynamicLight(index: Int): PersistentDynamicLight = this.getComponent(index, PersistentDynamicLight.getComponentType())!!
fun ArchetypeChunk<EntityStore>.persistentDynamicLightOrNull(index: Int): PersistentDynamicLight? = this.getComponent(index, PersistentDynamicLight.getComponentType())
val Ref<EntityStore>.persistentDynamicLightOrNull: PersistentDynamicLight?
get() = this.store.getComponent(this, PersistentDynamicLight.getComponentType())
val Ref<EntityStore>.persistentDynamicLight: PersistentDynamicLight
    get() = this.store.getComponent(this, PersistentDynamicLight.getComponentType())!!
val Holder<EntityStore>.persistentDynamicLightOrNull: PersistentDynamicLight?
get() = this.getComponent(PersistentDynamicLight.getComponentType())
var Holder<EntityStore>.persistentDynamicLight: PersistentDynamicLight
get() = this.getComponent(PersistentDynamicLight.getComponentType())!!
set(value) = this.putComponent(PersistentDynamicLight.getComponentType(), value)
fun Holder<EntityStore>.ensurePersistentDynamicLight(): PersistentDynamicLight = this.ensureAndGetComponent(PersistentDynamicLight.getComponentType())
fun Holder<EntityStore>.addPersistentDynamicLight(component: PersistentDynamicLight) = this.addComponent(PersistentDynamicLight.getComponentType(), component)
fun Holder<EntityStore>.removePersistentDynamicLight() = this.tryRemoveComponent(PersistentDynamicLight.getComponentType())

// PersistentModel
fun ComponentAccessor<EntityStore>.persistentModel(ref: Ref<EntityStore>): PersistentModel = this.getComponent(ref, PersistentModel.getComponentType())!!
fun ComponentAccessor<EntityStore>.persistentModelOrNull(ref: Ref<EntityStore>): PersistentModel? = this.getComponent(ref, PersistentModel.getComponentType())
fun ComponentAccessor<EntityStore>.ensurePersistentModel(ref: Ref<EntityStore>): PersistentModel = this.ensureAndGetComponent(ref, PersistentModel.getComponentType())
fun ComponentAccessor<EntityStore>.addPersistentModel(ref: Ref<EntityStore>, component: PersistentModel) = this.addComponent(ref, PersistentModel.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putPersistentModel(ref: Ref<EntityStore>, component: PersistentModel) = this.putComponent(ref, PersistentModel.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removePersistentModel(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, PersistentModel.getComponentType())
fun ArchetypeChunk<EntityStore>.persistentModel(index: Int): PersistentModel = this.getComponent(index, PersistentModel.getComponentType())!!
fun ArchetypeChunk<EntityStore>.persistentModelOrNull(index: Int): PersistentModel? = this.getComponent(index, PersistentModel.getComponentType())
val Ref<EntityStore>.persistentModelOrNull: PersistentModel?
get() = this.store.getComponent(this, PersistentModel.getComponentType())
val Ref<EntityStore>.persistentModel: PersistentModel
    get() = this.store.getComponent(this, PersistentModel.getComponentType())!!
val Holder<EntityStore>.persistentModelOrNull: PersistentModel?
get() = this.getComponent(PersistentModel.getComponentType())
var Holder<EntityStore>.persistentModel: PersistentModel
get() = this.getComponent(PersistentModel.getComponentType())!!
set(value) = this.putComponent(PersistentModel.getComponentType(), value)
fun Holder<EntityStore>.ensurePersistentModel(): PersistentModel = this.ensureAndGetComponent(PersistentModel.getComponentType())
fun Holder<EntityStore>.addPersistentModel(component: PersistentModel) = this.addComponent(PersistentModel.getComponentType(), component)
fun Holder<EntityStore>.removePersistentModel() = this.tryRemoveComponent(PersistentModel.getComponentType())

// PositionDataComponent
fun ComponentAccessor<EntityStore>.positionData(ref: Ref<EntityStore>): PositionDataComponent = this.getComponent(ref, PositionDataComponent.getComponentType())!!
fun ComponentAccessor<EntityStore>.positionDataOrNull(ref: Ref<EntityStore>): PositionDataComponent? = this.getComponent(ref, PositionDataComponent.getComponentType())
fun ComponentAccessor<EntityStore>.ensurePositionData(ref: Ref<EntityStore>): PositionDataComponent = this.ensureAndGetComponent(ref, PositionDataComponent.getComponentType())
fun ComponentAccessor<EntityStore>.addPositionData(ref: Ref<EntityStore>, component: PositionDataComponent) = this.addComponent(ref, PositionDataComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putPositionData(ref: Ref<EntityStore>, component: PositionDataComponent) = this.putComponent(ref, PositionDataComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removePositionData(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, PositionDataComponent.getComponentType())
fun ArchetypeChunk<EntityStore>.positionData(index: Int): PositionDataComponent = this.getComponent(index, PositionDataComponent.getComponentType())!!
fun ArchetypeChunk<EntityStore>.positionDataOrNull(index: Int): PositionDataComponent? = this.getComponent(index, PositionDataComponent.getComponentType())
val Ref<EntityStore>.positionDataOrNull: PositionDataComponent?
get() = this.store.getComponent(this, PositionDataComponent.getComponentType())
val Ref<EntityStore>.positionData: PositionDataComponent
    get() = this.store.getComponent(this, PositionDataComponent.getComponentType())!!
val Holder<EntityStore>.positionDataOrNull: PositionDataComponent?
get() = this.getComponent(PositionDataComponent.getComponentType())
var Holder<EntityStore>.positionData: PositionDataComponent
get() = this.getComponent(PositionDataComponent.getComponentType())!!
set(value) = this.putComponent(PositionDataComponent.getComponentType(), value)
fun Holder<EntityStore>.ensurePositionData(): PositionDataComponent = this.ensureAndGetComponent(PositionDataComponent.getComponentType())
fun Holder<EntityStore>.addPositionData(component: PositionDataComponent) = this.addComponent(PositionDataComponent.getComponentType(), component)
fun Holder<EntityStore>.removePositionData() = this.tryRemoveComponent(PositionDataComponent.getComponentType())

// PropComponent
fun ComponentAccessor<EntityStore>.prop(ref: Ref<EntityStore>): Boolean = this.getComponent(ref, PropComponent.getComponentType()) != null
fun ComponentAccessor<EntityStore>.setProp(ref: Ref<EntityStore>, value: Boolean) {
    if (value) this.ensureAndGetComponent(ref, PropComponent.getComponentType())
    else this.tryRemoveComponent(ref, PropComponent.getComponentType())
}
fun ArchetypeChunk<EntityStore>.prop(index: Int): Boolean = this.getComponent(index, PropComponent.getComponentType()) != null
val Ref<EntityStore>.isProp: Boolean
    get() = this.store.getComponent(this, PropComponent.getComponentType()) != null
var Holder<EntityStore>.isProp: Boolean
get() = this.getComponent(PropComponent.getComponentType()) != null
set(value) {
    if (value) this.ensureComponent(PropComponent.getComponentType())
    else this.tryRemoveComponent(PropComponent.getComponentType())
}

// RespondToHit
fun ComponentAccessor<EntityStore>.respondToHit(ref: Ref<EntityStore>): Boolean = this.getComponent(ref, RespondToHit.getComponentType()) != null
fun ComponentAccessor<EntityStore>.setRespondToHit(ref: Ref<EntityStore>, value: Boolean) {
    if (value) this.ensureAndGetComponent(ref, RespondToHit.getComponentType())
    else this.tryRemoveComponent(ref, RespondToHit.getComponentType())
}
fun ArchetypeChunk<EntityStore>.respondToHit(index: Int): Boolean = this.getComponent(index, RespondToHit.getComponentType()) != null
val Ref<EntityStore>.isRespondToHit: Boolean
    get() = this.store.getComponent(this, RespondToHit.getComponentType()) != null
var Holder<EntityStore>.isRespondToHit: Boolean
get() = this.getComponent(RespondToHit.getComponentType()) != null
set(value) {
    if (value) this.ensureComponent(RespondToHit.getComponentType())
    else this.tryRemoveComponent(RespondToHit.getComponentType())
}

// RotateObjectComponent
fun ComponentAccessor<EntityStore>.rotateObject(ref: Ref<EntityStore>): RotateObjectComponent = this.getComponent(ref, RotateObjectComponent.getComponentType())!!
fun ComponentAccessor<EntityStore>.rotateObjectOrNull(ref: Ref<EntityStore>): RotateObjectComponent? = this.getComponent(ref, RotateObjectComponent.getComponentType())
fun ComponentAccessor<EntityStore>.ensureRotateObject(ref: Ref<EntityStore>): RotateObjectComponent = this.ensureAndGetComponent(ref, RotateObjectComponent.getComponentType())
fun ComponentAccessor<EntityStore>.addRotateObject(ref: Ref<EntityStore>, component: RotateObjectComponent) = this.addComponent(ref, RotateObjectComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putRotateObject(ref: Ref<EntityStore>, component: RotateObjectComponent) = this.putComponent(ref, RotateObjectComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeRotateObject(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, RotateObjectComponent.getComponentType())
fun ArchetypeChunk<EntityStore>.rotateObject(index: Int): RotateObjectComponent = this.getComponent(index, RotateObjectComponent.getComponentType())!!
fun ArchetypeChunk<EntityStore>.rotateObjectOrNull(index: Int): RotateObjectComponent? = this.getComponent(index, RotateObjectComponent.getComponentType())
val Ref<EntityStore>.rotateObjectOrNull: RotateObjectComponent?
get() = this.store.getComponent(this, RotateObjectComponent.getComponentType())
val Ref<EntityStore>.rotateObject: RotateObjectComponent
    get() = this.store.getComponent(this, RotateObjectComponent.getComponentType())!!
val Holder<EntityStore>.rotateObjectOrNull: RotateObjectComponent?
get() = this.getComponent(RotateObjectComponent.getComponentType())
var Holder<EntityStore>.rotateObject: RotateObjectComponent
get() = this.getComponent(RotateObjectComponent.getComponentType())!!
set(value) = this.putComponent(RotateObjectComponent.getComponentType(), value)
fun Holder<EntityStore>.ensureRotateObject(): RotateObjectComponent = this.ensureAndGetComponent(RotateObjectComponent.getComponentType())
fun Holder<EntityStore>.addRotateObject(component: RotateObjectComponent) = this.addComponent(RotateObjectComponent.getComponentType(), component)
fun Holder<EntityStore>.removeRotateObject() = this.tryRemoveComponent(RotateObjectComponent.getComponentType())

// SnapshotBuffer
fun ComponentAccessor<EntityStore>.snapshotBuffer(ref: Ref<EntityStore>): SnapshotBuffer = this.getComponent(ref, SnapshotBuffer.getComponentType())!!
fun ComponentAccessor<EntityStore>.snapshotBufferOrNull(ref: Ref<EntityStore>): SnapshotBuffer? = this.getComponent(ref, SnapshotBuffer.getComponentType())
fun ComponentAccessor<EntityStore>.ensureSnapshotBuffer(ref: Ref<EntityStore>): SnapshotBuffer = this.ensureAndGetComponent(ref, SnapshotBuffer.getComponentType())
fun ComponentAccessor<EntityStore>.addSnapshotBuffer(ref: Ref<EntityStore>, component: SnapshotBuffer) = this.addComponent(ref, SnapshotBuffer.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putSnapshotBuffer(ref: Ref<EntityStore>, component: SnapshotBuffer) = this.putComponent(ref, SnapshotBuffer.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeSnapshotBuffer(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, SnapshotBuffer.getComponentType())
fun ArchetypeChunk<EntityStore>.snapshotBuffer(index: Int): SnapshotBuffer = this.getComponent(index, SnapshotBuffer.getComponentType())!!
fun ArchetypeChunk<EntityStore>.snapshotBufferOrNull(index: Int): SnapshotBuffer? = this.getComponent(index, SnapshotBuffer.getComponentType())
val Ref<EntityStore>.snapshotBufferOrNull: SnapshotBuffer?
get() = this.store.getComponent(this, SnapshotBuffer.getComponentType())
val Ref<EntityStore>.snapshotBuffer: SnapshotBuffer
    get() = this.store.getComponent(this, SnapshotBuffer.getComponentType())!!
val Holder<EntityStore>.snapshotBufferOrNull: SnapshotBuffer?
get() = this.getComponent(SnapshotBuffer.getComponentType())
var Holder<EntityStore>.snapshotBuffer: SnapshotBuffer
get() = this.getComponent(SnapshotBuffer.getComponentType())!!
set(value) = this.putComponent(SnapshotBuffer.getComponentType(), value)
fun Holder<EntityStore>.ensureSnapshotBuffer(): SnapshotBuffer = this.ensureAndGetComponent(SnapshotBuffer.getComponentType())
fun Holder<EntityStore>.addSnapshotBuffer(component: SnapshotBuffer) = this.addComponent(SnapshotBuffer.getComponentType(), component)
fun Holder<EntityStore>.removeSnapshotBuffer() = this.tryRemoveComponent(SnapshotBuffer.getComponentType())

// TransformComponent
fun ComponentAccessor<EntityStore>.transform(ref: Ref<EntityStore>): TransformComponent = this.getComponent(ref, TransformComponent.getComponentType())!!
fun ComponentAccessor<EntityStore>.transformOrNull(ref: Ref<EntityStore>): TransformComponent? = this.getComponent(ref, TransformComponent.getComponentType())
fun ComponentAccessor<EntityStore>.ensureTransform(ref: Ref<EntityStore>): TransformComponent = this.ensureAndGetComponent(ref, TransformComponent.getComponentType())
fun ComponentAccessor<EntityStore>.addTransform(ref: Ref<EntityStore>, component: TransformComponent) = this.addComponent(ref, TransformComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putTransform(ref: Ref<EntityStore>, component: TransformComponent) = this.putComponent(ref, TransformComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeTransform(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, TransformComponent.getComponentType())
fun ArchetypeChunk<EntityStore>.transform(index: Int): TransformComponent = this.getComponent(index, TransformComponent.getComponentType())!!
fun ArchetypeChunk<EntityStore>.transformOrNull(index: Int): TransformComponent? = this.getComponent(index, TransformComponent.getComponentType())
val Ref<EntityStore>.transformOrNull: TransformComponent?
get() = this.store.getComponent(this, TransformComponent.getComponentType())
val Ref<EntityStore>.transform: TransformComponent
    get() = this.store.getComponent(this, TransformComponent.getComponentType())!!
val Holder<EntityStore>.transformOrNull: TransformComponent?
get() = this.getComponent(TransformComponent.getComponentType())
var Holder<EntityStore>.transform: TransformComponent
get() = this.getComponent(TransformComponent.getComponentType())!!
set(value) = this.putComponent(TransformComponent.getComponentType(), value)
fun Holder<EntityStore>.ensureTransform(): TransformComponent = this.ensureAndGetComponent(TransformComponent.getComponentType())
fun Holder<EntityStore>.addTransform(component: TransformComponent) = this.addComponent(TransformComponent.getComponentType(), component)
fun Holder<EntityStore>.removeTransform() = this.tryRemoveComponent(TransformComponent.getComponentType())

// WorldGenId
fun ComponentAccessor<EntityStore>.worldGenId(ref: Ref<EntityStore>): WorldGenId = this.getComponent(ref, WorldGenId.getComponentType())!!
fun ComponentAccessor<EntityStore>.worldGenIdOrNull(ref: Ref<EntityStore>): WorldGenId? = this.getComponent(ref, WorldGenId.getComponentType())
fun ComponentAccessor<EntityStore>.ensureWorldGenId(ref: Ref<EntityStore>): WorldGenId = this.ensureAndGetComponent(ref, WorldGenId.getComponentType())
fun ComponentAccessor<EntityStore>.addWorldGenId(ref: Ref<EntityStore>, component: WorldGenId) = this.addComponent(ref, WorldGenId.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putWorldGenId(ref: Ref<EntityStore>, component: WorldGenId) = this.putComponent(ref, WorldGenId.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeWorldGenId(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, WorldGenId.getComponentType())
fun ArchetypeChunk<EntityStore>.worldGenId(index: Int): WorldGenId = this.getComponent(index, WorldGenId.getComponentType())!!
fun ArchetypeChunk<EntityStore>.worldGenIdOrNull(index: Int): WorldGenId? = this.getComponent(index, WorldGenId.getComponentType())
val Ref<EntityStore>.worldGenIdOrNull: WorldGenId?
get() = this.store.getComponent(this, WorldGenId.getComponentType())
val Ref<EntityStore>.worldGenId: WorldGenId
    get() = this.store.getComponent(this, WorldGenId.getComponentType())!!
val Holder<EntityStore>.worldGenIdOrNull: WorldGenId?
get() = this.getComponent(WorldGenId.getComponentType())
var Holder<EntityStore>.worldGenId: WorldGenId
get() = this.getComponent(WorldGenId.getComponentType())!!
set(value) = this.putComponent(WorldGenId.getComponentType(), value)
fun Holder<EntityStore>.ensureWorldGenId(): WorldGenId = this.ensureAndGetComponent(WorldGenId.getComponentType())
fun Holder<EntityStore>.addWorldGenId(component: WorldGenId) = this.addComponent(WorldGenId.getComponentType(), component)
fun Holder<EntityStore>.removeWorldGenId() = this.tryRemoveComponent(WorldGenId.getComponentType())

// Frozen
fun ComponentAccessor<EntityStore>.frozen(ref: Ref<EntityStore>): Boolean = this.getComponent(ref, Frozen.getComponentType()) != null
fun ComponentAccessor<EntityStore>.setFrozen(ref: Ref<EntityStore>, value: Boolean) {
    if (value) this.ensureAndGetComponent(ref, Frozen.getComponentType())
    else this.tryRemoveComponent(ref, Frozen.getComponentType())
}
fun ArchetypeChunk<EntityStore>.frozen(index: Int): Boolean = this.getComponent(index, Frozen.getComponentType()) != null
val Ref<EntityStore>.isFrozen: Boolean
    get() = this.store.getComponent(this, Frozen.getComponentType()) != null
var Holder<EntityStore>.isFrozen: Boolean
get() = this.getComponent(Frozen.getComponentType()) != null
set(value) {
    if (value) this.ensureComponent(Frozen.getComponentType())
    else this.tryRemoveComponent(Frozen.getComponentType())
}

// UUIDComponent
fun ComponentAccessor<EntityStore>.uuid(ref: Ref<EntityStore>): UUIDComponent = this.getComponent(ref, UUIDComponent.getComponentType())!!
fun ComponentAccessor<EntityStore>.uuidOrNull(ref: Ref<EntityStore>): UUIDComponent? = this.getComponent(ref, UUIDComponent.getComponentType())
fun ComponentAccessor<EntityStore>.ensureUuid(ref: Ref<EntityStore>): UUIDComponent = this.ensureAndGetComponent(ref, UUIDComponent.getComponentType())
fun ComponentAccessor<EntityStore>.addUuid(ref: Ref<EntityStore>, component: UUIDComponent) = this.addComponent(ref, UUIDComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putUuid(ref: Ref<EntityStore>, component: UUIDComponent) = this.putComponent(ref, UUIDComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeUuid(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, UUIDComponent.getComponentType())
fun ArchetypeChunk<EntityStore>.uuid(index: Int): UUIDComponent = this.getComponent(index, UUIDComponent.getComponentType())!!
fun ArchetypeChunk<EntityStore>.uuidOrNull(index: Int): UUIDComponent? = this.getComponent(index, UUIDComponent.getComponentType())
val Ref<EntityStore>.uuidOrNull: UUIDComponent?
get() = this.store.getComponent(this, UUIDComponent.getComponentType())
val Ref<EntityStore>.uuid: UUIDComponent
    get() = this.store.getComponent(this, UUIDComponent.getComponentType())!!
val Holder<EntityStore>.uuidOrNull: UUIDComponent?
get() = this.getComponent(UUIDComponent.getComponentType())
var Holder<EntityStore>.uuid: UUIDComponent
get() = this.getComponent(UUIDComponent.getComponentType())!!
set(value) = this.putComponent(UUIDComponent.getComponentType(), value)
fun Holder<EntityStore>.ensureUuid(): UUIDComponent = this.ensureAndGetComponent(UUIDComponent.getComponentType())
fun Holder<EntityStore>.addUuid(component: UUIDComponent) = this.addComponent(UUIDComponent.getComponentType(), component)
fun Holder<EntityStore>.removeUuid() = this.tryRemoveComponent(UUIDComponent.getComponentType())

// KnockbackComponent
fun ComponentAccessor<EntityStore>.knockback(ref: Ref<EntityStore>): KnockbackComponent = this.getComponent(ref, KnockbackComponent.getComponentType())!!
fun ComponentAccessor<EntityStore>.knockbackOrNull(ref: Ref<EntityStore>): KnockbackComponent? = this.getComponent(ref, KnockbackComponent.getComponentType())
fun ComponentAccessor<EntityStore>.ensureKnockback(ref: Ref<EntityStore>): KnockbackComponent = this.ensureAndGetComponent(ref, KnockbackComponent.getComponentType())
fun ComponentAccessor<EntityStore>.addKnockback(ref: Ref<EntityStore>, component: KnockbackComponent) = this.addComponent(ref, KnockbackComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putKnockback(ref: Ref<EntityStore>, component: KnockbackComponent) = this.putComponent(ref, KnockbackComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeKnockback(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, KnockbackComponent.getComponentType())
fun ArchetypeChunk<EntityStore>.knockback(index: Int): KnockbackComponent = this.getComponent(index, KnockbackComponent.getComponentType())!!
fun ArchetypeChunk<EntityStore>.knockbackOrNull(index: Int): KnockbackComponent? = this.getComponent(index, KnockbackComponent.getComponentType())
val Ref<EntityStore>.knockbackOrNull: KnockbackComponent?
get() = this.store.getComponent(this, KnockbackComponent.getComponentType())
val Ref<EntityStore>.knockback: KnockbackComponent
    get() = this.store.getComponent(this, KnockbackComponent.getComponentType())!!
val Holder<EntityStore>.knockbackOrNull: KnockbackComponent?
get() = this.getComponent(KnockbackComponent.getComponentType())
var Holder<EntityStore>.knockback: KnockbackComponent
get() = this.getComponent(KnockbackComponent.getComponentType())!!
set(value) = this.putComponent(KnockbackComponent.getComponentType(), value)
fun Holder<EntityStore>.ensureKnockback(): KnockbackComponent = this.ensureAndGetComponent(KnockbackComponent.getComponentType())
fun Holder<EntityStore>.addKnockback(component: KnockbackComponent) = this.addComponent(KnockbackComponent.getComponentType(), component)
fun Holder<EntityStore>.removeKnockback() = this.tryRemoveComponent(KnockbackComponent.getComponentType())

// Nameplate
fun ComponentAccessor<EntityStore>.nameplate(ref: Ref<EntityStore>): Nameplate = this.getComponent(ref, Nameplate.getComponentType())!!
fun ComponentAccessor<EntityStore>.nameplateOrNull(ref: Ref<EntityStore>): Nameplate? = this.getComponent(ref, Nameplate.getComponentType())
fun ComponentAccessor<EntityStore>.ensureNameplate(ref: Ref<EntityStore>): Nameplate = this.ensureAndGetComponent(ref, Nameplate.getComponentType())
fun ComponentAccessor<EntityStore>.addNameplate(ref: Ref<EntityStore>, component: Nameplate) = this.addComponent(ref, Nameplate.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putNameplate(ref: Ref<EntityStore>, component: Nameplate) = this.putComponent(ref, Nameplate.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeNameplate(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, Nameplate.getComponentType())
fun ArchetypeChunk<EntityStore>.nameplate(index: Int): Nameplate = this.getComponent(index, Nameplate.getComponentType())!!
fun ArchetypeChunk<EntityStore>.nameplateOrNull(index: Int): Nameplate? = this.getComponent(index, Nameplate.getComponentType())
val Ref<EntityStore>.nameplateOrNull: Nameplate?
get() = this.store.getComponent(this, Nameplate.getComponentType())
val Ref<EntityStore>.nameplate: Nameplate
    get() = this.store.getComponent(this, Nameplate.getComponentType())!!
val Holder<EntityStore>.nameplateOrNull: Nameplate?
get() = this.getComponent(Nameplate.getComponentType())
var Holder<EntityStore>.nameplate: Nameplate
get() = this.getComponent(Nameplate.getComponentType())!!
set(value) = this.putComponent(Nameplate.getComponentType(), value)
fun Holder<EntityStore>.ensureNameplate(): Nameplate = this.ensureAndGetComponent(Nameplate.getComponentType())
fun Holder<EntityStore>.addNameplate(component: Nameplate) = this.addComponent(Nameplate.getComponentType(), component)
fun Holder<EntityStore>.removeNameplate() = this.tryRemoveComponent(Nameplate.getComponentType())

// EntityGroup
fun ComponentAccessor<EntityStore>.entityGroup(ref: Ref<EntityStore>): EntityGroup = this.getComponent(ref, EntityGroup.getComponentType())!!
fun ComponentAccessor<EntityStore>.entityGroupOrNull(ref: Ref<EntityStore>): EntityGroup? = this.getComponent(ref, EntityGroup.getComponentType())
fun ComponentAccessor<EntityStore>.ensureEntityGroup(ref: Ref<EntityStore>): EntityGroup = this.ensureAndGetComponent(ref, EntityGroup.getComponentType())
fun ComponentAccessor<EntityStore>.addEntityGroup(ref: Ref<EntityStore>, component: EntityGroup) = this.addComponent(ref, EntityGroup.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putEntityGroup(ref: Ref<EntityStore>, component: EntityGroup) = this.putComponent(ref, EntityGroup.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeEntityGroup(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, EntityGroup.getComponentType())
fun ArchetypeChunk<EntityStore>.entityGroup(index: Int): EntityGroup = this.getComponent(index, EntityGroup.getComponentType())!!
fun ArchetypeChunk<EntityStore>.entityGroupOrNull(index: Int): EntityGroup? = this.getComponent(index, EntityGroup.getComponentType())
val Ref<EntityStore>.entityGroupOrNull: EntityGroup?
get() = this.store.getComponent(this, EntityGroup.getComponentType())
val Ref<EntityStore>.entityGroup: EntityGroup
    get() = this.store.getComponent(this, EntityGroup.getComponentType())!!
val Holder<EntityStore>.entityGroupOrNull: EntityGroup?
get() = this.getComponent(EntityGroup.getComponentType())
var Holder<EntityStore>.entityGroup: EntityGroup
get() = this.getComponent(EntityGroup.getComponentType())!!
set(value) = this.putComponent(EntityGroup.getComponentType(), value)
fun Holder<EntityStore>.ensureEntityGroup(): EntityGroup = this.ensureAndGetComponent(EntityGroup.getComponentType())
fun Holder<EntityStore>.addEntityGroup(component: EntityGroup) = this.addComponent(EntityGroup.getComponentType(), component)
fun Holder<EntityStore>.removeEntityGroup() = this.tryRemoveComponent(EntityGroup.getComponentType())

// PhysicsValues
fun ComponentAccessor<EntityStore>.physicsValues(ref: Ref<EntityStore>): PhysicsValues = this.getComponent(ref, PhysicsValues.getComponentType())!!
fun ComponentAccessor<EntityStore>.physicsValuesOrNull(ref: Ref<EntityStore>): PhysicsValues? = this.getComponent(ref, PhysicsValues.getComponentType())
fun ComponentAccessor<EntityStore>.ensurePhysicsValues(ref: Ref<EntityStore>): PhysicsValues = this.ensureAndGetComponent(ref, PhysicsValues.getComponentType())
fun ComponentAccessor<EntityStore>.addPhysicsValues(ref: Ref<EntityStore>, component: PhysicsValues) = this.addComponent(ref, PhysicsValues.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putPhysicsValues(ref: Ref<EntityStore>, component: PhysicsValues) = this.putComponent(ref, PhysicsValues.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removePhysicsValues(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, PhysicsValues.getComponentType())
fun ArchetypeChunk<EntityStore>.physicsValues(index: Int): PhysicsValues = this.getComponent(index, PhysicsValues.getComponentType())!!
fun ArchetypeChunk<EntityStore>.physicsValuesOrNull(index: Int): PhysicsValues? = this.getComponent(index, PhysicsValues.getComponentType())
val Ref<EntityStore>.physicsValuesOrNull: PhysicsValues?
get() = this.store.getComponent(this, PhysicsValues.getComponentType())
val Ref<EntityStore>.physicsValues: PhysicsValues
    get() = this.store.getComponent(this, PhysicsValues.getComponentType())!!
val Holder<EntityStore>.physicsValuesOrNull: PhysicsValues?
get() = this.getComponent(PhysicsValues.getComponentType())
var Holder<EntityStore>.physicsValues: PhysicsValues
get() = this.getComponent(PhysicsValues.getComponentType())!!
set(value) = this.putComponent(PhysicsValues.getComponentType(), value)
fun Holder<EntityStore>.ensurePhysicsValues(): PhysicsValues = this.ensureAndGetComponent(PhysicsValues.getComponentType())
fun Holder<EntityStore>.addPhysicsValues(component: PhysicsValues) = this.addComponent(PhysicsValues.getComponentType(), component)
fun Holder<EntityStore>.removePhysicsValues() = this.tryRemoveComponent(PhysicsValues.getComponentType())

// Velocity
fun ComponentAccessor<EntityStore>.velocity(ref: Ref<EntityStore>): Velocity = this.getComponent(ref, Velocity.getComponentType())!!
fun ComponentAccessor<EntityStore>.velocityOrNull(ref: Ref<EntityStore>): Velocity? = this.getComponent(ref, Velocity.getComponentType())
fun ComponentAccessor<EntityStore>.ensureVelocity(ref: Ref<EntityStore>): Velocity = this.ensureAndGetComponent(ref, Velocity.getComponentType())
fun ComponentAccessor<EntityStore>.addVelocity(ref: Ref<EntityStore>, component: Velocity) = this.addComponent(ref, Velocity.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putVelocity(ref: Ref<EntityStore>, component: Velocity) = this.putComponent(ref, Velocity.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeVelocity(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, Velocity.getComponentType())
fun ArchetypeChunk<EntityStore>.velocity(index: Int): Velocity = this.getComponent(index, Velocity.getComponentType())!!
fun ArchetypeChunk<EntityStore>.velocityOrNull(index: Int): Velocity? = this.getComponent(index, Velocity.getComponentType())
val Ref<EntityStore>.velocityOrNull: Velocity?
get() = this.store.getComponent(this, Velocity.getComponentType())
val Ref<EntityStore>.velocity: Velocity
    get() = this.store.getComponent(this, Velocity.getComponentType())!!
val Holder<EntityStore>.velocityOrNull: Velocity?
get() = this.getComponent(Velocity.getComponentType())
var Holder<EntityStore>.velocity: Velocity
get() = this.getComponent(Velocity.getComponentType())!!
set(value) = this.putComponent(Velocity.getComponentType(), value)
fun Holder<EntityStore>.ensureVelocity(): Velocity = this.ensureAndGetComponent(Velocity.getComponentType())
fun Holder<EntityStore>.addVelocity(component: Velocity) = this.addComponent(Velocity.getComponentType(), component)
fun Holder<EntityStore>.removeVelocity() = this.tryRemoveComponent(Velocity.getComponentType())

// DeathComponent
fun ComponentAccessor<EntityStore>.death(ref: Ref<EntityStore>): DeathComponent = this.getComponent(ref, DeathComponent.getComponentType())!!
fun ComponentAccessor<EntityStore>.deathOrNull(ref: Ref<EntityStore>): DeathComponent? = this.getComponent(ref, DeathComponent.getComponentType())
fun ComponentAccessor<EntityStore>.ensureDeath(ref: Ref<EntityStore>): DeathComponent = this.ensureAndGetComponent(ref, DeathComponent.getComponentType())
fun ComponentAccessor<EntityStore>.addDeath(ref: Ref<EntityStore>, component: DeathComponent) = this.addComponent(ref, DeathComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putDeath(ref: Ref<EntityStore>, component: DeathComponent) = this.putComponent(ref, DeathComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeDeath(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, DeathComponent.getComponentType())
fun ArchetypeChunk<EntityStore>.death(index: Int): DeathComponent = this.getComponent(index, DeathComponent.getComponentType())!!
fun ArchetypeChunk<EntityStore>.deathOrNull(index: Int): DeathComponent? = this.getComponent(index, DeathComponent.getComponentType())
val Ref<EntityStore>.deathOrNull: DeathComponent?
get() = this.store.getComponent(this, DeathComponent.getComponentType())
val Ref<EntityStore>.death: DeathComponent
    get() = this.store.getComponent(this, DeathComponent.getComponentType())!!
val Holder<EntityStore>.deathOrNull: DeathComponent?
get() = this.getComponent(DeathComponent.getComponentType())
var Holder<EntityStore>.death: DeathComponent
get() = this.getComponent(DeathComponent.getComponentType())!!
set(value) = this.putComponent(DeathComponent.getComponentType(), value)
fun Holder<EntityStore>.ensureDeath(): DeathComponent = this.ensureAndGetComponent(DeathComponent.getComponentType())
fun Holder<EntityStore>.addDeath(component: DeathComponent) = this.addComponent(DeathComponent.getComponentType(), component)
fun Holder<EntityStore>.removeDeath() = this.tryRemoveComponent(DeathComponent.getComponentType())

// ItemComponent
fun ComponentAccessor<EntityStore>.item(ref: Ref<EntityStore>): ItemComponent = this.getComponent(ref, ItemComponent.getComponentType())!!
fun ComponentAccessor<EntityStore>.itemOrNull(ref: Ref<EntityStore>): ItemComponent? = this.getComponent(ref, ItemComponent.getComponentType())
fun ComponentAccessor<EntityStore>.ensureItem(ref: Ref<EntityStore>): ItemComponent = this.ensureAndGetComponent(ref, ItemComponent.getComponentType())
fun ComponentAccessor<EntityStore>.addItem(ref: Ref<EntityStore>, component: ItemComponent) = this.addComponent(ref, ItemComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putItem(ref: Ref<EntityStore>, component: ItemComponent) = this.putComponent(ref, ItemComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeItem(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, ItemComponent.getComponentType())
fun ArchetypeChunk<EntityStore>.item(index: Int): ItemComponent = this.getComponent(index, ItemComponent.getComponentType())!!
fun ArchetypeChunk<EntityStore>.itemOrNull(index: Int): ItemComponent? = this.getComponent(index, ItemComponent.getComponentType())
val Ref<EntityStore>.itemOrNull: ItemComponent?
get() = this.store.getComponent(this, ItemComponent.getComponentType())
val Ref<EntityStore>.item: ItemComponent
    get() = this.store.getComponent(this, ItemComponent.getComponentType())!!
val Holder<EntityStore>.itemOrNull: ItemComponent?
get() = this.getComponent(ItemComponent.getComponentType())
var Holder<EntityStore>.item: ItemComponent
get() = this.getComponent(ItemComponent.getComponentType())!!
set(value) = this.putComponent(ItemComponent.getComponentType(), value)
fun Holder<EntityStore>.ensureItem(): ItemComponent = this.ensureAndGetComponent(ItemComponent.getComponentType())
fun Holder<EntityStore>.addItem(component: ItemComponent) = this.addComponent(ItemComponent.getComponentType(), component)
fun Holder<EntityStore>.removeItem() = this.tryRemoveComponent(ItemComponent.getComponentType())

// PickupItemComponent
fun ComponentAccessor<EntityStore>.pickupItem(ref: Ref<EntityStore>): PickupItemComponent = this.getComponent(ref, PickupItemComponent.getComponentType())!!
fun ComponentAccessor<EntityStore>.pickupItemOrNull(ref: Ref<EntityStore>): PickupItemComponent? = this.getComponent(ref, PickupItemComponent.getComponentType())
fun ComponentAccessor<EntityStore>.ensurePickupItem(ref: Ref<EntityStore>): PickupItemComponent = this.ensureAndGetComponent(ref, PickupItemComponent.getComponentType())
fun ComponentAccessor<EntityStore>.addPickupItem(ref: Ref<EntityStore>, component: PickupItemComponent) = this.addComponent(ref, PickupItemComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putPickupItem(ref: Ref<EntityStore>, component: PickupItemComponent) = this.putComponent(ref, PickupItemComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removePickupItem(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, PickupItemComponent.getComponentType())
fun ArchetypeChunk<EntityStore>.pickupItem(index: Int): PickupItemComponent = this.getComponent(index, PickupItemComponent.getComponentType())!!
fun ArchetypeChunk<EntityStore>.pickupItemOrNull(index: Int): PickupItemComponent? = this.getComponent(index, PickupItemComponent.getComponentType())
val Ref<EntityStore>.pickupItemOrNull: PickupItemComponent?
get() = this.store.getComponent(this, PickupItemComponent.getComponentType())
val Ref<EntityStore>.pickupItem: PickupItemComponent
    get() = this.store.getComponent(this, PickupItemComponent.getComponentType())!!
val Holder<EntityStore>.pickupItemOrNull: PickupItemComponent?
get() = this.getComponent(PickupItemComponent.getComponentType())
var Holder<EntityStore>.pickupItem: PickupItemComponent
get() = this.getComponent(PickupItemComponent.getComponentType())!!
set(value) = this.putComponent(PickupItemComponent.getComponentType(), value)
fun Holder<EntityStore>.ensurePickupItem(): PickupItemComponent = this.ensureAndGetComponent(PickupItemComponent.getComponentType())
fun Holder<EntityStore>.addPickupItem(component: PickupItemComponent) = this.addComponent(PickupItemComponent.getComponentType(), component)
fun Holder<EntityStore>.removePickupItem() = this.tryRemoveComponent(PickupItemComponent.getComponentType())

// PreventItemMerging
fun ComponentAccessor<EntityStore>.preventItemMerging(ref: Ref<EntityStore>): Boolean = this.getComponent(ref, PreventItemMerging.getComponentType()) != null
fun ComponentAccessor<EntityStore>.setPreventItemMerging(ref: Ref<EntityStore>, value: Boolean) {
    if (value) this.ensureAndGetComponent(ref, PreventItemMerging.getComponentType())
    else this.tryRemoveComponent(ref, PreventItemMerging.getComponentType())
}
fun ArchetypeChunk<EntityStore>.preventItemMerging(index: Int): Boolean = this.getComponent(index, PreventItemMerging.getComponentType()) != null
val Ref<EntityStore>.isPreventItemMerging: Boolean
    get() = this.store.getComponent(this, PreventItemMerging.getComponentType()) != null
var Holder<EntityStore>.isPreventItemMerging: Boolean
get() = this.getComponent(PreventItemMerging.getComponentType()) != null
set(value) {
    if (value) this.ensureComponent(PreventItemMerging.getComponentType())
    else this.tryRemoveComponent(PreventItemMerging.getComponentType())
}

// PreventPickup
fun ComponentAccessor<EntityStore>.preventPickup(ref: Ref<EntityStore>): Boolean = this.getComponent(ref, PreventPickup.getComponentType()) != null
fun ComponentAccessor<EntityStore>.setPreventPickup(ref: Ref<EntityStore>, value: Boolean) {
    if (value) this.ensureAndGetComponent(ref, PreventPickup.getComponentType())
    else this.tryRemoveComponent(ref, PreventPickup.getComponentType())
}
fun ArchetypeChunk<EntityStore>.preventPickup(index: Int): Boolean = this.getComponent(index, PreventPickup.getComponentType()) != null
val Ref<EntityStore>.isPreventPickup: Boolean
    get() = this.store.getComponent(this, PreventPickup.getComponentType()) != null
var Holder<EntityStore>.isPreventPickup: Boolean
get() = this.getComponent(PreventPickup.getComponentType()) != null
set(value) {
    if (value) this.ensureComponent(PreventPickup.getComponentType())
    else this.tryRemoveComponent(PreventPickup.getComponentType())
}

// Repulsion
fun ComponentAccessor<EntityStore>.repulsion(ref: Ref<EntityStore>): Repulsion = this.getComponent(ref, Repulsion.getComponentType())!!
fun ComponentAccessor<EntityStore>.repulsionOrNull(ref: Ref<EntityStore>): Repulsion? = this.getComponent(ref, Repulsion.getComponentType())
fun ComponentAccessor<EntityStore>.ensureRepulsion(ref: Ref<EntityStore>): Repulsion = this.ensureAndGetComponent(ref, Repulsion.getComponentType())
fun ComponentAccessor<EntityStore>.addRepulsion(ref: Ref<EntityStore>, component: Repulsion) = this.addComponent(ref, Repulsion.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putRepulsion(ref: Ref<EntityStore>, component: Repulsion) = this.putComponent(ref, Repulsion.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeRepulsion(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, Repulsion.getComponentType())
fun ArchetypeChunk<EntityStore>.repulsion(index: Int): Repulsion = this.getComponent(index, Repulsion.getComponentType())!!
fun ArchetypeChunk<EntityStore>.repulsionOrNull(index: Int): Repulsion? = this.getComponent(index, Repulsion.getComponentType())
val Ref<EntityStore>.repulsionOrNull: Repulsion?
get() = this.store.getComponent(this, Repulsion.getComponentType())
val Ref<EntityStore>.repulsion: Repulsion
    get() = this.store.getComponent(this, Repulsion.getComponentType())!!
val Holder<EntityStore>.repulsionOrNull: Repulsion?
get() = this.getComponent(Repulsion.getComponentType())
var Holder<EntityStore>.repulsion: Repulsion
get() = this.getComponent(Repulsion.getComponentType())!!
set(value) = this.putComponent(Repulsion.getComponentType(), value)
fun Holder<EntityStore>.ensureRepulsion(): Repulsion = this.ensureAndGetComponent(Repulsion.getComponentType())
fun Holder<EntityStore>.addRepulsion(component: Repulsion) = this.addComponent(Repulsion.getComponentType(), component)
fun Holder<EntityStore>.removeRepulsion() = this.tryRemoveComponent(Repulsion.getComponentType())

// Teleport
fun ComponentAccessor<EntityStore>.teleport(ref: Ref<EntityStore>): Teleport = this.getComponent(ref, Teleport.getComponentType())!!
fun ComponentAccessor<EntityStore>.teleportOrNull(ref: Ref<EntityStore>): Teleport? = this.getComponent(ref, Teleport.getComponentType())
fun ComponentAccessor<EntityStore>.ensureTeleport(ref: Ref<EntityStore>): Teleport = this.ensureAndGetComponent(ref, Teleport.getComponentType())
fun ComponentAccessor<EntityStore>.addTeleport(ref: Ref<EntityStore>, component: Teleport) = this.addComponent(ref, Teleport.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putTeleport(ref: Ref<EntityStore>, component: Teleport) = this.putComponent(ref, Teleport.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeTeleport(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, Teleport.getComponentType())
fun ArchetypeChunk<EntityStore>.teleport(index: Int): Teleport = this.getComponent(index, Teleport.getComponentType())!!
fun ArchetypeChunk<EntityStore>.teleportOrNull(index: Int): Teleport? = this.getComponent(index, Teleport.getComponentType())
val Ref<EntityStore>.teleportOrNull: Teleport?
get() = this.store.getComponent(this, Teleport.getComponentType())
val Ref<EntityStore>.teleport: Teleport
    get() = this.store.getComponent(this, Teleport.getComponentType())!!
val Holder<EntityStore>.teleportOrNull: Teleport?
get() = this.getComponent(Teleport.getComponentType())
var Holder<EntityStore>.teleport: Teleport
get() = this.getComponent(Teleport.getComponentType())!!
set(value) = this.putComponent(Teleport.getComponentType(), value)
fun Holder<EntityStore>.ensureTeleport(): Teleport = this.ensureAndGetComponent(Teleport.getComponentType())
fun Holder<EntityStore>.addTeleport(component: Teleport) = this.addComponent(Teleport.getComponentType(), component)
fun Holder<EntityStore>.removeTeleport() = this.tryRemoveComponent(Teleport.getComponentType())

// EntityStatMap
fun ComponentAccessor<EntityStore>.entityStats(ref: Ref<EntityStore>): EntityStatMap = this.getComponent(ref, EntityStatMap.getComponentType())!!
fun ComponentAccessor<EntityStore>.entityStatsOrNull(ref: Ref<EntityStore>): EntityStatMap? = this.getComponent(ref, EntityStatMap.getComponentType())
fun ComponentAccessor<EntityStore>.ensureEntityStats(ref: Ref<EntityStore>): EntityStatMap = this.ensureAndGetComponent(ref, EntityStatMap.getComponentType())
fun ComponentAccessor<EntityStore>.addEntityStats(ref: Ref<EntityStore>, component: EntityStatMap) = this.addComponent(ref, EntityStatMap.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putEntityStats(ref: Ref<EntityStore>, component: EntityStatMap) = this.putComponent(ref, EntityStatMap.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeEntityStats(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, EntityStatMap.getComponentType())
fun ArchetypeChunk<EntityStore>.entityStats(index: Int): EntityStatMap = this.getComponent(index, EntityStatMap.getComponentType())!!
fun ArchetypeChunk<EntityStore>.entityStatsOrNull(index: Int): EntityStatMap? = this.getComponent(index, EntityStatMap.getComponentType())
val Ref<EntityStore>.entityStatsOrNull: EntityStatMap?
get() = this.store.getComponent(this, EntityStatMap.getComponentType())
val Ref<EntityStore>.entityStats: EntityStatMap
    get() = this.store.getComponent(this, EntityStatMap.getComponentType())!!
val Holder<EntityStore>.entityStatsOrNull: EntityStatMap?
get() = this.getComponent(EntityStatMap.getComponentType())
var Holder<EntityStore>.entityStats: EntityStatMap
get() = this.getComponent(EntityStatMap.getComponentType())!!
set(value) = this.putComponent(EntityStatMap.getComponentType(), value)
fun Holder<EntityStore>.ensureEntityStats(): EntityStatMap = this.ensureAndGetComponent(EntityStatMap.getComponentType())
fun Holder<EntityStore>.addEntityStats(component: EntityStatMap) = this.addComponent(EntityStatMap.getComponentType(), component)
fun Holder<EntityStore>.removeEntityStats() = this.tryRemoveComponent(EntityStatMap.getComponentType())

// PredictedProjectile
fun ComponentAccessor<EntityStore>.predictedProjectile(ref: Ref<EntityStore>): PredictedProjectile = this.getComponent(ref, PredictedProjectile.getComponentType())!!
fun ComponentAccessor<EntityStore>.predictedProjectileOrNull(ref: Ref<EntityStore>): PredictedProjectile? = this.getComponent(ref, PredictedProjectile.getComponentType())
fun ComponentAccessor<EntityStore>.ensurePredictedProjectile(ref: Ref<EntityStore>): PredictedProjectile = this.ensureAndGetComponent(ref, PredictedProjectile.getComponentType())
fun ComponentAccessor<EntityStore>.addPredictedProjectile(ref: Ref<EntityStore>, component: PredictedProjectile) = this.addComponent(ref, PredictedProjectile.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putPredictedProjectile(ref: Ref<EntityStore>, component: PredictedProjectile) = this.putComponent(ref, PredictedProjectile.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removePredictedProjectile(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, PredictedProjectile.getComponentType())
fun ArchetypeChunk<EntityStore>.predictedProjectile(index: Int): PredictedProjectile = this.getComponent(index, PredictedProjectile.getComponentType())!!
fun ArchetypeChunk<EntityStore>.predictedProjectileOrNull(index: Int): PredictedProjectile? = this.getComponent(index, PredictedProjectile.getComponentType())
val Ref<EntityStore>.predictedProjectileOrNull: PredictedProjectile?
get() = this.store.getComponent(this, PredictedProjectile.getComponentType())
val Ref<EntityStore>.predictedProjectile: PredictedProjectile
    get() = this.store.getComponent(this, PredictedProjectile.getComponentType())!!
val Holder<EntityStore>.predictedProjectileOrNull: PredictedProjectile?
get() = this.getComponent(PredictedProjectile.getComponentType())
var Holder<EntityStore>.predictedProjectile: PredictedProjectile
get() = this.getComponent(PredictedProjectile.getComponentType())!!
set(value) = this.putComponent(PredictedProjectile.getComponentType(), value)
fun Holder<EntityStore>.ensurePredictedProjectile(): PredictedProjectile = this.ensureAndGetComponent(PredictedProjectile.getComponentType())
fun Holder<EntityStore>.addPredictedProjectile(component: PredictedProjectile) = this.addComponent(PredictedProjectile.getComponentType(), component)
fun Holder<EntityStore>.removePredictedProjectile() = this.tryRemoveComponent(PredictedProjectile.getComponentType())

// ProjectileComponent
fun ComponentAccessor<EntityStore>.projectile(ref: Ref<EntityStore>): ProjectileComponent = this.getComponent(ref, ProjectileComponent.getComponentType())!!
fun ComponentAccessor<EntityStore>.projectileOrNull(ref: Ref<EntityStore>): ProjectileComponent? = this.getComponent(ref, ProjectileComponent.getComponentType())
fun ComponentAccessor<EntityStore>.ensureProjectile(ref: Ref<EntityStore>): ProjectileComponent = this.ensureAndGetComponent(ref, ProjectileComponent.getComponentType())
fun ComponentAccessor<EntityStore>.addProjectile(ref: Ref<EntityStore>, component: ProjectileComponent) = this.addComponent(ref, ProjectileComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putProjectile(ref: Ref<EntityStore>, component: ProjectileComponent) = this.putComponent(ref, ProjectileComponent.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeProjectile(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, ProjectileComponent.getComponentType())
fun ArchetypeChunk<EntityStore>.projectile(index: Int): ProjectileComponent = this.getComponent(index, ProjectileComponent.getComponentType())!!
fun ArchetypeChunk<EntityStore>.projectileOrNull(index: Int): ProjectileComponent? = this.getComponent(index, ProjectileComponent.getComponentType())
val Ref<EntityStore>.projectileOrNull: ProjectileComponent?
get() = this.store.getComponent(this, ProjectileComponent.getComponentType())
val Ref<EntityStore>.projectile: ProjectileComponent
    get() = this.store.getComponent(this, ProjectileComponent.getComponentType())!!
val Holder<EntityStore>.projectileOrNull: ProjectileComponent?
get() = this.getComponent(ProjectileComponent.getComponentType())
var Holder<EntityStore>.projectile: ProjectileComponent
get() = this.getComponent(ProjectileComponent.getComponentType())!!
set(value) = this.putComponent(ProjectileComponent.getComponentType(), value)
fun Holder<EntityStore>.ensureProjectile(): ProjectileComponent = this.ensureAndGetComponent(ProjectileComponent.getComponentType())
fun Holder<EntityStore>.addProjectile(component: ProjectileComponent) = this.addComponent(ProjectileComponent.getComponentType(), component)
fun Holder<EntityStore>.removeProjectile() = this.tryRemoveComponent(ProjectileComponent.getComponentType())

// Projectile
fun ComponentAccessor<EntityStore>.projectileMarker(ref: Ref<EntityStore>): Boolean = this.getComponent(ref, Projectile.getComponentType()) != null
fun ComponentAccessor<EntityStore>.setProjectileMarker(ref: Ref<EntityStore>, value: Boolean) {
    if (value) this.ensureAndGetComponent(ref, Projectile.getComponentType())
    else this.tryRemoveComponent(ref, Projectile.getComponentType())
}
fun ArchetypeChunk<EntityStore>.projectileMarker(index: Int): Boolean = this.getComponent(index, Projectile.getComponentType()) != null
val Ref<EntityStore>.isProjectileMarker: Boolean
    get() = this.store.getComponent(this, Projectile.getComponentType()) != null
var Holder<EntityStore>.isProjectileMarker: Boolean
get() = this.getComponent(Projectile.getComponentType()) != null
set(value) {
    if (value) this.ensureComponent(Projectile.getComponentType())
    else this.tryRemoveComponent(Projectile.getComponentType())
}

// StandardPhysicsProvider
fun ComponentAccessor<EntityStore>.standardPhysics(ref: Ref<EntityStore>): StandardPhysicsProvider = this.getComponent(ref, StandardPhysicsProvider.getComponentType())!!
fun ComponentAccessor<EntityStore>.standardPhysicsOrNull(ref: Ref<EntityStore>): StandardPhysicsProvider? = this.getComponent(ref, StandardPhysicsProvider.getComponentType())
fun ComponentAccessor<EntityStore>.ensureStandardPhysics(ref: Ref<EntityStore>): StandardPhysicsProvider = this.ensureAndGetComponent(ref, StandardPhysicsProvider.getComponentType())
fun ComponentAccessor<EntityStore>.addStandardPhysics(ref: Ref<EntityStore>, component: StandardPhysicsProvider) = this.addComponent(ref, StandardPhysicsProvider.getComponentType(), component)
fun ComponentAccessor<EntityStore>.putStandardPhysics(ref: Ref<EntityStore>, component: StandardPhysicsProvider) = this.putComponent(ref, StandardPhysicsProvider.getComponentType(), component)
fun ComponentAccessor<EntityStore>.removeStandardPhysics(ref: Ref<EntityStore>) = this.tryRemoveComponent(ref, StandardPhysicsProvider.getComponentType())
fun ArchetypeChunk<EntityStore>.standardPhysics(index: Int): StandardPhysicsProvider = this.getComponent(index, StandardPhysicsProvider.getComponentType())!!
fun ArchetypeChunk<EntityStore>.standardPhysicsOrNull(index: Int): StandardPhysicsProvider? = this.getComponent(index, StandardPhysicsProvider.getComponentType())
val Ref<EntityStore>.standardPhysicsOrNull: StandardPhysicsProvider?
get() = this.store.getComponent(this, StandardPhysicsProvider.getComponentType())
val Ref<EntityStore>.standardPhysics: StandardPhysicsProvider
    get() = this.store.getComponent(this, StandardPhysicsProvider.getComponentType())!!
val Holder<EntityStore>.standardPhysicsOrNull: StandardPhysicsProvider?
get() = this.getComponent(StandardPhysicsProvider.getComponentType())
var Holder<EntityStore>.standardPhysics: StandardPhysicsProvider
get() = this.getComponent(StandardPhysicsProvider.getComponentType())!!
set(value) = this.putComponent(StandardPhysicsProvider.getComponentType(), value)
fun Holder<EntityStore>.ensureStandardPhysics(): StandardPhysicsProvider = this.ensureAndGetComponent(StandardPhysicsProvider.getComponentType())
fun Holder<EntityStore>.addStandardPhysics(component: StandardPhysicsProvider) = this.addComponent(StandardPhysicsProvider.getComponentType(), component)
fun Holder<EntityStore>.removeStandardPhysics() = this.tryRemoveComponent(StandardPhysicsProvider.getComponentType())
