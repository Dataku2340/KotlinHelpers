package iterabletools

import iterablesequencetools.*
import shouldBe
import org.junit.jupiter.api.Test

import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.test.assertEquals

class IterabletoolsKtTest {

    @Test
    fun countWithTotal() {
        repeat(1000_000) {
            val count = (0 .. 1000).random()
            val list = (0 ..< count).map { Random.nextInt() }
            val actual = list.countWithTotal { it % 2 == 0 }

            actual.second shouldBe list.size
            actual.first shouldBe list.count { it % 2 == 0 }
        }
    }

    @Test
    fun product() {
        listOf(3.0, 2.0, 1.0).product() shouldBe 6.0
        listOf(3.0, 0.0, 1.0).product() shouldBe 0.0
        listOf(3.0, 2.0, -1.0).product() shouldBe -6.0
    }

    @Test
    fun testProduct() {
        listOf(3, 2, 1).product() shouldBe 6
        listOf(3, 0, 1).product() shouldBe 0
        listOf(3, 2, -1).product() shouldBe -6
    }

    @Test
    fun productLong() {
        listOf(3, 2, 1).productLong() shouldBe 6L
        listOf(3, 0, 1).productLong() shouldBe 0L
        listOf(3, 2, -1).productLong() shouldBe -6L
    }

    @Test
    fun productOf() {
        listOf(3.0, 2.0, 1.0).productOf { it * 2 } shouldBe 48.0
        listOf(3.0, 0.0, 1.0).productOf { it * 2 }  shouldBe 0.0
        listOf(3.0, 2.0, -1.0).productOf { it * 2 } shouldBe -48.0
    }

    @Test
    fun testProductOf() {
        listOf(3, 2, 1).productOfInt { it * 2 } shouldBe 48
        listOf(3, 0, 1).productOfInt { it * 2 } shouldBe 0
        listOf(3, 2, -1).productOfInt { it * 2 } shouldBe -48
    }

    @Test
    fun testProductOf1() {
        listOf(3, 2, 1).productOfLong { it * 2L } shouldBe 48L
        listOf(3, 0, 1).productOfLong { it * 2L } shouldBe 0L
        listOf(3, 2, -1).productOfLong { it * 2L } shouldBe -48L
    }

    @Test
    fun averageOf() {
        repeat(10000) {
            val random = (0 .. 15).map { Random.nextDouble() }

            val actual = random.averageOf { it }
            val expected = random.sum() / random.size

            assertEquals(expected, actual, absoluteTolerance = 1e-10)
        }

        listOf<Double>().averageOf { it } shouldBe Double.NaN
    }

    @Test
    fun averageOfOrNull() {
        repeat(10000) {
            val random = (0 .. 15).map { Random.nextDouble() }

            val actual = random.averageOfOrNull { it }
            val expected = random.sum() / random.size

            assertEquals(expected, actual!!, absoluteTolerance = 1e-10)
        }

        listOf<Double>().averageOfOrNull { it } shouldBe null
    }

    @Test
    fun empStdError() {
        listOf(1.0, 2.0, 3.0, 4.0, 5.0).empStandardDev() shouldBe sqrt(10.0 / 4.0)
    }

    @Test
    fun empVariance() {
        listOf(1.0, 2.0, 3.0, 4.0, 5.0).empVariance() shouldBe (10.0 / 4.0)
    }

    @Test
    fun stdError() {
        listOf(1.0, 2.0, 3.0, 4.0, 5.0).standardDev() shouldBe sqrt(10.0 / 5.0)
    }

    @Test
    fun variance() {
        listOf(1.0, 2.0, 3.0, 4.0, 5.0).variance() shouldBe (10.0 / 5.0)
    }

    @Test
    fun valueCounts() {
        val list = listOf(1, 2, 3, 4, 5, 1, 2, 3, 4, 1, 2, 3,)
        val expected = mapOf(1 to 3, 2 to 3, 3 to 3, 4 to 2, 5 to 1)

        list.valueCounts() shouldBe expected
    }
}