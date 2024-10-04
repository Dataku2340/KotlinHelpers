package collectiontools

/**
 * A grid is a hyper-rectangle in n-dimensional space.
 * Each dimension has a fixed number of points of equal distance.
 * The grid is defined by the number of points in each dimension.
 * A grid may be complete (collectiontools.cross.cross-product of the points in each dimension) or incomplete (any subset of the complete grid).
 */
interface Grid : Iterable<List<Int>> {
    val isEmpty: Boolean
    fun asSequence(): Sequence<List<Int>>
}

/**
 * A grid with a fixed number of points in each dimension.
 * Each dimension has a fixed number of points of equal distance.
 * Different dimensions can have different number of points.
 * The grid is complete, i.e. there are no gaps.
 */
class RectangleGrid(counts: List<Int>) : Grid {
    constructor(vararg counts: Int) : this(counts.toList())

    private val sizes: List<Int> = ArrayList(counts)

    // empty if there is a dimension with 0 points or there is no dimension
    override val isEmpty = counts.isEmpty() || counts.any { it <= 0 }

    override fun asSequence() = if (isEmpty) emptySequence() else GridIteration().sequence()

    override fun iterator() = asSequence().iterator()

    private inner class GridIteration {
        private val currentGridPoint = Array(sizes.size) { 0 } // all 0

        private fun next(): List<Int>? {
            var currentIndex = sizes.lastIndex

            currentGridPoint[currentIndex] += 1

            // overflow? If yes set next digit
            while (currentGridPoint[currentIndex] >= sizes[currentIndex]) {
                currentGridPoint[currentIndex] = 0

                if (currentIndex == 0) // back at 0-0-…-0-0, therefore we are at the end
                    return null

                currentIndex -= 1
                currentGridPoint[currentIndex] += 1
            }

            return currentGridPoint.toList()
        }

        fun sequence() = generateSequence(currentGridPoint.toList()) { next() }
    }
}

/**
 * A grid with a fixed number of points in each dimension.
 * All dimensions have the same number of points.
 * Depending on the given [Uniqueness], the grid may be complete or incomplete.
 * For all uniqueness types, each grid point is computed in `O(count)` time, i.e. much faster than just
 * using a full grid and using .filter{} to achieve the same result.
 *
 * The following [Uniqueness] types are supported:
 * - [Uniqueness.NOT_UNIQUE]: This returns the complete grid.
 *   ```
 *   SquareGrid(base, count, Uniqueness.NOT_UNIQUE)
 *
 *   // E.g.: base = 3, count = 2:
 *   // (0, 0), (0, 1), (0, 2), (1, 0), (1, 1), (1, 2), (2, 0), (2, 1), (2, 2)
 *   ```
 *
 * - [Uniqueness.UNIQUE]: This returns only unique grid points, if the grid points are seen as a set.
 *   This is -- up to order -- equivalent to the following code, but more efficient:
 *   ```
 *   SquareGrid(base, count, Uniqueness.NOT_UNIQUE).asSequence().distinctBy { it.sorted() }
 *
 *   // E.g.: base = 3, count = 2:
 *   // (0, 0), (0, 1), (0, 2), (1, 1), (1, 2), (2, 2)
 *   ```
 *
 * - [Uniqueness.UNIQUE_NO_REPLACEMENT]: This returns only unique grid points, if the grid points are seen as a set and no grid point may contain the
 *   same value twice.
 *   This is -- up to order -- equivalent to the following code, but more efficient:
 *   ```kotlin
 *   SquareGrid(base, count, Uniqueness.NOT_UNIQUE).asSequence().distinctBy { it.sorted() }.filter { it.size == it.toSet().size }
 *
 *   // E.g.: base = 3, count = 2:
 *   // (0, 1), (0, 2), (1, 2)
 *   ```
 */
class SquareGrid(val base: Int, val count: Int, val uniqueness: Uniqueness) : Grid {
    enum class Uniqueness {
        NOT_UNIQUE, UNIQUE, UNIQUE_NO_REPLACEMENT
    }

    // empty if there is a dimension with 0 points or if it is impossible to create a unique grid point.
    override val isEmpty = count <= 0 || base <= 0 || (uniqueness == Uniqueness.UNIQUE_NO_REPLACEMENT && base < count)

    override fun asSequence() = if (isEmpty) emptySequence() else GridIteration().sequence()

    override fun iterator() = asSequence().iterator()

    private inner class GridIteration {
        private val currentGridPoint: ArrayList<Int> = when (uniqueness) {
            Uniqueness.NOT_UNIQUE, Uniqueness.UNIQUE -> (0 ..< count).mapTo(ArrayList(count)) { 0 } // all 0
            // 0, 1, 2, …. Can be illegal if for an i: sizes[i] <= i. Since UNIQUE_AND_DIFFERENT is only used if all sizes are equal, this means count > size
            Uniqueness.UNIQUE_NO_REPLACEMENT -> (0 ..< count).mapTo(ArrayList(count)) { it }
        }

        private fun next(): List<Int>? {
            // find start index
            val startIndex = when (uniqueness) {
                // find the index of the first dimension that can be increased. If there is none, the grid iteration finished, return null then.
                Uniqueness.NOT_UNIQUE, Uniqueness.UNIQUE -> (count - 1 downTo 0)
                    .firstOrNull { startIndex -> (currentGridPoint[startIndex] + 1) < base }

                // Get the last index usable as the starting index.
                // As all grid points after the starting index initially are set to
                // currentGridPoint[i] = currentGridPoint[startIndex] + (i - startIndex)
                // which must be true for all i in (startIndex + 1)..(count - 1).
                // Since the condition is monotonic, we must only check for i = count - 1.
                // and as currentGridPoint[i] < base must be satisfied, we get the following condition for valid start indices.
                // of all valid start indices, we pick the last.
                // If there is no such index, we can just return null as there is no remaining element in this grid.
                Uniqueness.UNIQUE_NO_REPLACEMENT -> (count - 1 downTo 0)
                    .firstOrNull { startIndex -> (currentGridPoint[startIndex] + 1) + ((count - 1) - startIndex) < base }
            } ?: return null

            // increment the point at the selected index. We know that currentGridPoint[startIndex] + 1 < base.
            currentGridPoint[startIndex] += 1

            // set all grid points after the starting index to their correct value
            ((startIndex + 1) ..< count).forEach {
                currentGridPoint[it] = when (uniqueness) {
                    Uniqueness.NOT_UNIQUE -> 0
                    Uniqueness.UNIQUE -> currentGridPoint[startIndex]
                    Uniqueness.UNIQUE_NO_REPLACEMENT -> currentGridPoint[startIndex] + (it - startIndex)
                }
            }

            return currentGridPoint.toList()
        }

        fun sequence() = generateSequence(currentGridPoint.toList()) { next() }
    }
}


