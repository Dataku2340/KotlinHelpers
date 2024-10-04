package defaultmaps


/**
 * A [HashMap] that returns a default value for a key if the key is not present in the map.
 * The default value is calculated by the [defaultValue] function.
 * Optionally, you can use [getOrPut] to insert the default value into the map.
 */
class DefaultHashMap<K, V: Any>(val defaultValue: (K) -> V) : HashMap<K, V>() {
    /**
     * Returns the value for the given [key] if the value **is present** (even if it is `null`).
     * Otherwise, calls the [defaultValue] function, and returns the call result **without putting the result into the map**.
     * This is useful if you want to get the default value without modifying the map.
     * If you want to insert the default value into the map, use [getOrPut] instead.
     */
    override fun get(key: K): V = super.get(key) ?: defaultValue(key) // V is not nullable -> null means no value in map.

    /**
     * Returns the value for the given [key] if the value **is present** (even if it is `null`).
     * Otherwise, calls the [defaultValue] function,
     * puts its result into the map under the given key and returns the call result.
     *
     * Note that the operation is not guaranteed to be atomic if the map is being modified concurrently.
     */
    fun getOrPut(key: K): V {
        val value = super.get(key)
        return if (value == null) {
            val answer = defaultValue(key)
            put(key, answer)
            answer
        } else {
            value
        }
    }
}

/**
 * Returns a new [DefaultHashMap] with the given [defaultValue] function.
 * Unlike [withDefault], this function returns a [DefaultHashMap].
 *
 * The result type of [DefaultHashMap].get(key) is `V`, not `V?`, making this a type-smart alternative to [withDefault].
 */
fun <K, V: Any> MutableMap<K, V>.addDefault(defaultValue: (K) -> V): DefaultHashMap<K, V> = DefaultHashMap(defaultValue).also { it.putAll(this) }