package dev.helight.kotale

import com.hypixel.hytale.server.core.asset.util.ColorParseUtil
import com.hypixel.hytale.server.core.plugin.JavaPluginInit
import com.hypixel.hytale.server.core.util.TargetUtil
import dev.helight.kotale.dsl.buildCommandCollection
import dev.helight.kotale.raycast.BlockSetRaycaster
import dev.helight.kotale.raycast.Raycasts

class KotaleExamplePlugin(init: JavaPluginInit) : KotlinPlugin(init) {

    override fun setup() {
        super.setup()
        this.commandRegistry.registerCommand(ExampleCommand(this))
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
        playerCommand("block") {
            handle {
                val look = TargetUtil.getLook(ref, store)
                val result = Raycasts.raycastBlock(world, look.position, look.direction)
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
                Raycasts.debugLine(playerRef, look.position, endPos, rayColor, 2000)
            }
        }

    }
}