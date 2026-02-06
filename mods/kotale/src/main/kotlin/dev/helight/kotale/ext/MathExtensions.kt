package dev.helight.kotale.ext

import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.math.vector.Vector3i

fun Vector3f.toProtocol() = com.hypixel.hytale.protocol.Vector3f(this.x, this.y, this.z)
fun Vector3d.toProtocol() = com.hypixel.hytale.protocol.Vector3d(this.x, this.y, this.z)
fun Vector3i.toProtocol() = com.hypixel.hytale.protocol.Vector3i(this.x, this.y, this.z)

fun com.hypixel.hytale.protocol.Vector3d.toMath(): Vector3d = Vector3d(this.x, this.y, this.z)
fun com.hypixel.hytale.protocol.Vector3f.toMath(): Vector3f = Vector3f(this.x, this.y, this.z)
fun com.hypixel.hytale.protocol.Vector3i.toMath(): Vector3i = Vector3i(this.x, this.y, this.z)