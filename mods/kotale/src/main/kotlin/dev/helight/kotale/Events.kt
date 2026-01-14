package dev.helight.kotale

import com.hypixel.hytale.event.IBaseEvent
import com.hypixel.hytale.event.IEventRegistry

inline fun <reified EventType: IBaseEvent<*>> IEventRegistry.register(crossinline event: (EventType) -> Unit) {
    this.register(EventType::class.java) {
        event(it)
    }
}

inline fun <reified EventType: IBaseEvent<Any>> IEventRegistry.registerGlobal(crossinline event: (EventType) -> Unit) {
    this.registerGlobal(EventType::class.java) {
        event(it)
    }
}