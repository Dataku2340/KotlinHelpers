package binarySearch

import shouldBe
import org.junit.jupiter.api.Test

import kotlin.math.nextDown
import kotlin.math.nextUp
import kotlin.random.Random

class BinarySearchKtTest {

    @Test
    fun floatBinarySearch() {

        // 0..1
        repeat(100) {
            val f = Random.nextFloat()
            (-Float.MAX_VALUE..Float.MAX_VALUE).binarySearch { it - f } shouldBe f
            (0.0f..1.0f).binarySearch { it - f } shouldBe f
        }

        // Any finite float
        repeat(100) {
            val f = FloatOrdinals.ordinalToFloat(FloatOrdinals.ordinalRange.random())
            (-Float.MAX_VALUE..Float.MAX_VALUE).binarySearch { it - f } shouldBe f
            (0.0f..1.0f).binarySearch { it - f } shouldBe if (f in 0.0..1.0) f else null
        }

        val somef = 0.0523934523452f
        (0.0f .. 10.0f).binarySearch {
            when  {
                it <= somef.nextDown() -> -1.0
                it <= somef -> 0.0
                else -> 1.0
            }
        } shouldBe somef

        val somef2 = 0.1234567890412f
        (0.0f .. 10.0f).binarySearch {
            when  {
                it <= somef2 -> -1.0
                it <= somef2.nextUp().nextUp().nextUp().nextUp().nextUp() -> 0.0
                else -> 1.0
            }
        } shouldBe somef2.nextUp().nextUp().nextUp().nextUp().nextUp()

        (0.0f..Float.POSITIVE_INFINITY).binarySearch { if (it == Float.POSITIVE_INFINITY) 1.0 else 0.0 } shouldBe Float.MAX_VALUE
        (0.0f..Float.NaN).binarySearch { if (it.isInfinite()) 1.0 else 0.0 } shouldBe null

        (0.0f .. 10.0f).binarySearch {
            when  {
                it <= 2.0 -> -1.0f
                it <= 6.0 -> 0.0f
                else -> 1.0f
            }
        } shouldBe 6.0f
    }

    @Test
    fun doubleBinarySearch() {

        // 0..1
        repeat(100) {
            val d = Random.nextDouble()
            (-Double.MAX_VALUE..Double.MAX_VALUE).binarySearch { it - d } shouldBe d
            (0.0..1.0).binarySearch { it - d } shouldBe d
        }

        // Any finite double
        repeat(100) {
            val d = DoubleOrdinals.ordinalToDouble(DoubleOrdinals.ordinalRange.random())
            (-Double.MAX_VALUE..Double.MAX_VALUE).binarySearch { it - d } shouldBe d
            (0.0..1.0).binarySearch { it - d } shouldBe if (d in 0.0..1.0) d else null
        }

        val somed = 0.0523934523452
        (0.0 .. 10.0).binarySearch {
            when  {
                it <= somed.nextDown() -> -1.0
                it <= somed -> 0.0
                else -> 1.0
            }
        } shouldBe somed

        val somed2 = 0.1234567890412
        (0.0 .. 10.0).binarySearch {
            when  {
                it <= somed2 -> -1.0
                it <= somed2.nextUp().nextUp().nextUp().nextUp().nextUp() -> 0.0
                else -> 1.0
            }
        } shouldBe somed2.nextUp().nextUp().nextUp().nextUp().nextUp()

        (0.0..Double.POSITIVE_INFINITY).binarySearch { if (it == Double.POSITIVE_INFINITY) 1.0 else 0.0 } shouldBe Double.MAX_VALUE
        (0.0..Double.NaN).binarySearch { if (it.isInfinite()) 1.0 else 0.0 } shouldBe null

        (0.0 .. 10.0).binarySearch {
            when  {
                it <= 2.0 -> -1.0
                it <= 6.0 -> 0.0
                else -> 1.0
            }
        } shouldBe 6.0


    }

    @Test
    fun intBinarySearch() {
        (1 .. 0).binarySearch { 0 } shouldBe null
        (0 .. 1).binarySearch { 3 } shouldBe null
        (-10000 .. 10000).binarySearch { it * it } shouldBe null
        (0 .. 10000).binarySearch { it - 1234 } shouldBe 1234

        (0 .. 10).binarySearch { it % 11 } shouldBe 0
        (0 .. 10).binarySearch { it % 2 } shouldBe 10 // not semi-monotonous, but never queried
        (0 .. 5).binarySearch {
            when (it) {
                0 -> -1
                1 -> 0
                2 -> 1
                3 -> -1
                4 -> 1
                5 -> -1
                else -> 1
            }
        } shouldBe null // not semi-monotonous, but never queried
        (0 .. 10).binarySearch { if (it == 7) -20 else it - 3 } shouldBe 3 // not semi-monotonous, but never queried
        (0 .. 10).binarySearch { 0 } shouldBe 10
        (0 .. 5).binarySearch {
            when (it) {
                0 -> -1
                1 -> 0
                2 -> 0
                3 -> 0
                4 -> 0
                5 -> 1
                else -> 1
            }
        } shouldBe 4
    }

    @Test
    fun longBinarySearch() {
        (1L .. 0L).binarySearch { 0.0 } shouldBe null
        (0L .. 1L).binarySearch { 3.0 } shouldBe null
        (-10000L .. 10000L).binarySearch { it * it } shouldBe null
        (0L .. 10000L).binarySearch { it - 1234.0 } shouldBe 1234L

        (0L .. 10L).binarySearch { it % 11.0 } shouldBe 0L
        (0L .. 10L).binarySearch { it % 2.0 } shouldBe 10L // not semi-monotonous, but never queried
        (0L .. 5L).binarySearch {
            when (it) {
                0L -> -1L
                1L -> 0L
                2L -> 1L
                3L -> -1L
                4L -> 1L
                5L -> -1L
                else -> 1L
            }
        } shouldBe null // not semi-monotonous, but never queried
        (0L .. 10L).binarySearch { if (it == 7L) -20.0 else it - 3L } shouldBe 3L // not semi-monotonous, but never queried
        (0L .. 10L).binarySearch { 0.0 } shouldBe 10L
        (0L .. 5L).binarySearch {
            when (it) {
                0L -> -1L
                1L -> 0L
                2L -> 0L
                3L -> 0L
                4L -> 0L
                5L -> 1L
                else -> 1L
            }
        } shouldBe 4L
    }


}