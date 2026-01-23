package dev.helight.kotale.ext

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.codec.KeyedCodec
import com.hypixel.hytale.codec.builder.BuilderCodec
import com.hypixel.hytale.codec.builder.BuilderField
import com.hypixel.hytale.codec.codecs.array.ArrayCodec
import com.hypixel.hytale.codec.codecs.map.MapCodec
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.lang.invoke.MethodHandles
import java.util.function.Supplier
import kotlin.reflect.KMutableProperty
import kotlin.reflect.jvm.javaMethod


inline fun <reified SELF> builderCodec(supplier: Supplier<SELF>): BuilderCodec.Builder<SELF> {
    return BuilderCodec.builder(SELF::class.java, supplier)
}

fun <SELF, T, BUILDER : BuilderCodec.BuilderBase<SELF, BUILDER>> BUILDER.property(
    property: KMutableProperty<T>, codec: Codec<T>,
    name: String = property.name.replaceFirstChar(Char::uppercase),
    required: Boolean = false,
    block: BuilderField.FieldBuilder<SELF, T, BUILDER>.() -> Unit = {}
): BUILDER {
    val lookup = MethodHandles.lookup()
    val getter = lookup.unreflect(property.getter.javaMethod)
    val setter = lookup.unreflect(property.setter.javaMethod)

    @Suppress("UNCHECKED_CAST")
    val append = this.append(
        KeyedCodec(name, codec, required),
        { comp: SELF, value: T -> setter.invoke(comp, value) },
        { comp: SELF -> getter.invoke(comp) as T }
    )
    block.invoke(append)
    return append.add()
}

inline fun <SELF, reified T, BUILDER : BuilderCodec.BuilderBase<SELF, BUILDER>> BUILDER.opaqueSerializedProperty(
    property: KMutableProperty<T>,
    name: String = property.name.replaceFirstChar(Char::uppercase),
    required: Boolean = false,
    block: BuilderField.FieldBuilder<SELF, String, BUILDER>.() -> Unit = {}
): BUILDER {
    val lookup = MethodHandles.lookup()
    val getter = lookup.unreflect(property.getter.javaMethod)
    val setter = lookup.unreflect(property.setter.javaMethod)

    val serializer = serializer<T>()

    @Suppress("UNCHECKED_CAST")
    val append = this.append(
        KeyedCodec(name, Codec.STRING, required),
        { comp: SELF, value: String ->
            val str = Json.decodeFromString(serializer, value)
            setter.invoke(comp, str)
        },
        { comp: SELF ->
            val value = getter.invoke(comp) as T
            Json.encodeToString(value)
        }
    )
    block.invoke(append)
    return append.add()
}

fun <T> Codec<T>.array(default: T? = null): ArrayCodec<T?> {
    return ArrayCodec<T?>(this, { arrayOfNulls(it) }, { default })
}

fun <T> Codec<T>.map(immutable: Boolean = false): MapCodec<T, MutableMap<String,T>> {
    return MapCodec(this, ::Object2ObjectOpenHashMap, immutable)
}