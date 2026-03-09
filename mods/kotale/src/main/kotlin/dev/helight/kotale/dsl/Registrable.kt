package dev.helight.kotale.dsl

import com.hypixel.hytale.assetstore.JsonAsset
import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec
import com.hypixel.hytale.codec.builder.BuilderCodec
import com.hypixel.hytale.component.Component
import com.hypixel.hytale.component.ComponentType
import com.hypixel.hytale.component.system.ISystem
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import dev.helight.kotale.KotlinPlugin
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

interface Registrable {
    fun registerFor(plugin: KotlinPlugin)
}

fun KotlinPlugin.install(vararg registrable: Registrable) {
    registrable.forEach { it.registerFor(this) }
}

interface ChunkSystemRegistrable : Registrable, ISystem<ChunkStore> {

    override fun registerFor(plugin: KotlinPlugin) {
        plugin.chunkStoreRegistry.registerSystem(this)
    }
}

interface EntitySystemRegistrable : Registrable, ISystem<EntityStore> {

    override fun registerFor(plugin: KotlinPlugin) {
        plugin.entityStoreRegistry.registerSystem(this)
    }
}

abstract class ChunkComponentCompanion<T>(
    val clazz: KClass<T>,
    val supplier: () -> T = { clazz.createInstance() },
    val codecBuilder: (BuilderCodec.Builder<T>.() -> BuilderCodec.Builder<T>)? = null,
    val id: String = clazz.simpleName!!
) : Registrable where T : Component<ChunkStore> {

    val codec: BuilderCodec<T>? = when (codecBuilder == null) {
        true -> null
        false -> BuilderCodec.builder<T>(clazz.java, supplier).let { codecBuilder.invoke(it) }.build()
    }

    lateinit var componentType: ComponentType<ChunkStore, T>

    override fun registerFor(plugin: KotlinPlugin) {
        val registry = plugin.chunkStoreRegistry
        componentType = if (codec == null) {
            registry.registerComponent(clazz.java, supplier)
        } else {
            registry.registerComponent(clazz.java, id, codec)
        }
    }
}

abstract class EntityComponentCompanion<T>(
    val clazz: KClass<T>,
    val supplier: () -> T = { clazz.createInstance() },
    val codecBuilder: (BuilderCodec.Builder<T>.() -> BuilderCodec.Builder<T>)? = null,
    val id: String = clazz.simpleName!!
) : Registrable where T : Component<EntityStore> {

    val codec: BuilderCodec<T>? = when (codecBuilder == null) {
        true -> null
        false -> BuilderCodec.builder<T>(clazz.java, supplier).let { codecBuilder.invoke(it) }.build()
    }

    lateinit var componentType: ComponentType<EntityStore, T>

    override fun registerFor(plugin: KotlinPlugin) {
        val registry = plugin.entityStoreRegistry
        componentType = if (codec == null) {
            registry.registerComponent(clazz.java, supplier)
        } else {
            registry.registerComponent(clazz.java, id, codec)
        }
    }
}

abstract class CodecRegistrableCompanion<T, REGISTRY_KEY, REGISTRY_TYPE>(
    val clazz: KClass<T>,
    val supplier: () -> T,
    val parent: AssetCodecMapCodec<REGISTRY_KEY, REGISTRY_TYPE>,
    val codecBuilder: () -> BuilderCodec.Builder<T>,
    val id: String = clazz.simpleName!!
) : Registrable where T : REGISTRY_TYPE, REGISTRY_TYPE : JsonAsset<REGISTRY_KEY> {

    val codec: BuilderCodec<out T> = codecBuilder().build()

    override fun registerFor(plugin: KotlinPlugin) {
        val registry = plugin.getCodecRegistry(parent)
        registry.register(id, clazz.java, codec)
    }
}