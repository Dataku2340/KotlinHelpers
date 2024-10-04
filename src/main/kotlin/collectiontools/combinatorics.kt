package collectiontools

import collectiontools.cross.crossSelf


/**
 * Returns a [List] implementing [RandomAccess] containing all elements of this [Iterable].
 * If this is already a [List] and implements [RandomAccess], it is returned as is.
 */
internal fun <T> Iterable<T>.asOrToRAList(): List<T> = when {
    this is List && this is RandomAccess -> this
    this is Collection<T> -> ArrayList(this)
    else -> ArrayList<T>().apply { addAll(this) }
}

/**
 * Returns all subsets of this collection.
 * If [includeEmptySet] is `true`, the result will contain the empty set.
 * If [includeOriginalSet] is `true`, the result will contain the original set.
 * The result is a sequence of sets.
 *
 * This function is equivalent to `this.select(0..this.size, false, false).map { it.toSet() }`; depending on [includeEmptySet] and [includeOriginalSet] the
 * range boundaries are adjusted by 1.
 */
fun <E> Collection<E>.subSets(includeEmptySet: Boolean = true, includeOriginalSet: Boolean = true): Sequence<Set<E>> {
    val base = ArrayList(if (this is Set) this else this.distinct())

    val indices = when {
        !includeEmptySet && !includeOriginalSet -> 1 ..< base.size
        includeEmptySet && !includeOriginalSet -> 0 ..< base.size
        !includeEmptySet && includeOriginalSet -> 1 .. base.size
        else -> 0 .. base.size
    }

    return base.select(indices, withReplacement = false, orderMatters = false).map { it.toSet() }
}


/**
 *  Select [selectionSize] elements from this collection.
 *  The result is a sequence of lists of selected elements.
 *  Even if `withReplacement` is `false`, the same element can be selected multiple times if it is present multiple times in this collection.
 *  If this unwanted, use `distinct()` before calling this function.
 *
 *   ## Parameters
 *   * [selectionSize] - the number of elements to select. [selectionSize] must be ` >= -this.size`.
 *              Negative values inside this range are treated as `this.size + selectionSize`.
 *              E.g., if `this.size == 5`, then `this.select(-1, ...)` is equivalent to `this.select(4, ...)`.
 *              Values outside this range throw an [IllegalArgumentException].
 *   * [withReplacement] - whether to allow the same **element** to be selected multiple times.
 *              **If the two equal elements are present in this collection, they are treated as distinct.**
 *              E.g., if `this == listOf(1, 1, 2, 2)`, then `this.select(2, withReplacement = false, ...)` the result will contain `[1, 1]`.
 *   * [orderMatters] - whether the order of the selected elements matters.
 *              E.g., if `this == listOf(1, 2)`, then `this.select(2, orderMatters = true, ...)` will contain `[1, 2]` and `[2, 1]`, while
 *              `this.select(2, orderMatters = false, ...)` will contain only `[1, 2]` or `[2, 1]`.
 *              If this collection is ordered, the result items will be in the same order as in this collection.
 *
 *   ## Edge cases
 *   The solution to the edge cases is mainly motivated by the following:
 *   `list.select(0..list.size, false, false).map { it.toSet() }` should return all subsets of `list`.
 *
 *   * [this] is empty and [selectionSize] is 0, returns a **sequence containing an empty list**.
 *   * [this] is empty and [selectionSize] is not 0, returns an **empty sequence**.
 *   * [this] is not empty and [selectionSize] is 0, returns a **sequence containing an empty list**.
 *
 *   ## Examples
 *   ```kotlin
 *   val list = listOf(1, 2, 3)
 *   list.select(2, withReplacement = false, orderMatters = false).toList() // [[1, 2], [1, 3], [2, 3]]
 *   list.select(2, withReplacement = false, orderMatters = true).toList() // [[1, 2], [2, 1], [1, 3], [3, 1], [2, 3], [3, 2]]
 *   list.select(2, withReplacement = true, orderMatters = false).toList() // [[1, 1], [1, 2], [1, 3], [2, 2], [2, 3], [3, 3]]
 *   list.select(2, withReplacement = true, orderMatters = true).toList() // [[1, 1], [1, 2], [1, 3], [2, 1], [2, 2], [2, 3], [3, 1], [3, 2], [3, 3]]
 *   ```
 *
 *   And, with duplicates:
 *   ```kotlin
 *   val list = listOf(1, 2, 1)
 *   list.select(2, withReplacement = false, orderMatters = false).toList() // [[1, 2], [1, 1], [2, 1]]
 *   list.select(2, withReplacement = false, orderMatters = true).toList() // [[1, 2], [2, 1], [1, 1], [1, 1], [2, 1], [1, 2]]
 *   list.select(2, withReplacement = true, orderMatters = false).toList() // [[1, 1], [1, 2], [1, 1], [2, 2], [2, 1], [1, 1]]
 *   list.select(2, withReplacement = true, orderMatters = true).toList() // [[1, 1], [1, 2], [1, 1], [2, 1], [2, 2], [2, 1], [1, 1], [1, 2], [1, 1]]
 *   ```
 */
fun <E> Collection<E>.select(selectionSize: Int, withReplacement: Boolean, orderMatters: Boolean): Sequence<List<E>> {
    require(selectionSize >= -this.size) { "Size must be in the range [-this.size, this.size]." }
    if (selectionSize == 0) return sequenceOf(emptyList())

    val size = if (selectionSize < 0) this.size + selectionSize else selectionSize

    if (withReplacement && orderMatters) {
        // this is just crossing. Whether using SquareGrid or not is best left for the specialized implementation.
        return this.crossSelf(size)
    }

    val base = this.asOrToRAList()
    if (base.isEmpty()) return emptySequence()

    return if (!withReplacement && !orderMatters) /* !withReplacement && !orderMatters */ {
        if (size > base.size) return emptySequence()

        SquareGrid(base = base.size, count = size, uniqueness = SquareGrid.Uniqueness.UNIQUE_NO_REPLACEMENT)
            .asSequence()
            .map { selectedIndices -> selectedIndices.mapTo(ArrayList()) { base[it] } }
    } else if (!withReplacement) /* !withReplacement && orderMatters */ {
        // Not directly implemented in SquareGrid. But we can use permutations of !withReplacement and !orderMatters.
        // This way, only the result items are computed; no filtering necessary.
        if (size > base.size) return emptySequence()
        if (size == base.size) return base.permutations()

        SquareGrid(base = base.size, count = size, uniqueness = SquareGrid.Uniqueness.UNIQUE_NO_REPLACEMENT)
            .asSequence()
            .map { selectedIndices -> selectedIndices.mapTo(ArrayList()) { base[it] } }
            .flatMap { it.permutations() }
    } else  /* withReplacement && !orderMatters */ {
        SquareGrid(base = base.size, count = size, uniqueness = SquareGrid.Uniqueness.UNIQUE)
            .asSequence()
            .map { selectedIndices -> selectedIndices.mapTo(ArrayList()) { base[it] } }
    }
}

/**
 * Allowing multiple selection sizes. The result is a sequence of lists of selected elements.
 * For more details, see [select].
 */
fun <E> Collection<E>.select(sizes: Iterable<Int>, withReplacement: Boolean, orderMatters: Boolean): Sequence<List<E>> =
    sizes.asSequence().flatMap { this.select(it, withReplacement, orderMatters) }

/**
 * Returns all combinations of size [size] from this collection.
 * This function is an alias for `this.select(size, withReplacement = false, orderMatters = false)`.
 */
fun <E> Collection<E>.combinations(size: Int): Sequence<List<E>> = this.select(size, withReplacement = false, orderMatters = false)

/**
 * Returns all permutations of size [size] from this collection.
 * This function is an alias for `this.select(size, withReplacement = false, orderMatters = true)`.
 */
fun <E> Collection<E>.permutations(size: Int): Sequence<List<E>> = this.select(size, withReplacement = false, orderMatters = true)

/**
 *  Returns all permutations of this collection.
 *  **The returned [List] will be changed in place**.
 */
internal fun <E> Collection<E>.permutationsInplace(): Sequence<List<E>> {
    val current = if (this is ArrayList) this else ArrayList(this)

    fun swap(from: Int, to: Int) {
        val tmp = current[from]
        current[from] = current[to]
        current[to] = tmp
    }

    return sequence {
        val c = IntArray(current.size)
        yield(current)

        var i = 1
        while (i < current.size) {
            if (c[i] < i) {
                if (i % 2 == 0) swap(0, i) else swap(c[i], i)
                yield(current)
                c[i] += 1
                i = 1
            } else {
                c[i] = 0
                i += 1
            }
        }
    }
}

/**
 * Returns all permutations of this collection.
 * This function is equivalent to `this.select(this.size, withReplacement = false, orderMatters = true)`.
 */
fun <E> Collection<E>.permutations(): Sequence<List<E>> {
    val current = if (this is ArrayList) this else ArrayList(this)

    fun swap(from: Int, to: Int) {
        val tmp = current[from]
        current[from] = current[to]
        current[to] = tmp
    }

    return sequence {
        val c = IntArray(current.size)
        yield(ArrayList(current))

        var i = 1
        while (i < current.size) {
            if (c[i] < i) {
                if (i % 2 == 0) swap(0, i) else swap(c[i], i)
                yield(ArrayList(current))
                c[i] += 1
                i = 1
            } else {
                c[i] = 0
                i += 1
            }
        }
    }
}
