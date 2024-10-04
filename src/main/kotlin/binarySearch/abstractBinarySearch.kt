package binarySearch

import numbertools.mean
import numbertools.sign
import numbertools.Sign.*

/**
 * binarySearch.binarySearch.AbstractBinarySearch is a class that provides a binary search algorithm to find a root of a semi-monotonically increasing function.
 */
abstract class AbstractBinarySearch<T : Comparable<T>> {
    /**
     *  Of the sign-semi-monotonously increasing function [goalFunction], this function returns
     *  the biggest `x` in the interval [start, end] with `goalFunction(x) <= 0`.
     *
     *  [goalFunction] must be sign-semi-monotonously increasing: for all x, y in [start, end] with x < y, sign(goalFunction(x)) <= sign(goalFunction(y)).
     *
     *  Returns `null` iff:
     *  * `start > end`, or
     *  * the function is `NaN` for a queried number, or
     *  * the function is shown to be not semi-monotonically increasing.
     */
    fun binarySearchRoot(start: T, end: T, goalFunction: (T) -> Number): T? {
        if (start > end) return null

        var low = start
        var high = end


        when (goalFunction(start).sign) {
            Positive, NaN -> return null
            Zero, Negative -> {}
        }

        when (goalFunction(end).sign) {
            Positive -> {}
            Zero -> return high
            Negative, NaN -> return null
        }

        // Remember: x:= largest value with goalFunction(x) <= 0
        // now true: low <= x < high

        while (true) {
            // invariant: low <= x < high
            val mean = mean(low, high)
            if (mean == low || mean == high) break // no more space between low and high

            val fmean = goalFunction(mean)

            when (fmean.sign) {
                Positive -> high = mean
                Zero, Negative -> low = mean
                NaN -> return null
            }
            // invariant still true: low <= x < high
        }

        // still true: low <= x < high, but since high == next(low)
        // this gets: low <= x < next(low)
        return low
    }

    /**
     *  Returns the mean of low and high, low <= high. Must work even if (low + high) / 2 overflows.
     *  If the real value of (low + high) / 2 is not representable, the result should be the neighboring value closer to 0.
     */
    abstract fun mean(low: T, high: T): T
}

object LongBinarySearch : AbstractBinarySearch<Long>() {
    override fun mean(low: Long, high: Long) = low mean high
}

object IntBinarySearch : AbstractBinarySearch<Int>() {
    override fun mean(low: Int, high: Int) = low mean high
}



