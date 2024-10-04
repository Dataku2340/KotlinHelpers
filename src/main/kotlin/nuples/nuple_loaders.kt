@file:Suppress("UNCHECKED_CAST")

package nuples

/**
 *  Unpack an iterable into a pair.
 *  This function uses unchecked casts, so it is not type safe.
 *  Use with caution.
 *
 *  @throws NoSuchElementException if the iterable is too short.
 *  @throws IndexOutOfBoundsException if the iterable is too long.
 */
fun <A, B> Iterable<*>.toPair(): Pair<A, B> {
    val iterator = this.iterator()

    val res = Pair(iterator.next() as A, iterator.next() as B)
    if (iterator.hasNext()) throw IndexOutOfBoundsException("Iterable is too long")
    return res
}

/**
 *  Unpack an iterable into a triple.
 *  This function uses unchecked casts, so it is not type safe.
 *  Use with caution.
 *
 *  @throws NoSuchElementException if the iterable is too short.
 *  @throws IndexOutOfBoundsException if the iterable is too long.
 */
fun <A, B, C> Iterable<*>.toTriple(): Triple<A, B, C> {
    val iterator = this.iterator()

    val res = Triple(iterator.next() as A, iterator.next() as B, iterator.next() as C)
    if (iterator.hasNext()) throw IndexOutOfBoundsException("Iterable is too long")
    return res
}

/**
 *  Unpack an iterable into a quadruple.
 *  This function uses unchecked casts, so it is not type safe.
 *  Use with caution.
 *
 *  @throws NoSuchElementException if the iterable is too short.
 *  @throws IndexOutOfBoundsException if the iterable is too long.
 */
fun <A, B, C, D> Iterable<*>.toQuadruple(): Quadruple<A, B, C, D> {
    val iterator = this.iterator()

    val res = Quadruple(iterator.next() as A, iterator.next() as B, iterator.next() as C, iterator.next() as D)
    if (iterator.hasNext()) throw IndexOutOfBoundsException("Iterable is too long")
    return res
}

/**
 *  Unpack an iterable into a quintuple.
 *  This function uses unchecked casts, so it is not type safe.
 *  Use with caution.
 *
 *  @throws NoSuchElementException if the iterable is too short.
 *  @throws IndexOutOfBoundsException if the iterable is too long.
 */
fun <A, B, C, D, E> Iterable<*>.toQuintuple(): Quintuple<A, B, C, D, E> {
    val iterator = this.iterator()

    val res = Quintuple(iterator.next() as A, iterator.next() as B, iterator.next() as C, iterator.next() as D, iterator.next() as E)
    if (iterator.hasNext()) throw IndexOutOfBoundsException("Iterable is too long")
    return res
}

/**
 *  Unpack an iterable into a sextuple.
 *  This function uses unchecked casts, so it is not type safe.
 *  Use with caution.
 *
 *  @throws NoSuchElementException if the iterable is too short.
 *  @throws IndexOutOfBoundsException if the iterable is too long.
 */
fun <A, B, C, D, E, F> Iterable<*>.toSextuple(): Sextuple<A, B, C, D, E, F> {
    val iterator = this.iterator()

    val res = Sextuple(iterator.next() as A, iterator.next() as B, iterator.next() as C, iterator.next() as D, iterator.next() as E, iterator.next() as F)
    if (iterator.hasNext()) throw IndexOutOfBoundsException("Iterable is too long")
    return res
}

/**
 *  Unpack an iterable into a septuple.
 *  This function uses unchecked casts, so it is not type safe.
 *  Use with caution.
 *
 *  @throws NoSuchElementException if the iterable is too short.
 *  @throws IndexOutOfBoundsException if the iterable is too long.
 */
fun <A, B, C, D, E, F, G> Iterable<*>.toSeptuple(): Septuple<A, B, C, D, E, F, G> {
    val iterator = this.iterator()

    val res = Septuple(iterator.next() as A, iterator.next() as B, iterator.next() as C, iterator.next() as D, iterator.next() as E, iterator.next() as F, iterator.next() as G)
    if (iterator.hasNext()) throw IndexOutOfBoundsException("Iterable is too long")
    return res
}

/**
 *  Unpack an iterable into an octuple.
 *  This function uses unchecked casts, so it is not type safe.
 *  Use with caution.
 *
 *  @throws NoSuchElementException if the iterable is too short.
 *  @throws IndexOutOfBoundsException if the iterable is too long.
 */
fun <A, B, C, D, E, F, G, H> Iterable<*>.toOctuple(): Octuple<A, B, C, D, E, F, G, H> {
    val iterator = this.iterator()

    val res = Octuple(iterator.next() as A, iterator.next() as B, iterator.next() as C, iterator.next() as D, iterator.next() as E, iterator.next() as F, iterator.next() as G, iterator.next() as H)
    if (iterator.hasNext()) throw IndexOutOfBoundsException("Iterable is too long")
    return res
}

/**
 *  Unpack an iterable into a nonuple.
 *  This function uses unchecked casts, so it is not type safe.
 *  Use with caution.
 *
 *  @throws NoSuchElementException if the iterable is too short.
 *  @throws IndexOutOfBoundsException if the iterable is too long.
 */
fun <A, B, C, D, E, F, G, H, I> Iterable<*>.toNonuple(): Nonuple<A, B, C, D, E, F, G, H, I> {
    val iterator = this.iterator()

    val res = Nonuple(iterator.next() as A, iterator.next() as B, iterator.next() as C, iterator.next() as D, iterator.next() as E, iterator.next() as F, iterator.next() as G, iterator.next() as H, iterator.next() as I)
    if (iterator.hasNext()) throw IndexOutOfBoundsException("Iterable is too long")
    return res
}

/**
 *  Unpack an iterable into a decuple.
 *  This function uses unchecked casts, so it is not type safe.
 *  Use with caution.
 *
 *  @throws NoSuchElementException if the iterable is too short.
 *  @throws IndexOutOfBoundsException if the iterable is too long.
 */
fun <A, B, C, D, E, F, G, H, I, J> Iterable<*>.toDecuple(): Decuple<A, B, C, D, E, F, G, H, I, J> {
    val iterator = this.iterator()

    val res = Decuple(iterator.next() as A, iterator.next() as B, iterator.next() as C, iterator.next() as D, iterator.next() as E, iterator.next() as F, iterator.next() as G, iterator.next() as H, iterator.next() as I, iterator.next() as J)
    if (iterator.hasNext()) throw IndexOutOfBoundsException("Iterable is too long")
    return res
}
