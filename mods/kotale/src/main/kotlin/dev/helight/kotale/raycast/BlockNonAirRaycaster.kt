package dev.helight.kotale.raycast

object BlockNonAirRaycaster : BlockRaycaster() {
    override fun checkCollision(blockId: Int): Boolean {
        return blockId != 0
    }
}