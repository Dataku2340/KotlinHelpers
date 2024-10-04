package binarySearch

/**
 *  Converts floats into ordinal numbers that preserve the order of the floats.
 *  This is useful for binary search, because it allows us to compare floats without the need for floating point comparisons.
 *
 *  The ordinals are integers that are ordered in the same way as the floats; neighboring floats have neighboring ordinals.
 *
 *  NaNs are normalized to a single value [CANONICAL_NAN_VALUE], so that they can be compared.
 */
object FloatOrdinals {
    const val CANONICAL_NAN_VALUE = Int.MIN_VALUE
    private const val DIGITS_MASK = Int.MAX_VALUE //0x7fff_ffff
    private const val MIN_ORDINAL = -2139095041
    private const val MAX_ORDINAL = 2139095040

    /**
     * The range of all possible ordinals, excluding NaN.
     * Every ordinal in this range represents a float.
     *
     * Note that -0.0 and 0.0 are different values, so the range includes both.
     */
    val ordinalRange = MIN_ORDINAL .. MAX_ORDINAL

    /**
     * The range of all possible ordinals, excluding the special values -Infinity, +Infinity and NaN.
     * Every ordinal in this range represents a float.
     *
     * Note that -0.0 and 0.0 are different values, so the range includes both.
     */
    @Suppress("ReplaceRangeToWithRangeUntil")
    val finiteOrdinalRange = (MIN_ORDINAL + 1) .. (MAX_ORDINAL - 1)

    /**
     *  Converts floats into ordinal numbers that preserve the order of the floats.
     *  This is useful for binary search, because it allows us to compare floats without the need for floating point comparisons.
     *
     *  The ordinals are integers that are ordered in the same way as the floats; neighboring floats have neighboring ordinals.
     *
     *  Special cases:
     *  * -0.0 (ordinal -1) and 0.0 (ordinal 0) are treated different values.
     *  * NaNs are normalized to a single value [CANONICAL_NAN_VALUE], so that they can be compared.
     */
    fun floatToOrdinal(f: Float): Int {
        if (f.isNaN()) return CANONICAL_NAN_VALUE // normalize NaNs
        val raw = f.toRawBits()
        return if (raw >= 0) raw else (raw and DIGITS_MASK).inv()
    }

    /**
     *  Converts ordinal numbers back into the represented floats. See [floatToOrdinal] for more information.
     *  Any value outside of [ordinalRange] will be converted to [Float.NaN].
     */
    fun ordinalToFloat(ordinal: Int): Float {
        if (ordinal !in ordinalRange) return Float.NaN // all other values are NaNs.
        return if (ordinal >= 0)
            Float.fromBits(ordinal)
        else
            -Float.fromBits(ordinal.inv()) // to un-do the else, we just need to invert the bits again and then flip the first bit.
    }
}

/**
 *  Converts doubles into ordinal numbers that preserve the order of the doubles.
 *  This is useful for binary search, because it allows us to compare doubles without the need for floating point comparisons.
 *
 *  The ordinals are longs that are ordered in the same way as the doubles; neighboring doubles have neighboring ordinals.
 *
 *  NaNs are normalized to a single value [CANONICAL_NAN_VALUE], so that they can be compared.
 */
object DoubleOrdinals {
    const val CANONICAL_NAN_VALUE = Long.MIN_VALUE
    private const val DIGITS_MASK = Long.MAX_VALUE //0x7fff_ffff_ffff_ffff
    private const val MIN_ORDINAL = -9218868437227405313
    private const val MAX_ORDINAL = 9218868437227405312
    /**
     * The range of all possible ordinals, excluding NaN.
     * Every ordinal in this range represents a double.
     *
     * Note that -0.0 and 0.0 are different values, so the range includes both.
     */
    val ordinalRange = MIN_ORDINAL .. MAX_ORDINAL

    /**
     * The range of all possible ordinals, excluding the special values -Infinity, +Infinity and NaN.
     * Every ordinal in this range represents a double.
     *
     * Note that -0.0 and 0.0 are different values, so the range includes both.
     */
    @Suppress("ReplaceRangeToWithRangeUntil")
    val finiteOrdinalRange = (MIN_ORDINAL + 1) .. (MAX_ORDINAL - 1)

    /**
     *  Converts doubles into ordinal numbers that preserve the order of the doubles.
     *  This is useful for binary search, because it allows us to compare doubles without the need for floating point comparisons.
     *
     *  The ordinals are longs that are ordered in the same way as the doubles; neighboring doubles have neighboring ordinals.
     *
     *  Special cases:
     *  * -0.0 (ordinal -1) and 0.0 (ordinal 0) are treated different values.
     *  * NaNs are normalized to a single value [CANONICAL_NAN_VALUE], so that they can be compared.
     */
    fun doubleToOrdinal(f: Double): Long {
        if (f.isNaN()) return CANONICAL_NAN_VALUE // normalize NaNs
        val raw = f.toRawBits()
        return if (raw >= 0) raw else (raw and DIGITS_MASK).inv()
    }

    /**
     *  Converts ordinal numbers back into the represented doubles. See [doubleToOrdinal] for more information.
     *  Any value outside of [ordinalRange] will be converted to [Double.NaN].
     */
    fun ordinalToDouble(ordinal: Long): Double {
        if (ordinal !in ordinalRange) return Double.NaN // all other values are NaNs.
        return if (ordinal >= 0)
            Double.fromBits(ordinal)
        else
            -Double.fromBits(ordinal.inv()) // to un-do the else, we just need to invert the bits again and then flip the first bit.
    }
}
