package binarySearch

import shouldBe
import kotlin.math.nextDown
import kotlin.math.nextUp
import kotlin.random.Random
import kotlin.test.Test

class DoubleOrdinalsTest {
    @Test
    fun testSpecialValues() {
        val tests = listOf(
            Double.NEGATIVE_INFINITY to DoubleOrdinals.ordinalRange.start,
            Double.POSITIVE_INFINITY to DoubleOrdinals.ordinalRange.endInclusive,
            -Double.MAX_VALUE to DoubleOrdinals.ordinalRange.start + 1,
            Double.MAX_VALUE to DoubleOrdinals.ordinalRange.endInclusive - 1,
            -Double.MIN_VALUE to -2L,
            (-0.0).nextDown() to -2L,
            -0.0 to -1L,
            0.0 to 0L,
            0.0.nextUp() to 1L,
            Double.NaN to DoubleOrdinals.CANONICAL_NAN_VALUE
        )

        tests.forEach { (dbl, expOrdinal) ->
            DoubleOrdinals.doubleToOrdinal(dbl) shouldBe expOrdinal
            DoubleOrdinals.ordinalToDouble(expOrdinal) shouldBe dbl
        }
    }

    /**
     * Randomly test possible double values. Test 2**31 different values.
     * Test Double - Ordinal - Double.
     */
    @Test
    fun testRandomly() {
        repeat(Int.MAX_VALUE) {
            val dbl = Random.nextDouble(-1e10, 1e10)
            val ordinal = DoubleOrdinals.doubleToOrdinal(dbl)
            val dbl2 = DoubleOrdinals.ordinalToDouble(ordinal)
            dbl shouldBe dbl2
        }
    }

    /**
     * Randomly test possible double values. Test 2**32 different values.
     * Test Ordinal - Double - Ordinal
     */
    @Test
    fun testRandomlyOrdinal() {
        repeat(Int.MAX_VALUE) {
            val ordinal = DoubleOrdinals.ordinalRange.random()
            val dbl = DoubleOrdinals.ordinalToDouble(ordinal)
            val ordinal2 = DoubleOrdinals.doubleToOrdinal(dbl)
            ordinal shouldBe ordinal2
        }
    }

    @Test
    fun testRanges() {
        DoubleOrdinals.finiteOrdinalRange.start shouldBe DoubleOrdinals.ordinalRange.start + 1
        DoubleOrdinals.finiteOrdinalRange.endInclusive shouldBe DoubleOrdinals.ordinalRange.endInclusive - 1
    }

}