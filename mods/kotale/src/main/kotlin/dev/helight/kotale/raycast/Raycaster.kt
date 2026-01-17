package dev.helight.kotale.raycast

import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.server.core.universe.world.World

abstract class Raycaster<T> {

    abstract fun raycast(
        world: World,
        origin: Vector3d,
        direction: Vector3d,
        maxDistance: Double = 255.0
    ): RaycastResult<T>

}

data class RaycastResult<T>(
    val lastPosition: Vector3d,
    val hitPosition: Vector3d?,
    val hit: T?
)