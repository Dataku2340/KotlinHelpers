package binarySearch

/**
 *  Of the semi-monotonously increasing function [goalFunction], this function returns
 *  the biggest `x` in the interval [start, end] with `goalFunction(x) <= 0`.
 *
 *  [goalFunction] must be semi-monotonously increasing: for all x, y in [start, end] with x < y, goalFunction(x) <= goalFunction(y).
 *
 *  Returns `null` iff:
 *  * `start > end`, or
 *  * the function is `NaN` for a queried number, or
 *  * the function is shown to be not semi-monotonically increasing.
 */
fun ClosedRange<Float>.binarySearch(goalFunction: (Float) -> Number): Float? {
    if (start.isNaN() || endInclusive.isNaN()) return null

    val resultOrdinal = IntBinarySearch.binarySearchRoot(
        start = FloatOrdinals.floatToOrdinal(this.start),
        end = FloatOrdinals.floatToOrdinal(this.endInclusive),
        goalFunction = { goalFunction(FloatOrdinals.ordinalToFloat(it)) }
    ) ?: return null
    return FloatOrdinals.ordinalToFloat(resultOrdinal)
}

/**
 *  Of the semi-monotonously increasing function [goalFunction], this function returns
 *  the biggest `x` in the interval [start, end] with `goalFunction(x) <= 0`.
 *
 *  [goalFunction] must be semi-monotonously increasing: for all x, y in [start, end] with x < y, goalFunction(x) <= goalFunction(y).
 *
 *  Returns `null` iff:
 *  * `start > end`, or
 *  * the function is `NaN` for a queried number, or
 *  * the function is shown to be not semi-monotonically increasing.
 */
fun ClosedRange<Double>.binarySearch(goalFunction: (Double) -> Number): Double? {
    if (start.isNaN() || endInclusive.isNaN()) return null

    val resultOrdinal = LongBinarySearch.binarySearchRoot(
        start = DoubleOrdinals.doubleToOrdinal(this.start),
        end = DoubleOrdinals.doubleToOrdinal(this.endInclusive),
        goalFunction = {
            goalFunction(DoubleOrdinals.ordinalToDouble(it))
        }
    ) ?: return null
    return DoubleOrdinals.ordinalToDouble(resultOrdinal)
}

/**
 *  Of the semi-monotonously increasing function [goalFunction], this function returns
 *  the biggest `x` in the interval [start, end] with `goalFunction(x) <= 0`.
 *
 *  [goalFunction] must be semi-monotonously increasing: for all x, y in [start, end] with x < y, goalFunction(x) <= goalFunction(y).
 *
 *  Returns `null` iff:
 *  * `start > end`, or
 *  * the function is `NaN` for a queried number, or
 *  * the function is shown to be not semi-monotonically increasing.
 */
fun ClosedRange<Int>.binarySearch(goalFunction: (Int) -> Number) =
    IntBinarySearch.binarySearchRoot(this.start, this.endInclusive) { goalFunction(it) }

/**
 *  Of the semi-monotonously increasing function [goalFunction], this function returns
 *  the biggest `x` in the interval [start, end] with `goalFunction(x) <= 0`.
 *
 *  [goalFunction] must be semi-monotonously increasing: for all x, y in [start, end] with x < y, goalFunction(x) <= goalFunction(y).
 *
 *  Returns `null` iff:
 *  * `start > end`, or
 *  * the function is `NaN` for a queried number, or
 *  * the function is shown to be not semi-monotonically increasing.
 */
fun ClosedRange<Long>.binarySearch(goalFunction: (Long) -> Number) =
    LongBinarySearch.binarySearchRoot(this.start, this.endInclusive) { goalFunction(it) }

