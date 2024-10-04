@file:Suppress("DuplicatedCode")

package iterablesequencetools

import defaultmaps.addDefault
import kotlin.math.sqrt

// ================================================
// ========== Iterables ===========================
// ================================================

/**
 * Returns the total count of elements and the count of elements that satisfy the predicate.
 * @throws ArithmeticException if the total count exceeds [Int.MAX_VALUE]
 */
inline fun <T> Iterable<T>.countWithTotal(predicate: (T) -> Boolean): Pair<Int, Int> {
    var count = 0
    var totalCount = 0
    for (element in this) {
        if (totalCount == Int.MAX_VALUE) throw ArithmeticException("Count overflow has happened.")
        totalCount++
        if (predicate(element))
            count++
    }
    return count to totalCount
}

/**
 * Returns the product of all elements in order.
 */
fun Iterable<Double>.product(): Double {
    var prod = 1.0
    for (element in this) prod *= element
    return prod
}

/**
 * Returns the product of all elements in order.
 * Overflows are not detected.
 */
fun Iterable<Int>.product(): Int {
    var prod = 1
    for (element in this) prod *= element
    return prod
}

/**
 * Returns the product of all elements in order, returning a [Long].
 * Overflows are not detected.
 */
fun Iterable<Int>.productLong(): Long {
    var prod = 1L
    for (element in this) prod *= element
    return prod
}

/**
 * Returns the product of all values produced by the [transform] function applied to each element in the collection.
 */
inline fun <T> Iterable<T>.productOf(transform: (T) -> Double): Double {
    var prod = 1.0
    for (element in this) prod *= transform(element)
    return prod
}

/**
 * Returns the product of all values produced by the [transform] function applied to each element in the collection.
 */
inline fun <T> Iterable<T>.productOfInt(transform: (T) -> Int): Int {
    var prod = 1
    for (element in this) prod *= transform(element)
    return prod
}

/**
 * Returns the product of all values produced by the [transform] function applied to each element in the collection.
 */
inline fun <T> Iterable<T>.productOfLong(transform: (T) -> Long): Long {
    var prod = 1L
    for (element in this) prod *= transform(element)
    return prod
}

/**
 * Returns the arithmetic mean of all values produced by the [transform] function applied to each element in the collection.
 * If the collection is empty, returns [Double.NaN].
 *
 * @throws ArithmeticException if the total count exceeds [Int.MAX_VALUE]
 */
inline fun <T> Iterable<T>.averageOf(transform: (T) -> Double): Double {
    var sum = 0.0
    var count = 0
    for (element in this) {
        if (count == Int.MAX_VALUE) throw ArithmeticException("Count overflow has happened.")

        sum += transform(element)
        count += 1
    }
    return if (count == 0) Double.NaN else sum / count
}

/**
 * Returns the arithmetic mean of all values produced by the [transform] function applied to each element in the collection.
 * If the collection is empty, returns null.
 *
 * @throws ArithmeticException if the total count exceeds [Int.MAX_VALUE]
 */
fun <T> Iterable<T>.averageOfOrNull(transform: (T) -> Double): Double? {
    var sum = 0.0
    var count = 0
    for (element in this) {
        if (count == Int.MAX_VALUE) throw ArithmeticException("Count overflow has happened.")

        sum += transform(element)
        count++
    }
    return if (count == 0) null else sum / count
}


/**
 * Returns the empirical standard deviation of all values in the collection.
 * If the collection is empty or has one element, returns [Double.NaN].
 *
 * @throws ArithmeticException if the total count exceeds [Int.MAX_VALUE]
 */
fun Iterable<Double>.empStandardDev(): Double = sqrt(this.empVariance())

/**
 * Returns the empirical variance of all values in the collection.
 * If the collection is empty or has one element, returns [Double.NaN].
 *
 * @throws ArithmeticException if the total count exceeds [Int.MAX_VALUE]
 */
fun Iterable<Double>.empVariance(): Double {
    var sum = 0.0
    var count = 0
    for (element in this) {
        if (count == Int.MAX_VALUE) throw ArithmeticException("Count overflow has happened.")

        sum += element
        count++
    }

    if (count <= 1) return Double.NaN

    val mean = sum / count
    val ssq = this.sumOf { val a = it - mean; a * a }
    return ssq / (count - 1)
}

/**
 * Returns the standard deviation of all values in the collection.
 * If the collection is empty, returns [Double.NaN].
 *
 * @throws ArithmeticException if the total count exceeds [Int.MAX_VALUE]
 */
fun Iterable<Double>.standardDev(): Double = sqrt(this.variance())

/**
 * Returns the variance of all values in the collection.
 * If the collection is empty, returns [Double.NaN].
 *
 * @throws ArithmeticException if the total count exceeds [Int.MAX_VALUE]
 */
fun Iterable<Double>.variance(): Double {
    var sum = 0.0
    var count = 0
    for (element in this) {
        if (count == Int.MAX_VALUE) throw ArithmeticException("Count overflow has happened.")

        sum += element
        count++
    }

    val mean = sum / count
    val ssq = this.sumOf { val a = it - mean; a * a }
    return ssq / count
}

/**
 * Returns a map containing the count of occurrences of each element in the collection.
 */
fun <T> Iterable<T>.valueCounts(): Map<T, Int> {
    val map = HashMap<T, Int>().addDefault { 0 }
    for (element in this) map[element] += 1
    return map
}
