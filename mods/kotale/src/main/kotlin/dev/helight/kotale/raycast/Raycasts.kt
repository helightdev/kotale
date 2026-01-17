package dev.helight.kotale.raycast

import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.math.vector.Vector2d
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolLaserPointer
import com.hypixel.hytale.server.core.asset.util.ColorParseUtil
import com.hypixel.hytale.server.core.modules.collision.CollisionMath
import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.hypixel.hytale.server.core.util.TargetUtil
import dev.helight.kotale.ext.transform
import kotlin.math.sqrt


object Raycasts {

    fun raycastBlock(
        world: World,
        origin: Vector3d,
        direction: Vector3d,
        maxDistance: Double = 255.0,
    ): RaycastResult<Int> {
        return BlockNonAirRaycaster.raycast(world, origin, direction, maxDistance)
    }

    fun raycastEntity(
        world: World,
        origin: Vector3d,
        direction: Vector3d,
        maxDistance: Double = 255.0,
        entityPredicate: (Ref<EntityStore>) -> Boolean = { true },
        initialRaycaster: Raycaster<*> = BlockNonAirRaycaster,
        stepSize: Double = 5.0,
        checkSphereSize: Double = 10.0
    ): RaycastResult<Ref<EntityStore>> {
        // Note: This is implemented by the assumption that it's more efficient to do a block raycast first
        //       and then do entity checks along that path, rather than checking all entities in a maxDistance sphere.
        val (lastPos) = initialRaycaster.raycast(world, origin, direction, maxDistance)
        return lineCheckEntities(world, origin, lastPos, entityPredicate, stepSize, checkSphereSize)
    }

    fun lineCheckEntities(
        world: World,
        start: Vector3d,
        end: Vector3d,
        entityPredicate: (Ref<EntityStore>) -> Boolean = { true },
        stepSize: Double = 5.0,
        checkSphereSize: Double = 10.0
    ): RaycastResult<Ref<EntityStore>> {
        val direction = end.clone().subtract(start)
        val lengthSqr = direction.squaredLength()
        val length = sqrt(lengthSqr)
        direction.scale(1.0 / length)
        val entityStore = world.entityStore.store

        // Step through the ray in increments
        var currentDistance = 0.0
        while (currentDistance <= length) {
            val currentPosition = start.clone().add(direction.clone().scale(currentDistance))
            val nearbyEntities = TargetUtil.getAllEntitiesInSphere(
                currentPosition, checkSphereSize, entityStore
            )
            var closestEntity: Ref<EntityStore>? = null
            var closestHitDistance: Double? = null
            var hitPos = start
            nearbyEntities.forEach {
                val transform = entityStore.transform(it)
                val boundingBox = entityStore.getComponent(it, BoundingBox.getComponentType()) ?: return@forEach
                val entityPos = transform.position
                val minMax = Vector2d()
                if (!CollisionMath.intersectRayAABB(
                        start,
                        direction,
                        entityPos.x,
                        entityPos.y,
                        entityPos.z,
                        boundingBox.boundingBox,
                        minMax
                    )
                ) {
                    return@forEach
                }

                val entityHitPos = start.clone().add(direction.clone().scale((minMax.x + minMax.y) / 2.0))
                if (!entityPredicate(it)) return@forEach
                if (minMax.x > length) return@forEach
                if (closestHitDistance == null) {
                    closestEntity = it
                    closestHitDistance = minMax.x
                    hitPos = entityHitPos
                } else if (minMax.x < closestHitDistance) {
                    closestEntity = it
                    closestHitDistance = minMax.x
                    hitPos = entityHitPos
                }
            }
            if (closestEntity != null) {
                return RaycastResult(
                    lastPosition = hitPos,
                    hitPosition = hitPos,
                    hit = closestEntity
                )
            }
            currentDistance += stepSize
        }
        return RaycastResult(
            lastPosition = end,
            hitPosition = null,
            hit = null
        )
    }

    fun debugDirection(
        playerRef: PlayerRef,
        start: Vector3d,
        direction: Vector3d,
        color: Int = redColor,
        durationMs: Int = 2000
    ) {
        val end = start.clone().add(direction)
        debugLine(playerRef, start, end, color, durationMs)
    }

    fun debugLine(
        playerRef: PlayerRef,
        start: Vector3d,
        end: Vector3d,
        color: Int = redColor,
        durationMs: Int = 2000
    ) {
        val ref = playerRef.reference!!
        val netId = ref.store.getComponent(ref, NetworkId.getComponentType())!!
        val packet = BuilderToolLaserPointer()
        packet.playerNetworkId = netId.id
        packet.startX = start.x.toFloat()
        packet.startY = start.y.toFloat()
        packet.startZ = start.z.toFloat()
        packet.endX = end.x.toFloat()
        packet.endY = end.y.toFloat()
        packet.endZ = end.z.toFloat()
        packet.color = color
        packet.durationMs = durationMs
        playerRef.packetHandler.writeNoCache(packet)
    }


    private val redColor = ColorParseUtil.hexStringToRGBInt("#FF0000")
}