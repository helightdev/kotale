package dev.helight.kotale.raycast

import com.hypixel.hytale.protocol.BlockMaterial
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType
import it.unimi.dsi.fastutil.ints.IntOpenHashSet
import it.unimi.dsi.fastutil.ints.IntSet
import kotlin.collections.iterator

class BlockSetRaycaster(
    val blockIds: IntSet,
    val invert: Boolean = false
) : BlockRaycaster() {

    override fun checkCollision(blockId: Int): Boolean {
        return blockIds.contains(blockId) != invert
    }

    companion object {
        fun findTraversableBlocks(): IntSet {
            val set = IntOpenHashSet()
            set.add(0) // Air
            for ((key, type) in BlockType.getAssetMap().assetMap) {
                if (type.material == BlockMaterial.Solid) continue
                val index = BlockType.getAssetMap().getIndex(key)
                set.add(index)
            }
            return set
        }
    }
}