package collectiontools.cross

/**
 * Crosses multiple sequences, returning the cartesian product of their elements.
 * The output order depends on the types of sequences.
 * It is *not* required that the sequences are out-of-the-box sequence multiple times or that the iteration order is preserved over multiple passes.
 * However, it is necessary that the content of the sequence does not change.
 *
 * The crossing result is semi-stable in the following sense:
 * The last sequence is iterated over first, the first sequence is iterated over last.
 * Each time the (n+1)-th sequence is exhausted, it is reset and the n-th sequence is advanced by one.
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
 * However, as said, the result is always some ordering of the cartesian product of the sequences.
 *
 * @property outputTransformation A function that transforms the current values of the sequences into the output type. Can (should) be used to
 *                                transform the list into strongly-typed n-uples.
 */
@Suppress("DuplicatedCode")
class SequenceCrosser<T, O>(
    sequences: List<Sequence<T>>,
    private val outputTransformation: (List<T>) -> O
) : Sequence<O> {
    constructor(vararg sequences: Sequence<T>, outputTransformation: (List<T>) -> O) : this(sequences.toList(), outputTransformation)

    private val iterators = sequences.mapTo(ArrayList()) { ResettableIterator(it) }
    private val currentValues = run {
        sequences.indices.mapTo(ArrayList(sequences.size)) { if (iterators[it].hasNext()) iterators[it].next() else return@run null }
    }

    private data class ResettableIterator<out T>(val sequence: Sequence<T>) : Iterator<T> {
        private var backingIterator = sequence.iterator()
        override fun hasNext() = backingIterator.hasNext()
        override fun next() = backingIterator.next()
        fun reset() {
            backingIterator = sequence.iterator()
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



