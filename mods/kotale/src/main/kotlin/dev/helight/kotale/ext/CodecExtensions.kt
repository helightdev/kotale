package dev.helight.kotale.ext

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.codec.KeyedCodec
import com.hypixel.hytale.codec.builder.BuilderCodec
import com.hypixel.hytale.codec.builder.BuilderField
import java.lang.invoke.MethodHandles
import kotlin.reflect.KMutableProperty
import kotlin.reflect.jvm.javaMethod

fun <SELF, T, BUILDER : BuilderCodec.BuilderBase<SELF, BUILDER>> BUILDER.appendProperty(
    property: KMutableProperty<T>, codec: Codec<T>,
    name: String = property.name.replaceFirstChar(Char::uppercase),
    required: Boolean = false
): BuilderField.FieldBuilder<SELF, T, BUILDER> {
    val lookup = MethodHandles.lookup()
    val getter = lookup.unreflect(property.getter.javaMethod)
    val setter = lookup.unreflect(property.setter.javaMethod)

    @Suppress("UNCHECKED_CAST")
    return this.append(
        KeyedCodec<T>(name, codec, required),
        { comp: SELF, value: T -> setter.invoke(comp, value) },
        { comp: SELF -> getter.invoke(comp) as T }
    )
}