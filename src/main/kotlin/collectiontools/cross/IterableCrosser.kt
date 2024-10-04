package collectiontools.cross

/**
 * Crosses multiple iterables, returning the cartesian product of their elements.
 * The output order depends on the types of iterables.
 * It is *not* required that the iterables are out-of-the-box iterable multiple times or that the iteration order is preserved over multiple passes.
 * However, it is necessary that the content of the iterable does not change.
 *
 * The crossing result is semi-stable in the following sense:
 * The last iterable is iterated over first, the first iterable is iterated over last.
 * Each time the (n+1)-th iterable is exhausted, it is reset and the n-th iterable is advanced by one.
 *
 * If the following sequences are crossed:
 * ```
 * 1 2 3
 * 4 5
 * ```
 *
 * The result would be:
 *
 * `(1, 4), (1, 5), (2, 4), (2, 5), (3, 4), (3, 5)`.
 *
 * If the sequences are instable, the result could be:
 * `(1, (4 or 5)), (2, (the remaining number)), (3, (4 or 5)), (1, (the remaining number)), (2, (4 or 5)), (3, (the remaining number))`.
 *
 * However, as said, the result is always some ordering of the cartesian product of the iterables.
 *
 * @property outputTransformation A function that transforms the current values of the iterables into the output type. Can (should) be used to
 *                                transform the list into strongly-typed n-uples.
 */
@Suppress("DuplicatedCode")
class IterableCrosser<T, O>(
    iterables: List<Iterable<T>>,
    private val outputTransformation: (List<T>) -> O
) : Iterable<O> {
    constructor(vararg iterables: Iterable<T>, outputTransformation: (List<T>) -> O) : this(iterables.toList(), outputTransformation)

    private val iterators = iterables.mapTo(ArrayList()) { ResettableIterator(it) }
    private val currentValues = run {
        iterables.indices.mapTo(ArrayList(iterables.size)) { if (iterators[it].hasNext()) iterators[it].next() else return@run null }
    }

    private data class ResettableIterator<out T>(val iterable: Iterable<T>) : Iterator<T> {
        private var backingIterator = iterable.iterator()
        override fun hasNext() = backingIterator.hasNext()
        override fun next() = backingIterator.next()
        fun reset() {
            backingIterator = iterable.iterator()
        }
    }

    private fun next(): O? {
        if (currentValues == null) return null

        for ((index, iter) in iterators.asReversed().withIndex()) {
            if (!iter.hasNext()) { // reset this iterator
                iter.reset()
                currentValues[currentValues.size - 1 - index] = iter.next()
            } else {
                currentValues[currentValues.size - 1 - index] = iter.next()
                return outputTransformation(currentValues)
            }
        }
        return null
    }

    override fun iterator(): Iterator<O> = if (currentValues == null)
        emptySequence<O>().iterator()
    else
        generateSequence(outputTransformation(currentValues)) { next() }.iterator()
}



