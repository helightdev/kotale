package dev.helight.kotale.helper

import com.hypixel.hytale.math.util.ChunkUtil
import com.hypixel.hytale.math.vector.Vector3i

object BlockHelper {

    fun inverseTransformChunkColumn(columnIndex: Int, chunkIndex: Long): Vector3i {
        val x = ChunkUtil.xFromBlockInColumn(columnIndex) + ChunkUtil.minBlock(ChunkUtil.xOfChunkIndex(chunkIndex))
        val y = ChunkUtil.yFromBlockInColumn(columnIndex)
        val z = ChunkUtil.zFromBlockInColumn(columnIndex) + ChunkUtil.minBlock(ChunkUtil.zOfChunkIndex(chunkIndex))
        return Vector3i(x, y, z)
    }

    fun transformChunkColumn(position: Vector3i): Pair<Int, Long> {
        val chunkIndex = ChunkUtil.indexChunk(position.x, position.z)
        val columnIndex = ChunkUtil.indexBlockInColumn(position.x, position.y, position.z)
        return Pair(columnIndex, chunkIndex)
    }

}