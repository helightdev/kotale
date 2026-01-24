package dev.helight.kotale

import com.hypixel.hytale.protocol.InteractionType
import com.hypixel.hytale.protocol.WaitForDataFrom
import com.hypixel.hytale.server.core.asset.util.ColorParseUtil
import com.hypixel.hytale.server.core.entity.InteractionContext
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction
import com.hypixel.hytale.server.core.util.TargetUtil
import dev.helight.kotale.ext.*
import dev.helight.kotale.raycast.BlockBoxRaycaster
import dev.helight.kotale.raycast.Raycasts

class TracerInteraction : SimpleInstantInteraction() {

    override fun getWaitForDataFrom(): WaitForDataFrom {
        return WaitForDataFrom.None
    }

    override fun firstRun(
        p0: InteractionType, p1: InteractionContext, p2: CooldownHandler
    ) {
        val store = p1.entity.store
        val world = store.externalData.world
        val ref = p1.entity
        val look = TargetUtil.getLook(ref, store)

        val pongTickTime = p1.entity.playerRef.estimateWorldTickLatency()
        val rewind = if (p0 == InteractionType.Primary) pongTickTime else 0

        val (endPos, hitPos, entity) = Raycasts.raycastEntity(
            world = world,
            origin = look.position,
            direction = look.direction,
            maxDistance = 255.0,
            entityPredicate = { it != ref },
            initialRaycaster = BlockBoxRaycaster,
            tickRewind = rewind
        )
        println("Raycast r=$rewind hit entity: $entity at position $hitPos")
        val rayColor = when (hitPos) {
            null -> ColorParseUtil.hexStringToRGBInt("#FF0000")
            else -> ColorParseUtil.hexStringToRGBInt("#00FF00")
        }

        if (entity != null) {
            val buffer = entity.snapshotBufferOrNull
            val bb = entity.boundingBoxOrNull
            if (buffer != null && bb != null) {
                val snapshot = buffer.getSnapshotClamped(buffer.currentTickIndex - rewind)
                Raycasts.debugBoundingBox(
                    ref.playerRef, bb.boundingBox, snapshot.position, snapshot.bodyRotation.toVector3d()
                )
            } else {
                println("Entity has no snapshot buffer or bounding box")
            }
        }
        val distanceTo = look.position.distanceTo(endPos)
        println("D: $distanceTo")
        Raycasts.debugLine(ref.playerRef, look.position, endPos, rayColor, 2000)
    }

    companion object {
        val CODEC = builderCodec(::TracerInteraction).build()
    }
}