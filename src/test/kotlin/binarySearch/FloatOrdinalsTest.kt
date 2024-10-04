package binarySearch

import shouldBe
import kotlin.math.nextDown
import kotlin.math.nextUp
import kotlin.test.Test

class FloatOrdinalsTest {

    @Test
    fun testSpecialValues() {
        val tests = listOf(
            Float.NEGATIVE_INFINITY to FloatOrdinals.ordinalRange.start,
            Float.POSITIVE_INFINITY to FloatOrdinals.ordinalRange.endInclusive,
            -Float.MAX_VALUE to FloatOrdinals.ordinalRange.start + 1,
            Float.MAX_VALUE to FloatOrdinals.ordinalRange.endInclusive - 1,
            -Float.MIN_VALUE to -2,
            (-0.0f).nextDown() to -2,
            -0.0f to -1,
            0.0f to 0,
            0.0f.nextUp() to 1,
            Float.NaN to FloatOrdinals.CANONICAL_NAN_VALUE
        )

        tests.forEach { (fl, expOrdinal) ->
            FloatOrdinals.floatToOrdinal(fl) shouldBe expOrdinal
            FloatOrdinals.ordinalToFloat(expOrdinal) shouldBe fl
        }
    }

    /**
     * Test all possible float values. A little less than 2**32.
     * Test Float - Ordinal - Float.
     */
    @Test
    fun testAllNonNAN() {
        var fl = Float.NEGATIVE_INFINITY
        var expected = FloatOrdinals.ordinalRange.start
        val negZeroBits = (-0.0f).toRawBits()

        while (fl != Float.POSITIVE_INFINITY) {
            val ordinal = FloatOrdinals.floatToOrdinal(fl)
            val fl2 = FloatOrdinals.ordinalToFloat(ordinal)
            fl shouldBe fl2
            ordinal shouldBe expected

            fl = if (fl.toRawBits() == negZeroBits) 0.0f else fl.nextUp() // nextup, treating -0.0 and 0.0 as different values.
            expected++
        }
    }

    /**
     * Test all possible ordinal values. Exactly 2**32.
     * Test Ordinal - Float - Ordinal
     */
    @Test
    fun testAllOrdinal() {
        (Int.MIN_VALUE..Int.MAX_VALUE)
            .forEach {
                if (it in FloatOrdinals.ordinalRange) {
                    val fl = FloatOrdinals.ordinalToFloat(it)
                    val ordinal = FloatOrdinals.floatToOrdinal(fl)
                    it shouldBe ordinal
                } else {
                    val fl = FloatOrdinals.ordinalToFloat(it)
                    fl shouldBe Float.NaN
                    val ordinal = FloatOrdinals.floatToOrdinal(fl)
                    ordinal shouldBe FloatOrdinals.CANONICAL_NAN_VALUE
                }
            }
    }

    @Test
    fun testRanges() {
        FloatOrdinals.finiteOrdinalRange.start shouldBe FloatOrdinals.ordinalRange.start + 1
        FloatOrdinals.finiteOrdinalRange.endInclusive shouldBe FloatOrdinals.ordinalRange.endInclusive - 1
    }

}