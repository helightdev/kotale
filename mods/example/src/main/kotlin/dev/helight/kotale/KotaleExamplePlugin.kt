package dev.helight.kotale

import com.hypixel.hytale.math.matrix.Matrix4d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.protocol.DebugShape
import com.hypixel.hytale.server.core.asset.util.ColorParseUtil
import com.hypixel.hytale.server.core.modules.debug.DebugUtils
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction
import com.hypixel.hytale.server.core.plugin.JavaPluginInit
import com.hypixel.hytale.server.core.util.TargetUtil
import dev.helight.kotale.dsl.buildCommandCollection
import dev.helight.kotale.ext.boundingBoxOrNull
import dev.helight.kotale.ext.snapshotBufferOrNull
import dev.helight.kotale.message.sendMessage
import dev.helight.kotale.raycast.BlockSetRaycaster
import dev.helight.kotale.raycast.Raycasts

class KotaleExamplePlugin(init: JavaPluginInit) : KotlinPlugin(init) {

    override fun setup() {
        super.setup()
        this.commandRegistry.registerCommand(ExampleCommand(this))

        this.getCodecRegistry(Interaction.CODEC)
            .register("TracerInteraction", TracerInteraction::class.java, TracerInteraction.CODEC)
    }

    override fun start() {
        traversableRaycaster = BlockSetRaycaster(BlockSetRaycaster.findTraversableBlocks(), true)
        commandRegistry.registerCommand(kotaleCommand)
    }

    companion object {
        lateinit var traversableRaycaster: BlockSetRaycaster
    }
}

val kotaleCommand = buildCommandCollection(
    name = "kotale",
    description = "Commands for the kotale example mod"
) {
    command.requirePermission("kotale.debug")

    collection("raycast") {
        playerCommand("bounds") {
            handle {
                val buffer = playerRef.reference?.snapshotBufferOrNull ?: error("No snapshot buffer available")
                context.sender()
                    .sendMessage("Snapshot buffer bounds: oldest=${buffer.oldestTickIndex}, cur=${buffer.currentTickIndex}")
            }
        }

        playerCommand("block") {
            handle {
                val look = TargetUtil.getLook(ref, store)
                val result = Raycasts.raycastBlock(world, look.position, look.direction, 10.0)
                val target = result.lastPosition
                val rayColor = when (result.hitPosition) {
                    null -> ColorParseUtil.hexStringToRGBInt("#FF0000")
                    else -> ColorParseUtil.hexStringToRGBInt("#00FF00")
                }
                Raycasts.debugLine(playerRef, look.position, target, rayColor, 2000)
            }
        }

        playerCommand("entity") {
            handle {
                val look = TargetUtil.getLook(ref, store)
                val (endPos, hitPos, entity) = Raycasts.raycastEntity(
                    world = world,
                    origin = look.position,
                    direction = look.direction,
                    maxDistance = 255.0,
                    entityPredicate = { it != playerRef.reference },
                    initialRaycaster = KotaleExamplePlugin.traversableRaycaster
                )
                println("Raycast hit entity: $entity at position $hitPos")
                val rayColor = when (hitPos) {
                    null -> ColorParseUtil.hexStringToRGBInt("#FF0000")
                    else -> ColorParseUtil.hexStringToRGBInt("#00FF00")
                }

                if (entity != null) {
                    val buffer = entity.snapshotBufferOrNull
                    val bb = entity.boundingBoxOrNull
                    if (buffer != null && bb != null) {
                        for (i in buffer.oldestTickIndex until buffer.currentTickIndex) {
                            val snapshot = buffer.getSnapshotClamped(i)
                            val matrix = Matrix4d().identity()
                            var bodyRotation = snapshot.bodyRotation.toVector3d()
                            matrix.rotateEuler(
                                bodyRotation.x,
                                bodyRotation.y,
                                bodyRotation.z,
                                Matrix4d()
                            )
                            matrix.scale(bb.boundingBox.width(), bb.boundingBox.height(), bb.boundingBox.depth())
                            matrix.translate(
                                snapshot.position.clone()
                                    .add(0.0, bb.boundingBox.height(), 0.0)
                            )

                            DebugUtils.add(
                                world, DebugShape.Cube, matrix,
                                Vector3f(0.5f, 1.8f, 0.5f), 1.0f, false
                            )
                        }
                    } else {
                        println("Entity has no snapshot buffer or bounding box")
                    }
                }

                Raycasts.debugLine(playerRef, look.position, endPos, rayColor, 2000)
            }
        }

    }
}