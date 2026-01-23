package dev.helight.kotale.message

import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.receiver.IMessageReceiver

fun IMessageReceiver.sendMessage(message: String) {
    this.sendMessage(MessageParser.parse(message))
}

fun IMessageReceiver.sendMessage(format: String, params: Map<String, String>) {
    this.sendMessage(MessageParser.parseFormat(format, params))
}

fun IMessageReceiver.sendMessageFormat(format: String, vararg params: String) {
    val names = MessageParser.extractFormatVariables(format)
        .mapIndexed { index, string -> string to params.getOrNull(index).orEmpty() }
        .toMap()
    this.sendMessage(MessageParser.parseFormat(format, names))
}

fun IMessageReceiver.sendRawMessage(message: String) {
    this.sendMessage(Message.raw(message))
}

fun Message.toRaw(): Message {
    val builder = StringBuilder()
    visitMessage(this, builder)
    return Message.raw(builder.toString())
}

private fun visitMessage(message: Message, builder: StringBuilder) {
    val text = message.rawText
    if (text != null) builder.append(text)
    message.children.forEach {
        visitMessage(it, builder)
    }
}