import com.hypixel.hytale.codec.Codec
import kotlinx.serialization.Serializable
import kotlin.test.Test
import com.hypixel.hytale.codec.ExtraInfo
import dev.helight.kotale.ext.array
import dev.helight.kotale.ext.property
import dev.helight.kotale.ext.opaqueSerializedProperty
import dev.helight.kotale.ext.builderCodec
import dev.helight.kotale.ext.map
import java.util.UUID
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class CodecTests {

    @Test
    fun `Test opaque serialization works`() {
        val initial = MyCodecClass(
            MySerializeClass(12, "abc"), mutableMapOf(
                "a" to "b", "b" to "c"
            )
        )
        val encoded = MyCodecClass.CODEC.encode(initial, ExtraInfo())
        val decoded = MyCodecClass.CODEC.decode(encoded, ExtraInfo())
        MyCodecClass.CODEC.afterDecodeAndValidate(decoded, ExtraInfo())
        assertEquals(initial, decoded)
    }

    @Test
    fun `Test Array Serialization`() {
        val codec = Codec.UUID_BINARY.array()
        val value = arrayOf(UUID.randomUUID(), UUID.randomUUID())
        val encoded = codec.encode(value, ExtraInfo())
        val decoded = codec.decode(encoded, ExtraInfo())
        assertContentEquals(value, decoded)
    }

}


data class MyCodecClass(
    var inner: MySerializeClass = MySerializeClass(0, "default"),
    var map: MutableMap<String, String> = mutableMapOf()
) {

    companion object {
        val CODEC = builderCodec(::MyCodecClass)
            .opaqueSerializedProperty(MyCodecClass::inner)
            .property(MyCodecClass::map, Codec.STRING.map())
            .build()
    }
}

@Serializable
data class MySerializeClass(
    val a: Int,
    val b: String
)