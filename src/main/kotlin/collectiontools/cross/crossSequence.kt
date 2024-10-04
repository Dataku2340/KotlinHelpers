@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package collectiontools.cross

import nuples.*


// ===============================
// Special functions such as vararg or infix
// ===============================

/**
 * Crosses an arbitrary number of sequences (cartesian product).
 */
inline fun <T> crossN(vararg sequences: Sequence<T>) =
    SequenceCrosser(*sequences) { ArrayList(it) }.asSequence()

/**
 * Crosses an arbitrary number of sequences `count` times (cartesian product).
 *
 * `crossWithCount(listOf(1, 2, 3) to 2, listOf(4, 5) to 1)` is equivalent to:
 *
 * `cross(listOf(1, 2, 3), listOf(1, 2, 3), listOf(4, 5), listOf(4, 5))`
 */
inline fun <T> crossWithCount(vararg sequencesAndCounts: Pair<Sequence<T>, Int>): Sequence<List<T>> {
    require(sequencesAndCounts.all { it.second >= 0 }) { "All counts must be non-negative." }
    // just get the references, NOT copy the list elements
    val sequences = ArrayList<Sequence<T>>(sequencesAndCounts.sumOf { it.second })
    sequencesAndCounts.forEach { (sequence, count) -> repeat(count) { sequences.add(sequence) } }
    return crossN(*sequences.toTypedArray())
}

/**
 * Crosses this sequence with the other sequence (cartesian product).
 */
inline infix fun <A, B> Sequence<A>.crossWith(other: Sequence<B>) = cross(this, other)

/**
 * Crosses this sequence with the other sequence and applies a transformation to the resulting pairs (cartesian product).
 */
inline fun <A, B, RES> Sequence<A>.crossWith(other: Sequence<B>, crossinline transformation: (A, B) -> RES) = cross(this, other, transformation)

/**
 * Crosses a sequence with itself `times` times (cartesian product).
 *
 * `listOf(1, 2, 3).crossSelf(2)` is equivalent to `cross(listOf(1, 2, 3), listOf(1, 2, 3))`
 */
inline fun <T> Sequence<T>.crossSelf(times: Int): Sequence<List<T>> {
    require(times >= 0) { "Times must be non-negative." }
    if (times == 0) return sequenceOf(emptyList())
    if (times == 1) return this.map { listOf(it) }
    if (this is Collection<*> && this.isEmpty())
        return emptySequence()

    val sequences = ArrayList<Sequence<T>>(times)
    repeat(times) { sequences.add(this) }
    return crossN(*sequences.toTypedArray())
}


// ====================================================
// Default and transformation cross of 2-10 sequences
// ====================================================

/**
 * Crosses (cartesian product)  two sequences.
 */
inline fun <A, B> cross(first: Sequence<A>, second: Sequence<B>): Sequence<Pair<A, B>> = SequenceCrosser(first, second) { it.toPair<A, B>() }.asSequence()

/**
 * Crosses (cartesian product)  two sequences and applies a transformation to the resulting pairs.
 */
inline fun <A, B, RES> cross(first: Sequence<A>, second: Sequence<B>, crossinline transformation: (A, B) -> RES): Sequence<RES> =
    SequenceCrosser(first, second) {
        transformation(it[0] as A, it[1] as B)
    }.asSequence()


/**
 * Crosses (cartesian product)  three sequences.
 */
inline fun <A, B, C> cross(first: Sequence<A>, second: Sequence<B>, third: Sequence<C>): Sequence<Triple<A, B, C>> =
    SequenceCrosser(first, second, third) { it.toTriple<A, B, C>() }.asSequence()


/**
 * Crosses (cartesian product)  three sequences and applies a transformation to the resulting triples.
 */
inline fun <A, B, C, RES> cross(
    first: Sequence<A>, second: Sequence<B>, third: Sequence<C>, crossinline transformation: (A, B, C) -> RES
): Sequence<RES> =
    SequenceCrosser(first, second, third) {
        transformation(it[0] as A, it[1] as B, it[2] as C)
    }.asSequence()

/**
 * Crosses (cartesian product)  four sequences.
 */
inline fun <A, B, C, D> cross(first: Sequence<A>, second: Sequence<B>, third: Sequence<C>, fourth: Sequence<D>): Sequence<Quadruple<A, B, C, D>> =
    SequenceCrosser(first, second, third, fourth) { it.toQuadruple<A, B, C, D>() }.asSequence()

/**
 * Crosses (cartesian product)  four sequences and applies a transformation to the resulting quadruples.
 */
inline fun <A, B, C, D, RES> cross(
    first: Sequence<A>, second: Sequence<B>, third: Sequence<C>, fourth: Sequence<D>, crossinline transformation: (A, B, C, D) -> RES
): Sequence<RES> =
    SequenceCrosser(first, second, third, fourth) {
        transformation(it[0] as A, it[1] as B, it[2] as C, it[3] as D)
    }.asSequence()

/**
 * Crosses (cartesian product)  five sequences.
 */
inline fun <A, B, C, D, E> cross(
    first: Sequence<A>, second: Sequence<B>, third: Sequence<C>, fourth: Sequence<D>, fifth: Sequence<E>
): Sequence<Quintuple<A, B, C, D, E>> = SequenceCrosser(first, second, third, fourth, fifth) { it.toQuintuple<A, B, C, D, E>() }.asSequence()

/**
 * Crosses (cartesian product)  five sequences and applies a transformation to the resulting quintuples.
 */
inline fun <A, B, C, D, E, RES> cross(
    first: Sequence<A>, second: Sequence<B>, third: Sequence<C>, fourth: Sequence<D>, fifth: Sequence<E>, crossinline transformation: (A, B, C, D, E) -> RES
): Sequence<RES> =
    SequenceCrosser(first, second, third, fourth, fifth) {
        transformation(it[0] as A, it[1] as B, it[2] as C, it[3] as D, it[4] as E)
    }.asSequence()

/**
 * Crosses (cartesian product)  six sequences.
 */
inline fun <A, B, C, D, E, F> cross(
    first: Sequence<A>, second: Sequence<B>, third: Sequence<C>, fourth: Sequence<D>, fifth: Sequence<E>, sixth: Sequence<F>
): Sequence<Sextuple<A, B, C, D, E, F>> = SequenceCrosser(first, second, third, fourth, fifth, sixth) { it.toSextuple<A, B, C, D, E, F>() }.asSequence()

/**
 * Crosses (cartesian product)  six sequences and applies a transformation to the resulting sextuples.
 */
inline fun <A, B, C, D, E, F, RES> cross(
    first: Sequence<A>,
    second: Sequence<B>,
    third: Sequence<C>,
    fourth: Sequence<D>,
    fifth: Sequence<E>,
    sixth: Sequence<F>,
    crossinline transformation: (A, B, C, D, E, F) -> RES
): Sequence<RES> =
    SequenceCrosser(first, second, third, fourth, fifth, sixth) {
        transformation(it[0] as A, it[1] as B, it[2] as C, it[3] as D, it[4] as E, it[5] as F)
    }.asSequence()

/**
 * Crosses (cartesian product)  seven sequences and applies a transformation to the resulting septuples.
 */
inline fun <A, B, C, D, E, F, G, RES> cross(
    first: Sequence<A>,
    second: Sequence<B>,
    third: Sequence<C>,
    fourth: Sequence<D>,
    fifth: Sequence<E>,
    sixth: Sequence<F>,
    seventh: Sequence<G>,
    crossinline transformation: (A, B, C, D, E, F, G) -> RES
): Sequence<RES> =
    SequenceCrosser(first, second,  third, fourth, fifth, sixth, seventh) {
        transformation(it[0] as A, it[1] as B, it[2] as C, it[3] as D, it[4] as E, it[5] as F, it[6] as G)
    }.asSequence()

/**
 * Crosses (cartesian product)  seven sequences.
 */
inline fun <A, B, C, D, E, F, G> cross(
    first: Sequence<A>, second: Sequence<B>, third: Sequence<C>, fourth: Sequence<D>, fifth: Sequence<E>, sixth: Sequence<F>, seventh: Sequence<G>
): Sequence<Septuple<A, B, C, D, E, F, G>> =
    SequenceCrosser(first, second, third, fourth, fifth, sixth, seventh) { it.toSeptuple<A, B, C, D, E, F, G>() }.asSequence()

/**
 * Crosses (cartesian product)  eight sequences.
 */
inline fun <A, B, C, D, E, F, G, H> cross(
    first: Sequence<A>,
    second: Sequence<B>,
    third: Sequence<C>,
    fourth: Sequence<D>,
    fifth: Sequence<E>,
    sixth: Sequence<F>,
    seventh: Sequence<G>,
    eighth: Sequence<H>
): Sequence<Octuple<A, B, C, D, E, F, G, H>> =
    SequenceCrosser(first, second, third, fourth, fifth, sixth, seventh, eighth) { it.toOctuple<A, B, C, D, E, F, G, H>() }.asSequence()

/**
 * Crosses (cartesian product)  eight sequences and applies a transformation to the resulting octuples.
 */
inline fun <A, B, C, D, E, F, G, H, RES> cross(
    first: Sequence<A>,
    second: Sequence<B>,
    third: Sequence<C>,
    fourth: Sequence<D>,
    fifth: Sequence<E>,
    sixth: Sequence<F>,
    seventh: Sequence<G>,
    eighth: Sequence<H>,
    crossinline transformation: (A, B, C, D, E, F, G, H) -> RES
): Sequence<RES> =
    SequenceCrosser(first, second, third, fourth, fifth, sixth, seventh, eighth) {
        transformation(it[0] as A, it[1] as B, it[2] as C, it[3] as D, it[4] as E, it[5] as F, it[6] as G, it[7] as H)
    }.asSequence()

/**
 * Crosses (cartesian product)  nine sequences.
 */
inline fun <A, B, C, D, E, F, G, H, I> cross(
    first: Sequence<A>,
    second: Sequence<B>,
    third: Sequence<C>,
    fourth: Sequence<D>,
    fifth: Sequence<E>,
    sixth: Sequence<F>,
    seventh: Sequence<G>,
    eighth: Sequence<H>,
    ninth: Sequence<I>
): Sequence<Nonuple<A, B, C, D, E, F, G, H, I>> =
    SequenceCrosser(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth) { it.toNonuple<A, B, C, D, E, F, G, H, I>() }.asSequence()

/**
 * Crosses (cartesian product)  nine sequences and applies a transformation to the resulting nonuples.
 */
inline fun <A, B, C, D, E, F, G, H, I, RES> cross(
    first: Sequence<A>,
    second: Sequence<B>,
    third: Sequence<C>,
    fourth: Sequence<D>,
    fifth: Sequence<E>,
    sixth: Sequence<F>,
    seventh: Sequence<G>,
    eighth: Sequence<H>,
    ninth: Sequence<I>,
    crossinline transformation: (A, B, C, D, E, F, G, H, I) -> RES
): Sequence<RES> =
    SequenceCrosser(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth) {
        transformation(it[0] as A, it[1] as B, it[2] as C, it[3] as D, it[4] as E, it[5] as F, it[6] as G, it[7] as H, it[8] as I)
    }.asSequence()

/**
 * Crosses (cartesian product)  ten sequences.
 */
inline fun <A, B, C, D, E, F, G, H, I, J> cross(
    first: Sequence<A>,
    second: Sequence<B>,
    third: Sequence<C>,
    fourth: Sequence<D>,
    fifth: Sequence<E>,
    sixth: Sequence<F>,
    seventh: Sequence<G>,
    eighth: Sequence<H>,
    ninth: Sequence<I>,
    tenth: Sequence<J>
): Sequence<Decuple<A, B, C, D, E, F, G, H, I, J>> =
    SequenceCrosser(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth) { it.toDecuple<A, B, C, D, E, F, G, H, I, J>() }.asSequence()

/**
 * Crosses (cartesian product)  ten sequences and applies a transformation to the resulting decuples.
 */
inline fun <A, B, C, D, E, F, G, H, I, J, RES> cross(
    first: Sequence<A>,
    second: Sequence<B>,
    third: Sequence<C>,
    fourth: Sequence<D>,
    fifth: Sequence<E>,
    sixth: Sequence<F>,
    seventh: Sequence<G>,
    eighth: Sequence<H>,
    ninth: Sequence<I>,
    tenth: Sequence<J>,
    crossinline transformation: (A, B, C, D, E, F, G, H, I, J) -> RES
): Sequence<RES> =
    SequenceCrosser(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth) {
        transformation(it[0] as A, it[1] as B, it[2] as C, it[3] as D, it[4] as E, it[5] as F, it[6] as G, it[7] as H, it[8] as I, it[9] as J)
    }.asSequence()


