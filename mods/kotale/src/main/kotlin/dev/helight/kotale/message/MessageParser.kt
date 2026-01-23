package dev.helight.kotale.message

import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.receiver.IMessageReceiver
import java.util.*

object MessageParser {
    private val formatVariableRegex = Regex("""\{([A-Za-z0-9_]+)}""")
    private val tagVariableRegex = Regex("""<var:([A-Za-z0-9_]+)\\/>""")

    fun extractFormatVariables(input: String): List<String> {
        return formatVariableRegex
            .findAll(input)
            .map { it.groupValues[1] }
            .distinct().toList()
    }

    fun extractTagVariables(input: String): Set<String> {
        return tagVariableRegex
            .findAll(input)
            .map { it.groupValues[1] }
            .toSet()
    }

    fun extractAllVariables(input: String): Set<String> {
        val formatVars = extractFormatVariables(input).toSet()
        val tagVars = extractTagVariables(input)
        return formatVars + tagVars
    }

    fun replaceFormatVariables(
        input: String,
        values: Map<String, String>
    ): String {
        return formatVariableRegex.replace(input) { match ->
            val key = match.groupValues[1]
            values[key] ?: match.value
        }
    }

    fun escape(input: String): String {
        val out = StringBuilder(input.length)

        for (c in input) {
            when (c) {
                '\\' -> out.append("\\\\")
                '<' -> out.append("\\<")
                '>' -> out.append("\\>")
                '/' -> out.append("\\/")
                else -> out.append(c)
            }
        }
        return out.toString()
    }

    fun parseFormat(input: String, params: Map<String, String> = emptyMap()): Message {
        val rawInput = replaceFormatVariables(input, params)
        return parse(rawInput, params)
    }

    fun parse(input: String, params: Map<String, String> = emptyMap()): Message {
        val root = Message.empty()
        val stack = ArrayDeque<Message>()
        stack.push(root)

        var i = 0
        val buffer = StringBuilder()

        fun flushText() {
            if (buffer.isNotEmpty()) {
                stack.peek().insert(buffer.toString())
                buffer.setLength(0)
            }
        }

        while (i < input.length) {
            val c = input[i]

            // Escape handling
            if (c == '\\' && i + 1 < input.length) {
                when (val next = input[i + 1]) {
                    '\\', '<', '>', '/' -> {
                        buffer.append(next)
                        i += 2
                        continue
                    }
                }
                buffer.append(c)
                i++
                continue
            }

            if (c == '<') {
                val end = input.indexOf('>', i)
                if (end == -1) {
                    buffer.append(c)
                    i++
                    continue
                }

                flushText()

                val tag = input.substring(i + 1, end).trim()
                i = end + 1

                if (tag == "/") {
                    if (stack.size > 1) {
                        stack.pop()
                    }
                    continue
                } else if (tag.endsWith("/")) {
                    val value = tag.dropLast(1).trim()
                    val inline = createInlineMessage(value, params)
                    if (inline != null) {
                        stack.peek().insert(inline)
                    } else {
                        stack.peek().insert("<$tag>")
                    }
                    continue
                }

                val newMsg = Message.empty()
                applyTag(stack, newMsg, tag)

                stack.peek().insert(newMsg)
                stack.push(newMsg)
            } else {
                buffer.append(c)
                i++
            }
        }

        flushText()
        return root
    }

    private fun createInlineMessage(tag: String, params: Map<String, String>): Message? {
        val parts = tag.split(':', limit = 2)
        if (parts.size != 2) return null

        val type = parts[0].lowercase()
        val value = parts[1]

        return when (type) {
            "var" -> Message.raw(params[value] ?: "")
            "tr", "i18n" -> Message.translation(value)
            else -> null
        }
    }

    private fun applyTag(stack: ArrayDeque<Message>, msg: Message, tag: String) {
        if (tag.startsWith("r")) {
            while (stack.size > 1) {
                stack.pop()
            }
        }
        if (tag == "reset") return
        when {
            tag.equals("bold", true) -> msg.bold(true)
            tag.equals("italic", true) -> msg.italic(true)
            tag.equals("mono", true) || tag.equals("monospace", true) ->
                msg.monospace(true)

            tag.startsWith("color:", true) ->
                msg.color(tag.substringAfter(':'))
            tag.startsWith("#") -> msg.color(tag)
            tag.startsWith("link:", true) ->
                msg.link(tag.substringAfter(':'))
            tag == "br" -> msg.insert("\n")
            else -> tag.forEach {
                val colorValue = SimpleColors.charMap[it]
                if (colorValue != null) {
                    msg.color(colorValue)
                }
                when (it.lowercaseChar()) {
                    'b' -> msg.bold(true)
                    'i' -> msg.italic(true)
                    'm' -> msg.monospace(true)
                }
            }
        }
    }
}