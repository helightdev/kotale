package dev.helight.kotale.dsl

import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.server.core.command.system.AbstractCommand
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.arguments.system.Argument
import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg
import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType
import com.hypixel.hytale.server.core.command.system.arguments.types.Coord
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncPlayerCommand
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import dev.helight.kotale.KotaleInitializer
import dev.helight.kotale.KotlinPlugin
import dev.helight.kotale.asVoidCompletableFuture
import dev.helight.kotale.launch
import java.util.UUID
import java.util.concurrent.CompletableFuture
import kotlin.reflect.KClass

@DslMarker
annotation class CommandDsl

@CommandDsl
class CommandListBuilder(
    val command: AbstractCommand
) {

    @CommandDsl
    fun collection(
        name: String,
        description: String = "",
        block: CommandListBuilder.() -> Unit
    ) {
        val command = GeneralCollectionCommand(name, description)
        val builder = CommandListBuilder(command)
        block.invoke(builder)
        this.command.addSubCommand(command)
    }

    @CommandDsl
    fun command(
        name: String,
        description: String = "",
        confirmation: Boolean = false,
        plugin: KotlinPlugin? = KotaleInitializer.plugin,
        block: CommandBuilder<CommandScope>.() -> Unit
    ) {
        requireNotNull(plugin) { "No KotlinPlugin instance found for command registration!" }
        val command = GeneralTerminalCommand(plugin, name, description, confirmation)
        val builder = CommandBuilder<CommandScope>(command)
        block.invoke(builder)
        command.block = builder.handlerFun
        this.command.addSubCommand(command)
    }

    @CommandDsl
    fun playerCommand(
        name: String,
        description: String = "",
        confirmation: Boolean = false,
        plugin: KotlinPlugin? = KotaleInitializer.plugin,
        block: CommandBuilder<PlayerCommandScope>.() -> Unit
    ) {
        requireNotNull(plugin) { "No KotlinPlugin instance found for command registration!" }
        val command = PlayerTerminalCommand(plugin, name, description, confirmation)
        val builder = CommandBuilder<PlayerCommandScope>(command)
        block.invoke(builder)
        command.block = builder.handlerFun
        this.command.addSubCommand(command)
    }
}

@CommandDsl
fun buildCommandCollection(
    name: String,
    description: String = "",
    block: CommandListBuilder.() -> Unit
): AbstractCommand {
    val command = GeneralCollectionCommand(name, description)
    val builder = CommandListBuilder(command)
    block.invoke(builder)
    return command
}

@CommandDsl
fun buildCommand(
    name: String,
    description: String = "",
    confirmation: Boolean = false,
    plugin: KotlinPlugin? = KotaleInitializer.plugin,
    block: CommandBuilder<CommandScope>.() -> Unit
): AbstractCommand {
    requireNotNull(plugin) { "No KotlinPlugin instance found for command registration!" }
    val command = GeneralTerminalCommand(plugin, name, description, confirmation)
    val builder = CommandBuilder<CommandScope>(command)
    block.invoke(builder)
    command.block = builder.handlerFun
    return command
}

@CommandDsl
fun buildPlayerCommand(
    name: String,
    description: String = "",
    confirmation: Boolean = false,
    plugin: KotlinPlugin? = KotaleInitializer.plugin,
    block: CommandBuilder<PlayerCommandScope>.() -> Unit
): AbstractCommand {
    requireNotNull(plugin) { "No KotlinPlugin instance found for command registration!" }
    val command = PlayerTerminalCommand(plugin, name, description, confirmation)
    val builder = CommandBuilder<PlayerCommandScope>(command)
    block.invoke(builder)
    command.block = builder.handlerFun
    return command
}


@CommandDsl
class CommandBuilder<T : CommandScope>(
    val command: AbstractCommand
) {
    internal var handlerFun: suspend T.() -> Unit = {}

    inline fun <reified T> required(
        name: String,
        description: String = "",
        type: ArgumentType<T>? = null
    ): RequiredArg<T> {
        val argType: ArgumentType<T>? = type ?: typeToArgType(T::class.java)
        requireNotNull(argType) { "No argument type found for class ${T::class.java}" }
        return command.withRequiredArg(name, description, argType)
   }

    inline fun <reified T> optional(
        name: String,
        description: String = "",
        type: ArgumentType<T>? = null
    ): OptionalArg<T> {
        val argType: ArgumentType<T>? = type ?: typeToArgType(T::class.java)
        requireNotNull(argType) { "No argument type found for class ${T::class.java}" }
        return command.withOptionalArg(name, description, argType)
    }

    inline fun <reified T> defaulted(
        name: String,
        defaultValue: T,
        description: String = "",
        defaultValueDescription: String = "",
        type: ArgumentType<T>? = null
    ): DefaultArg<T> {
        val argType: ArgumentType<T>? = type ?: typeToArgType(T::class.java)
        requireNotNull(argType) { "No argument type found for class ${T::class.java}" }
        return command.withDefaultArg(name, description, argType, defaultValue, defaultValueDescription)
    }

    fun flag(name: String, description: String = ""): FlagArg {
        return command.withFlagArg(name, description)
    }

    @PublishedApi
    internal fun <T> typeToArgType(clazz: Class<T>): ArgumentType<T>? {
        return when (clazz) {
            Boolean::class.java -> ArgTypes.BOOLEAN
            Int::class.java -> ArgTypes.INTEGER
            String::class.java -> ArgTypes.STRING
            Float::class.java -> ArgTypes.FLOAT
            Double::class.java -> ArgTypes.DOUBLE
            UUID::class.java -> ArgTypes.UUID
            PlayerRef::class.java -> ArgTypes.PLAYER_REF
            World::class.java -> ArgTypes.WORLD
            else -> null
        } as? ArgumentType<T>?
    }

    @CommandDsl
    fun handle(block: suspend T.() -> Unit) {
        handlerFun = block
    }
}

@CommandDsl
open class CommandScope(
    val context: CommandContext
) {

    fun <T> get(argument: Argument<*, T>): T {
        val resolved = context.get(argument)
        return requireNotNull(resolved)
    }

    fun <T> getNullable(argument: Argument<*, T>): T? {
        return context.get(argument)
    }

    operator fun <T> RequiredArg<T>.invoke(): T = context.get(this)!!
    operator fun <T> DefaultArg<T>.invoke(): T = context.get(this)!!
    operator fun <T> OptionalArg<T>.invoke(): T? = context.get(this)
    operator fun FlagArg.invoke(): Boolean = context.get(this) ?: false

}

@CommandDsl
class PlayerCommandScope(
    context: CommandContext,
    val playerRef: PlayerRef,
    val world: World,
    val store: Store<EntityStore>,
    val ref: Ref<EntityStore>
) : CommandScope(context)

class GeneralTerminalCommand(
    val plugin: KotlinPlugin,
    name: String,
    description: String,
    confirmation: Boolean
) : AbstractAsyncCommand(name, description, confirmation) {

    var block: suspend (CommandScope) -> Unit = {}

    override fun executeAsync(p0: CommandContext): CompletableFuture<Void> {
        return plugin.launch {
            block.invoke(CommandScope(p0))
        }.asVoidCompletableFuture()
    }
}

class PlayerTerminalCommand(
    val plugin: KotlinPlugin,
    name: String,
    description: String,
    confirmation: Boolean
) : AbstractAsyncPlayerCommand(name, description, confirmation) {

    var block: suspend (PlayerCommandScope) -> Unit = {}

    override fun executeAsync(
        p0: CommandContext,
        p1: Store<EntityStore>,
        p2: Ref<EntityStore>,
        p3: PlayerRef,
        p4: World
    ): CompletableFuture<Void> {
        return p4.launch(plugin) {
            block.invoke(PlayerCommandScope(p0, p3, p4, p1, p2))
        }.asVoidCompletableFuture()
    }

}

class GeneralCollectionCommand(
    name: String,
    description: String
) : AbstractCommandCollection(name, description) {}