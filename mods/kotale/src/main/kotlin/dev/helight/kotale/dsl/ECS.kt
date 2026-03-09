package dev.helight.kotale.dsl

import com.hypixel.hytale.component.query.Query

operator fun <T> Query<T>.plus(other: Query<T>): KAndQuery<T> {
    val entries = mutableListOf<Query<T>>()
    if (this is KAndQuery<T>) entries.addAll(this.queries) else entries.add(this)
    if (other is KAndQuery<T>) entries.addAll(other.queries) else entries.add(other)
    return KAndQuery(entries.toTypedArray())
}

operator fun <T> Query<T>.div(other: Query<T>): KOrQuery<T> {
    val entries = mutableListOf<Query<T>>()
    if (this is KOrQuery<T>) entries.addAll(this.queries) else entries.add(this)
    if (other is KOrQuery<T>) entries.addAll(other.queries) else entries.add(other)
    return KOrQuery(entries.toTypedArray())
}

operator fun <T> Query<T>.not(): Query<T> = Query.not(this)

class KAndQuery<T>(
    val queries: Array<Query<T>>,
    delegated: Query<T> = Query.and<T>(*queries)
) : Query<T> by delegated

class KOrQuery<T>(
    val queries: Array<Query<T>>,
    delegated: Query<T> = Query.or<T>(*queries)
) : Query<T> by delegated