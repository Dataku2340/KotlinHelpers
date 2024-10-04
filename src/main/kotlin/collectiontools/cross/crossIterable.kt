@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package collectiontools.cross

import nuples.*

// ===============================
// Special functions such as vararg or infix
// ===============================

/**
 * Crosses an arbitrary number of iterables (cartesian product).
 *
 * `collectiontools.cross.crossN()` is equivalent to `sequenceOf(emptyList())`.
 *
 * `collectiontools.cross.crossN(list) is equivalent to `list.map { listOf(it) }.asSequence()`.
 *
 * `collectiontools.cross.crossN(l1, l2, emptyList(), l3, l4)` is equivalent to `emptySequence()`.
 */
inline fun <T> crossN(vararg iterables: Iterable<T>) =
    IterableCrosser(*iterables) { ArrayList(it) }.asSequence()

/**
 * Crosses an arbitrary number of iterables `count` times (cartesian product).
 *
 * `collectiontools.cross.crossWithCount(listOf(1, 2, 3) to 2, listOf(4, 5) to 1)` is equivalent to:
 *
 * `collectiontools.cross.cross(listOf(1, 2, 3), listOf(1, 2, 3), listOf(4, 5), listOf(4, 5))`
 */
inline fun <T> crossWithCount(vararg iterablesAndCounts: Pair<Iterable<T>, Int>): Sequence<List<T>> {
    require(iterablesAndCounts.all { it.second >= 0 }) { "All counts must be non-negative." }
    // just get the references, NOT copy the list elements
    val iterables = ArrayList<Iterable<T>>(iterablesAndCounts.sumOf { it.second })
    iterablesAndCounts.forEach { (iterable, count) -> repeat(count) { iterables.add(iterable) } }
    return crossN(*iterables.toTypedArray())
}

/**
 * Crosses this iterable with the other iterable (cartesian product).
 */
inline infix fun <A, B> Iterable<A>.crossWith(other: Iterable<B>) = cross(this, other)

/**
 * Crosses this iterable with the other iterable and applies a transformation to the resulting pairs (cartesian product).
 */
inline fun <A, B, RES> Iterable<A>.crossWith(other: Iterable<B>, crossinline transformation: (A, B) -> RES) = cross(this, other, transformation)

/**
 * Crosses an iterable with itself `times` times (cartesian product).
 *
 * `collectiontools.cross.crossSelf(0)` is equivalent to `sequenceOf(emptyList())`.
 *
 * `listOf(1, 2, 3).collectiontools.cross.crossSelf(2)` is equivalent to `collectiontools.cross.cross(listOf(1, 2, 3), listOf(1, 2, 3))`
 */
inline fun <T> Iterable<T>.crossSelf(times: Int): Sequence<List<T>> {
    require(times >= 0) { "Times must be non-negative." }
    if (times == 0) return sequenceOf(emptyList())
    if (times == 1) return this.asSequence().map { listOf(it) }
    if (this is Collection<*> && this.isEmpty())
        return emptySequence()

    val iterables = ArrayList<Iterable<T>>(times)
    repeat(times) { iterables.add(this) }
    return crossN(*iterables.toTypedArray())
}


// ====================================================
// Default and transformation collectiontools.cross.cross of 2-10 iterables
// ====================================================

/**
 * Crosses (cartesian product)  two iterables.
 */
inline fun <A, B> cross(first: Iterable<A>, second: Iterable<B>): Sequence<Pair<A, B>> = IterableCrosser(first, second) { it.toPair<A, B>() }.asSequence()

/**
 * Crosses (cartesian product)  two iterables and applies a transformation to the resulting pairs.
 */
inline fun <A, B, RES> cross(first: Iterable<A>, second: Iterable<B>, crossinline transformation: (A, B) -> RES): Sequence<RES> =
    IterableCrosser(first, second) {
        transformation(it[0] as A, it[1] as B)
    }.asSequence()


/**
 * Crosses (cartesian product)  three iterables.
 */
inline fun <A, B, C> cross(first: Iterable<A>, second: Iterable<B>, third: Iterable<C>): Sequence<Triple<A, B, C>> =
    IterableCrosser(first, second, third) { it.toTriple<A, B, C>() }.asSequence()


/**
 * Crosses (cartesian product)  three iterables and applies a transformation to the resulting triples.
 */
inline fun <A, B, C, RES> cross(
    first: Iterable<A>, second: Iterable<B>, third: Iterable<C>, crossinline transformation: (A, B, C) -> RES
): Sequence<RES> =
    IterableCrosser(first, second, third) {
        transformation(it[0] as A, it[1] as B, it[2] as C)
    }.asSequence()

/**
 * Crosses (cartesian product)  four iterables.
 */
inline fun <A, B, C, D> cross(first: Iterable<A>, second: Iterable<B>, third: Iterable<C>, fourth: Iterable<D>): Sequence<Quadruple<A, B, C, D>> =
    IterableCrosser(first, second, third, fourth) { it.toQuadruple<A, B, C, D>() }.asSequence()

/**
 * Crosses (cartesian product)  four iterables and applies a transformation to the resulting quadruples.
 */
inline fun <A, B, C, D, RES> cross(
    first: Iterable<A>, second: Iterable<B>, third: Iterable<C>, fourth: Iterable<D>, crossinline transformation: (A, B, C, D) -> RES
): Sequence<RES> =
    IterableCrosser(first, second, third, fourth) {
        transformation(it[0] as A, it[1] as B, it[2] as C, it[3] as D)
    }.asSequence()

/**
 * Crosses (cartesian product)  five iterables.
 */
inline fun <A, B, C, D, E> cross(
    first: Iterable<A>, second: Iterable<B>, third: Iterable<C>, fourth: Iterable<D>, fifth: Iterable<E>
): Sequence<Quintuple<A, B, C, D, E>> = IterableCrosser(first, second, third, fourth, fifth) { it.toQuintuple<A, B, C, D, E>() }.asSequence()

/**
 * Crosses (cartesian product)  five iterables and applies a transformation to the resulting quintuples.
 */
inline fun <A, B, C, D, E, RES> cross(
    first: Iterable<A>, second: Iterable<B>, third: Iterable<C>, fourth: Iterable<D>, fifth: Iterable<E>, crossinline transformation: (A, B, C, D, E) -> RES
): Sequence<RES> =
    IterableCrosser(first, second, third, fourth, fifth) {
        transformation(it[0] as A, it[1] as B, it[2] as C, it[3] as D, it[4] as E)
    }.asSequence()

/**
 * Crosses (cartesian product)  six iterables.
 */
inline fun <A, B, C, D, E, F> cross(
    first: Iterable<A>, second: Iterable<B>, third: Iterable<C>, fourth: Iterable<D>, fifth: Iterable<E>, sixth: Iterable<F>
): Sequence<Sextuple<A, B, C, D, E, F>> = IterableCrosser(first, second, third, fourth, fifth, sixth) { it.toSextuple<A, B, C, D, E, F>() }.asSequence()

/**
 * Crosses (cartesian product)  six iterables and applies a transformation to the resulting sextuples.
 */
inline fun <A, B, C, D, E, F, RES> cross(
    first: Iterable<A>,
    second: Iterable<B>,
    third: Iterable<C>,
    fourth: Iterable<D>,
    fifth: Iterable<E>,
    sixth: Iterable<F>,
    crossinline transformation: (A, B, C, D, E, F) -> RES
): Sequence<RES> =
    IterableCrosser(first, second, third, fourth, fifth, sixth) {
        transformation(it[0] as A, it[1] as B, it[2] as C, it[3] as D, it[4] as E, it[5] as F)
    }.asSequence()

/**
 * Crosses (cartesian product)  seven iterables and applies a transformation to the resulting septuples.
 */
inline fun <A, B, C, D, E, F, G, RES> cross(
    first: Iterable<A>,
    second: Iterable<B>,
    third: Iterable<C>,
    fourth: Iterable<D>,
    fifth: Iterable<E>,
    sixth: Iterable<F>,
    seventh: Iterable<G>,
    crossinline transformation: (A, B, C, D, E, F, G) -> RES
): Sequence<RES> =
    IterableCrosser(first, second,  third, fourth, fifth, sixth, seventh) {
        transformation(it[0] as A, it[1] as B, it[2] as C, it[3] as D, it[4] as E, it[5] as F, it[6] as G)
    }.asSequence()

/**
 * Crosses (cartesian product)  seven iterables.
 */
inline fun <A, B, C, D, E, F, G> cross(
    first: Iterable<A>, second: Iterable<B>, third: Iterable<C>, fourth: Iterable<D>, fifth: Iterable<E>, sixth: Iterable<F>, seventh: Iterable<G>
): Sequence<Septuple<A, B, C, D, E, F, G>> =
    IterableCrosser(first, second, third, fourth, fifth, sixth, seventh) { it.toSeptuple<A, B, C, D, E, F, G>() }.asSequence()

/**
 * Crosses (cartesian product)  eight iterables.
 */
inline fun <A, B, C, D, E, F, G, H> cross(
    first: Iterable<A>,
    second: Iterable<B>,
    third: Iterable<C>,
    fourth: Iterable<D>,
    fifth: Iterable<E>,
    sixth: Iterable<F>,
    seventh: Iterable<G>,
    eighth: Iterable<H>
): Sequence<Octuple<A, B, C, D, E, F, G, H>> =
    IterableCrosser(first, second, third, fourth, fifth, sixth, seventh, eighth) { it.toOctuple<A, B, C, D, E, F, G, H>() }.asSequence()

/**
 * Crosses (cartesian product)  eight iterables and applies a transformation to the resulting octuples.
 */
inline fun <A, B, C, D, E, F, G, H, RES> cross(
    first: Iterable<A>,
    second: Iterable<B>,
    third: Iterable<C>,
    fourth: Iterable<D>,
    fifth: Iterable<E>,
    sixth: Iterable<F>,
    seventh: Iterable<G>,
    eighth: Iterable<H>,
    crossinline transformation: (A, B, C, D, E, F, G, H) -> RES
): Sequence<RES> =
    IterableCrosser(first, second, third, fourth, fifth, sixth, seventh, eighth) {
        transformation(it[0] as A, it[1] as B, it[2] as C, it[3] as D, it[4] as E, it[5] as F, it[6] as G, it[7] as H)
    }.asSequence()

/**
 * Crosses (cartesian product)  nine iterables.
 */
inline fun <A, B, C, D, E, F, G, H, I> cross(
    first: Iterable<A>,
    second: Iterable<B>,
    third: Iterable<C>,
    fourth: Iterable<D>,
    fifth: Iterable<E>,
    sixth: Iterable<F>,
    seventh: Iterable<G>,
    eighth: Iterable<H>,
    ninth: Iterable<I>
): Sequence<Nonuple<A, B, C, D, E, F, G, H, I>> =
    IterableCrosser(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth) { it.toNonuple<A, B, C, D, E, F, G, H, I>() }.asSequence()

/**
 * Crosses (cartesian product)  nine iterables and applies a transformation to the resulting nonuples.
 */
inline fun <A, B, C, D, E, F, G, H, I, RES> cross(
    first: Iterable<A>,
    second: Iterable<B>,
    third: Iterable<C>,
    fourth: Iterable<D>,
    fifth: Iterable<E>,
    sixth: Iterable<F>,
    seventh: Iterable<G>,
    eighth: Iterable<H>,
    ninth: Iterable<I>,
    crossinline transformation: (A, B, C, D, E, F, G, H, I) -> RES
): Sequence<RES> =
    IterableCrosser(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth) {
        transformation(it[0] as A, it[1] as B, it[2] as C, it[3] as D, it[4] as E, it[5] as F, it[6] as G, it[7] as H, it[8] as I)
    }.asSequence()

/**
 * Crosses (cartesian product)  ten iterables.
 */
inline fun <A, B, C, D, E, F, G, H, I, J> cross(
    first: Iterable<A>,
    second: Iterable<B>,
    third: Iterable<C>,
    fourth: Iterable<D>,
    fifth: Iterable<E>,
    sixth: Iterable<F>,
    seventh: Iterable<G>,
    eighth: Iterable<H>,
    ninth: Iterable<I>,
    tenth: Iterable<J>
): Sequence<Decuple<A, B, C, D, E, F, G, H, I, J>> =
    IterableCrosser(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth) { it.toDecuple<A, B, C, D, E, F, G, H, I, J>() }.asSequence()

/**
 * Crosses (cartesian product)  ten iterables and applies a transformation to the resulting decuples.
 */
inline fun <A, B, C, D, E, F, G, H, I, J, RES> cross(
    first: Iterable<A>,
    second: Iterable<B>,
    third: Iterable<C>,
    fourth: Iterable<D>,
    fifth: Iterable<E>,
    sixth: Iterable<F>,
    seventh: Iterable<G>,
    eighth: Iterable<H>,
    ninth: Iterable<I>,
    tenth: Iterable<J>,
    crossinline transformation: (A, B, C, D, E, F, G, H, I, J) -> RES
): Sequence<RES> =
    IterableCrosser(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth) {
        transformation(it[0] as A, it[1] as B, it[2] as C, it[3] as D, it[4] as E, it[5] as F, it[6] as G, it[7] as H, it[8] as I, it[9] as J)
    }.asSequence()





