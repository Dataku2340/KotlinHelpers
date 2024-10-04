@file:Suppress("NOTHING_TO_INLINE")

package collectiontools

/**
 * Alias for `println(this)`.
 */
fun Any?.println() = println(this)

/**
 * Checks if all elements in the collection are distinct.
 */
inline fun <T> Collection<T>.allDistinct(): Boolean {
    if (this is Set) return true

    val set = HashSet<T>(this.size)
    return this.all { set.add(it) }
}

/**
 * Checks if the values returned by the given [selector] function are distinct.
 */
inline fun <T, K> Collection<T>.allDistinctBy(selector: (T) -> K): Boolean {
    val set = HashSet<K>(this.size)
    return this.all { set.add(selector(it)) }
}


/**
 * Zips two maps together, using default values if a key is missing in one of the maps.
 * Example:
 * ```
 * val map1 = mapOf("a" to 1, "b" to 2)
 * val map2 = mapOf("b" to 3, "c" to 4)
 * val zipped = map1.zipValues(map2, 0, 0)
 * // zipped == mapOf("a" to (1, 0), "b" to (2, 3), "c" to (0, 4))
 * ```
 */
fun <K, K1 : K, K2 : K, V1, V2> Map<K1, V1>.zipValues(
    other: Map<K2, V2>, defaultFirst: V1, defaultSecond: V2
): Map<K, Pair<V1, V2>> {
    val keys = this.keys + other.keys
    return keys.associateWith { (this[it] ?: defaultFirst) to (other[it] ?: defaultSecond) }
}

/**
 * Zips two maps together, returning null if a key of the other map is missing in one of the maps.
 * Example:
 * ```
 * val map1 = mapOf("a" to 1, "b" to 2)
 * val map2 = mapOf("b" to 3, "c" to 4)
 * val zipped = map1.zipValuesOrNull(map2)
 * // zipped == mapOf("a" to (1, null), "b" to (2, 3), "c" to (null, 4))
 * ```
 */
infix fun <K, K1 : K, K2 : K, V1, V2> Map<K1, V1>.zipValuesOrNull(other: Map<K2, V2>): Map<K, Pair<V1?, V2?>> {
    val keys = this.keys + other.keys
    return keys.associateWith { this[it] to other[it] }
}

/**
 * Zips two maps together, throwing an exception if a key of the other map is missing in one of the maps.
 * Example:
 * ```
 * val map1 = mapOf("a" to 1, "b" to 2)
 * val map2 = mapOf("b" to 3, "c" to 4)
 * val zipped = map1.zipValuesOrNull(map2)
 * // throws IllegalArgumentException("Key c not found in first map") or
 * // throws IllegalArgumentException("Key a not found in second map")
 * ```
 * */
infix fun <K, K1 : K, K2 : K, V1, V2> Map<K1, V1>.zipValuesOrThrow(other: Map<K2, V2>): Map<K, Pair<V1?, V2?>> {
    val keys = this.keys + other.keys
    return keys.associateWith {
        Pair(
            this[it] ?: throw IllegalArgumentException("Key $it not found in first map"),
            other[it] ?: throw IllegalArgumentException("Key $it not found in second map")
        )
    }
}