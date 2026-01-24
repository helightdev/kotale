package dev.helight.kotale.raycast

import com.hypixel.hytale.math.shape.Box
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3i
import com.hypixel.hytale.protocol.BlockMaterial
import com.hypixel.hytale.server.core.modules.collision.*
import com.hypixel.hytale.server.core.universe.world.World

object BlockBoxRaycaster : Raycaster<BlockData>() {
    private val collisionProvider = BlockCollisionProvider()
    private val emptyTracker = object : IBlockTracker {
        override fun getPosition(index: Int): Vector3i = Vector3i()
        override fun getCount(): Int = 0
        override fun track(x: Int, y: Int, z: Int): Boolean = false
        override fun trackNew(x: Int, y: Int, z: Int) {}
        override fun isTracked(x: Int, y: Int, z: Int): Boolean = false
        override fun untrack(x: Int, y: Int, z: Int) {}
    }

    override fun raycast(
        world: World,
        origin: Vector3d,
        direction: Vector3d,
        maxDistance: Double
    ): RaycastResult<BlockData> {
        val collider = Box(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        val movement = direction.clone().normalize().scale(maxDistance)

        var hitBlock: BlockData? = null
        var collisionStart = 1.0

        val consumer = object : IBlockCollisionConsumer {
            override fun onCollision(
                x: Int, y: Int, z: Int,
                motion: Vector3d,
                contact: BlockContactData,
                blockData: BlockData,
                box: Box
            ): IBlockCollisionConsumer.Result {
                if (blockData.blockType?.material != BlockMaterial.Solid) {
                    return IBlockCollisionConsumer.Result.CONTINUE
                }

                collisionStart = contact.collisionStart
                val newData = BlockData()
                newData.assign(blockData)
                hitBlock = newData
                return IBlockCollisionConsumer.Result.STOP
            }

            override fun probeCollisionDamage(
                x: Int,
                y: Int,
                z: Int,
                motion: Vector3d,
                contact: BlockContactData,
                blockData: BlockData
            ): IBlockCollisionConsumer.Result = IBlockCollisionConsumer.Result.CONTINUE

            override fun onCollisionDamage(
                x: Int,
                y: Int,
                z: Int,
                motion: Vector3d,
                contact: BlockContactData,
                blockData: BlockData
            ) {
            }

            override fun onCollisionSliceFinished(): IBlockCollisionConsumer.Result =
                IBlockCollisionConsumer.Result.CONTINUE

            override fun onCollisionFinished() {}
        }

        collisionProvider.setRequestedCollisionMaterials(6)
        collisionProvider.cast(world, collider, origin.clone(), movement.clone(), consumer, emptyTracker, 1.0)

        val lastPosition = origin.clone().addScaled(movement, collisionStart)
        return RaycastResult(lastPosition, lastPosition, hitBlock)
    }
}