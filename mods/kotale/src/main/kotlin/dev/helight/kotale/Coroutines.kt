package dev.helight.kotale

import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Collections
import java.util.WeakHashMap
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

fun World.launch(plugin: KotlinPlugin, block: suspend CoroutineScope.() -> Unit): Job =
    CoroutineScope(plugin.supervisor + this.dispatcher).launch {
        block()
    }

fun <T> World.launch(plugin: KotlinPlugin, block: suspend CoroutineScope.() -> T): Deferred<T> =
    CoroutineScope(plugin.supervisor + this.dispatcher).async {
        block()
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
            WeakHashMap<World, CoroutineDispatcher>()
        )

        fun get(world: World): CoroutineDispatcher =
            dispatchers.getOrPut(world) { WorldThreadDispatcher(world) }
    }
}