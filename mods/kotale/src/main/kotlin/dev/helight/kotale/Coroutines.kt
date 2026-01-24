package dev.helight.kotale

import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext

val World.dispatcher: CoroutineDispatcher
    get() = WorldThreadDispatcher.get(this)

suspend fun <T> World.context(block: suspend CoroutineScope.() -> T): T = withContext(this.dispatcher) {
    block()
}

suspend fun <T> EntityStore.context(block: suspend CoroutineScope.() -> T): T = withContext(world.dispatcher) {
    block()
}

fun World.launch(plugin: KotlinPlugin? = KotaleInitializer.plugin, block: suspend CoroutineScope.() -> Unit): Job {
    requireNotNull(plugin) { "No plugin found in scope" }
    return CoroutineScope(plugin.supervisor + this.dispatcher).launch {
        block()
    }
}

fun launchGlobal(plugin: KotlinPlugin? = KotaleInitializer.plugin, block: suspend CoroutineScope.() -> Unit): Job {
    requireNotNull(plugin) { "No plugin found in scope" }
    return CoroutineScope(plugin.supervisor + Dispatchers.Default).launch {
        block()
    }
}


fun <T> World.launch(
    plugin: KotlinPlugin? = KotaleInitializer.plugin,
    block: suspend CoroutineScope.() -> T
): Deferred<T> {
    requireNotNull(plugin) { "No plugin found in scope" }
    return CoroutineScope(plugin.supervisor + this.dispatcher).async {
        block()
    }
}

fun Job.asVoidCompletableFuture(): CompletableFuture<Void> {
    val future = CompletableFuture<Void>()
    invokeOnCompletion { throwable ->
        if (throwable != null) {
            future.completeExceptionally(throwable)
        } else {
            future.complete(null)
        }
    }
    return future
}

fun Job.asNullVoidCompletableFuture(): CompletableFuture<Void?> {
    val future = CompletableFuture<Void?>()
    invokeOnCompletion { throwable ->
        if (throwable != null) {
            future.completeExceptionally(throwable)
        } else {
            future.complete(null)
        }
    }
    return future
}

class WorldThreadDispatcher(
    val world: World
) : CoroutineDispatcher() {

    override fun isDispatchNeeded(context: CoroutineContext): Boolean {
        return !world.isInThread
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        world.execute(block)
    }


    companion object {
        private val dispatchers = Collections.synchronizedMap(
            IdentityHashMap<World, CoroutineDispatcher>()
        )

        internal fun remove(world: World) {
            dispatchers.remove(world)
        }

        fun get(world: World): CoroutineDispatcher =
            dispatchers.getOrPut(world) { WorldThreadDispatcher(world) }
    }
}