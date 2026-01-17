package dev.helight.kotale.raycast

import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.math.iterator.BlockIterator
import com.hypixel.hytale.math.util.ChunkUtil
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore

abstract class BlockRaycaster : Raycaster<Int>() {

    abstract fun checkCollision(blockId: Int): Boolean

    override fun raycast(
        world: World,
        origin: Vector3d,
        direction: Vector3d,
        maxDistance: Double
    ): RaycastResult<Int> {
        val buffer = TargetBufferLocation(world)
        buffer.updateChunk(origin.x.toInt(), origin.z.toInt())
        var lastBlockId: Int? = null
        val success = BlockIterator.iterate<TargetBufferLocation?>(
            origin.x,
            origin.y,
            origin.z,
            direction.x,
            direction.y,
            direction.z,
            maxDistance,
            { x: Int, y: Int, z: Int,
              px: Double, py: Double, pz: Double,
              qx: Double, qy: Double, qz: Double,
              iBuffer: TargetBufferLocation ->
                if (y >= 0 && y < 320) {
                    iBuffer.updateChunk(x, z)
                    if (iBuffer.currentBlockChunk == null) {
                        return@iterate false
                    } else {
                        iBuffer.x = x.toDouble() + px
                        iBuffer.y = y.toDouble() + py
                        iBuffer.z = z.toDouble() + pz
                        val blockSection = iBuffer.currentBlockChunk!!.getSectionAtBlockY(y)
                        iBuffer.currentBlockChunk
                        val blockId = blockSection.get(x, y, z)
                        lastBlockId = blockId
                        return@iterate !checkCollision(blockId)
                    }
                } else {
                    return@iterate false
                }
            },
            buffer
        )
        val bufferEndPos = Vector3d(buffer.x, buffer.y, buffer.z)
        return when (success) {
            true -> RaycastResult(bufferEndPos, null, null)
            false -> RaycastResult(
                bufferEndPos,
                bufferEndPos,
                lastBlockId
            )
        }

    }


    private class TargetBufferLocation(val world: World) {
        val chunkStoreAccessor: ComponentAccessor<ChunkStore> = world.chunkStore.store

        var x = 0.0
        var y = 0.0
        var z = 0.0
        var currentChunkX = 0
        var currentChunkZ = 0
        var currentChunkRef: Ref<ChunkStore>? = null
        var currentBlockChunk: BlockChunk? = null

        fun updateChunk(blockX: Int, blockZ: Int) {
            val chunkX = ChunkUtil.chunkCoordinate(blockX)
            val chunkZ = ChunkUtil.chunkCoordinate(blockZ)
            if (this.currentChunkRef == null || chunkX != this.currentChunkX || chunkZ != this.currentChunkZ) {
                this.currentChunkX = chunkX
                this.currentChunkZ = chunkZ
                val chunkIndex = ChunkUtil.indexChunk(chunkX, chunkZ)
                this.currentChunkRef = this.world.chunkStore.getChunkReference(chunkIndex)
                if (this.currentChunkRef != null && this.currentChunkRef!!.isValid) {
                    this.currentBlockChunk = this.chunkStoreAccessor.getComponent<BlockChunk?>(
                        this.currentChunkRef!!,
                        BlockChunk.getComponentType()
                    )
                } else {
                    this.currentBlockChunk = null
                }
            }
        }
    }
}