@file:DependsOn("com.squareup:kotlinpoet-jvm:2.2.0")

import java.io.File

val targetFile = File("src/main/kotlin/dev/helight/kotale/ext/Generated.kt")

enum class StoreType(
    val cname: String, val accessors: List<Accessor>
) {
    Chunk(
        "ChunkStore", listOf(
            Accessor.Collection(
                "ComponentAccessor<ChunkStore>", "Ref<ChunkStore>", "ref"
            )
        )
    ),
    Entity(
        "EntityStore", listOf(
            Accessor.Collection("ComponentAccessor<EntityStore>", "Ref<EntityStore>", "ref", true),
            Accessor.Collection("ArchetypeChunk<EntityStore>", "Int", "index"),
            Accessor.Direct("Ref<EntityStore>", ".store", "this, "),
            Accessor.Direct("Holder<EntityStore>", "", "", true)
        )
    );

    val ref: String get() = "Ref<$cname>"
    val accessor: String get() = "ComponentAccessor<$cname>"
}

sealed class Accessor() {
    abstract val mutable: Boolean

    class Collection(
        val base: String, val keyType: String, val keyName: String, override val mutable: Boolean = false
    ) : Accessor()

    class Direct(
        val base: String, val path: String, val args: String, override val mutable: Boolean = false
    ) : Accessor()

}

enum class ExtType {
    Default, Flag
}

var importList = mutableSetOf<Pair<String, String>>()
importList.add("com.hypixel.hytale.component" to "*")
importList.add("com.hypixel.hytale.server.core.universe.world.storage" to "EntityStore")
importList.add("com.hypixel.hytale.server.core.universe.world.storage" to "ChunkStore")

var functions = mutableListOf<String>()

File("components.csv").readLines().drop(1).forEach { line ->
    if (line.isBlank()) return@forEach

    val parts = line.split(",")
    val qualified = parts[0]
    val nameParts = qualified.split(".")
    val name = nameParts.last()
    importList.add(nameParts.take(nameParts.size - 1).joinToString(".") to name)

    val storeType = StoreType.valueOf(parts[1])
    val extType = ExtType.valueOf(parts[2])

    val simpleName = parts[3].takeIf { it.isNotBlank() } ?: name.removeSuffix("Component")
    val lowerName = simpleName.replaceFirstChar { it.lowercase() }
    functions.add("\n// $name")

    storeType.accessors.forEach {
        if (it is Accessor.Direct) {
            if (extType == ExtType.Default) """
        val ${it.base}.${lowerName}OrNull: $name?
        get() = this${it.path}.getComponent(${it.args}$name.getComponentType())
    """.trimIndent().let { functions.add(it) }

            if (it.mutable) {
                if (extType == ExtType.Default) """
        var ${it.base}.$lowerName: $name
        get() = this${it.path}.getComponent(${it.args}$name.getComponentType())!!
        set(value) = this${it.path}.putComponent(${it.args}$name.getComponentType(), value)
    """.trimIndent().let { functions.add(it) }

                if (extType == ExtType.Flag) """
        var ${it.base}.is$simpleName: Boolean
        get() = this${it.path}.getComponent(${it.args}$name.getComponentType()) != null
        set(value) {
            if (value) this${it.path}.ensureComponent(${it.args}$name.getComponentType())
            else this${it.path}.tryRemoveComponent(${it.args}$name.getComponentType())
        }
    """.trimIndent().let { functions.add(it) }
                else {

                    """ fun ${it.base}.ensure${simpleName}(component: $name): $name = this${it.path}.ensureAndGetComponent(${it.args}$name.getComponentType())
  """.trimIndent().let { functions.add(it) }

                    """
        fun ${it.base}.add${simpleName}(component: $name) = this${it.path}.addComponent(${it.args}$name.getComponentType(), component)
    """.trimIndent().let { functions.add(it) }

                    """
        fun ${it.base}.remove${simpleName}() = this${it.path}.removeComponent(${it.args}$name.getComponentType())
    """.trimIndent().let { functions.add(it) }
                }


            } else {
                if (extType == ExtType.Default) """
        val ${it.base}.$lowerName: $name
            get() = this${it.path}.getComponent(${it.args}$name.getComponentType())!!
    """.trimIndent().let { functions.add(it) }
                else """
        val ${it.base}.is$simpleName: Boolean
            get() = this${it.path}.getComponent(${it.args}$name.getComponentType()) != null
    """.trimIndent().let { functions.add(it) }


            }
        } else if (it is Accessor.Collection) {

            if (extType == ExtType.Default) {
                """
        fun ${it.base}.$lowerName(${it.keyName}: ${it.keyType}): $name = this.getComponent(${it.keyName}, $name.getComponentType())!!
    """.trimIndent().let { functions.add(it) }

                """
        fun ${it.base}.${lowerName}OrNull(${it.keyName}: ${it.keyType}): $name? = this.getComponent(${it.keyName}, $name.getComponentType())
    """.trimIndent().let { functions.add(it) }
            } else {
                """
        fun ${it.base}.${lowerName}(${it.keyName}: ${it.keyType}): Boolean = this.getComponent(${it.keyName}, $name.getComponentType()) != null
    """.trimIndent().let { functions.add(it) }
            }



            if (it.mutable) {
                if (extType == ExtType.Default) {
                    """
        fun ${it.base}.ensure$simpleName(${it.keyName}: ${it.keyType}): $name = this.ensureAndGetComponent(${it.keyName}, $name.getComponentType())
    """.trimIndent().let { functions.add(it) }

                    """
        fun ${it.base}.add$simpleName(${it.keyName}: ${it.keyType}, component: $name) = this.addComponent(${it.keyName}, $name.getComponentType(), component)
    """.trimIndent().let { functions.add(it) }

                    """
        fun ${it.base}.put$simpleName(${it.keyName}: ${it.keyType}, component: $name) = this.putComponent(${it.keyName}, $name.getComponentType(), component)
    """.trimIndent().let { functions.add(it) }

                    """
        fun ${it.base}.remove$simpleName(${it.keyName}: ${it.keyType}) = this.removeComponent(${it.keyName}, $name.getComponentType())
    """.trimIndent().let { functions.add(it) }
                } else {
                    """
        fun ${it.base}.set$simpleName(${it.keyName}: ${it.keyType}, value: Boolean) {
            if (value) this.ensureAndGetComponent(${it.keyName}, $name.getComponentType())
            else this.tryRemoveComponent(${it.keyName}, $name.getComponentType())
        }
    """.trimIndent().let { functions.add(it) }
                }

            }
        }
    }
}

val builder = StringBuilder()
builder.appendLine("@file:Suppress(\"unused\")")
builder.appendLine()
builder.appendLine("package dev.helight.kotale.ext")
builder.appendLine()
importList.forEach {
    builder.appendLine("import ${it.first}.${it.second}")
}
builder.appendLine()

functions.forEach {
    builder.appendLine(it)
}
println(builder.toString())
targetFile.writeText(builder.toString())