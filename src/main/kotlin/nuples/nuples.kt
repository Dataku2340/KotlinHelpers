package nuples

// Data classes after triple
/**
 * A data class representing four values.
 */
data class Quadruple<out A, out B, out C, out D>(val a: A, val b: B, val c: C, val d: D)

/**
 * A data class representing five values.
 */
data class Quintuple<out A, out B, out C, out D, out E>(val a: A, val b: B, val c: C, val d: D, val e: E)

/**
 * A data class representing six values.
 */
data class Sextuple<out A, out B, out C, out D, out E, out F>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F)

/**
 * A data class representing seven values.
 */
data class Septuple<out A, out B, out C, out D, out E, out F, out G>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G)

/**
 * A data class representing eight values.
 */
data class Octuple<out A, out B, out C, out D, out E, out F, out G, out H>(val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G, val h: H)

/**
 * A data class representing nine values.
 */
data class Nonuple<out A, out B, out C, out D, out E, out F, out G, out H, out I>
    (val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G, val h: H, val i: I)

/**
 * A data class representing ten values.
 */
data class Decuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J>
    (val a: A, val b: B, val c: C, val d: D, val e: E, val f: F, val g: G, val h: H, val i: I, val j: J)


// ToList-Functions
/**
 * Converts this quadruple into a list.
 */
fun <T> Quadruple<T, T, T, T>.toList(): List<T> = listOf(a, b, c, d)

/**
 * Converts this quintuple into a list.
 */
fun <T> Quintuple<T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e)

/**
 * Converts this sextuple into a list.
 */
fun <T> Sextuple<T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f)

/**
 * Converts this septuple into a list.
 */
fun <T> Septuple<T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g)

/**
 * Converts this octuple into a list.
 */
fun <T> Octuple<T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h)

/**
 * Converts this nonuple into a list.
 */
fun <T> Nonuple<T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i)

/**
 * Converts this decuple into a list.
 */
fun <T> Decuple<T, T, T, T, T, T, T, T, T, T>.toList(): List<T> = listOf(a, b, c, d, e, f, g, h, i, j)
