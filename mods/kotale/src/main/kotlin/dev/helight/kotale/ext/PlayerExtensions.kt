package dev.helight.kotale.ext

import com.hypixel.hytale.math.vector.Transform
import com.hypixel.hytale.protocol.SoundCategory
import com.hypixel.hytale.protocol.packets.connection.PongType
import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.SoundUtil
import com.hypixel.hytale.server.core.universe.world.World
import kotlin.math.roundToInt

fun PlayerRef.heal() {
    val ref = reference ?: error("Player reference is null")
    ref.store.heal(ref)
}

fun PlayerRef.teleport(transform: Transform) {
    val ref = reference ?: error("Player reference is null")
    ref.store.teleport(ref, ref.store.externalData.world, transform)
}

fun PlayerRef.teleport(world: World, transform: Transform) {
    val ref = reference ?: error("PlayerRef ${this.uuid} has no reference!")
    ref.store.teleport(ref, world, transform)
}

fun PlayerRef.play2DSound(soundKey: String, volumeMultiplier: Float = 1f, pitchMultiplier: Float = 1f) {
    val soundIndex = SoundEvent.getAssetMap().getIndex(soundKey)
    SoundUtil.playSoundEvent2dToPlayer(this, soundIndex, SoundCategory.SFX, volumeMultiplier, pitchMultiplier)
}

fun PlayerRef.estimateWorldTickLatency(): Int {
    val world = reference?.store?.externalData?.world ?: return 0
    val pongTime = this.packetHandler.getPingInfo(PongType.Raw).pingMetricSet.lastValue
    return (pongTime.toDouble() / (world.tickStepNanos / 1000)).roundToInt()
}