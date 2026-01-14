package dev.helight.kotale

import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import kotlinx.coroutines.delay

class ExampleCommand(
    val plugin: KotlinPlugin
) : AbstractPlayerCommand("kotale-example", "kotale-example") {

    override fun execute(
        p0: CommandContext,
        p1: Store<EntityStore?>,
        p2: Ref<EntityStore?>,
        p3: PlayerRef,
        p4: World
    ) {
        val position = p3.transform.position.toVector3i()
        p4.launch(plugin) {
            delay(1000) // We sleep here to check if coroutines even in foreign threads work
            p4.setBlock(position.x, position.y, position.z, "Rock_Aqua_Cobble")
        }
    }
}