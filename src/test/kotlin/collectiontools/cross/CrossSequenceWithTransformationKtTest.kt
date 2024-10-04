package collectiontools.cross

import org.junit.jupiter.api.Test
import shouldBeSequence
import kotlin.math.pow

class CrossSequenceWithTransformationKtTest {

    private val GOAL_TOTAL_SIZE = 50_000

    private fun createSequence(totalSequences: Int): Sequence<Int> {
        val maxSize = GOAL_TOTAL_SIZE.toDouble().pow(1.0 / totalSequences).toInt()
        val size = (1 .. maxSize).random()
        return (0 until size).asSequence().map { it - it * it * it * it } // must be deterministic!!!
    }

    @Test
    fun testCrossWith() {
        repeat(REPETITIONS) {
            val l1 = createSequence(2)
            val l2 = createSequence(2)

            l1.crossWith(l2) { a, b -> a + 2 * b } shouldBeSequence crossN(l1, l2).map { (it[0] + 2 * it[1])}
        }
    }

    @Test
    fun testCross2() {
        repeat(REPETITIONS) {
            val l1 = createSequence(2)
            val l2 = createSequence(2)

            cross(l1, l2) { a, b -> a + 2 * b } shouldBeSequence crossN(l1, l2).map { (it[0] + 2 * it[1])}
        }
    }

    @Test
    fun testCross3() {
        repeat(REPETITIONS) {
            val l1 = createSequence(3)
            val l2 = createSequence(3)
            val l3 = createSequence(3)

            cross(l1, l2, l3) { a, b, c -> a + 2 * b + 3 * c } shouldBeSequence crossN(l1, l2, l3).map { (it[0] + 2 * it[1] + 3 * it[2])}
        }
    }

    @Test
    fun testCross4() {
        repeat(REPETITIONS) {
            val l1 = createSequence(4)
            val l2 = createSequence(4)
            val l3 = createSequence(4)
            val l4 = createSequence(4)

            cross(l1, l2, l3, l4) { a, b, c, d -> a + 2 * b + 3 * c + 4 * d } shouldBeSequence
                    crossN(l1, l2, l3, l4).map { (it[0] + 2 * it[1] + 3 * it[2] + 4 * it[3])}
        }
    }

    @Test
    fun testCross5() {
        repeat(REPETITIONS) {
            val l1 = createSequence(5)
            val l2 = createSequence(5)
            val l3 = createSequence(5)
            val l4 = createSequence(5)
            val l5 = createSequence(5)

            cross(l1, l2, l3, l4, l5) { a, b, c, d, e -> a + 2 * b + 3 * c + 4 * d + 5 * e } shouldBeSequence
                    crossN(l1, l2, l3, l4, l5).map { (it[0] + 2 * it[1] + 3 * it[2] + 4 * it[3] + 5 * it[4])}
        }
    }

    @Test
    fun testCross6() {
        repeat(REPETITIONS) {
            val l1 = createSequence(6)
            val l2 = createSequence(6)
            val l3 = createSequence(6)
            val l4 = createSequence(6)
            val l5 = createSequence(6)
            val l6 = createSequence(6)

            cross(l1, l2, l3, l4, l5, l6) { a, b, c, d, e, f -> a + 2 * b + 3 * c + 4 * d + 5 * e + 6 * f } shouldBeSequence
                    crossN(l1, l2, l3, l4, l5, l6).map { (it[0] + 2 * it[1] + 3 * it[2] + 4 * it[3] + 5 * it[4] + 6 * it[5])}
        }
    }

    @Test
    fun testCross7() {
        repeat(REPETITIONS) {
            val l1 = createSequence(7)
            val l2 = createSequence(7)
            val l3 = createSequence(7)
            val l4 = createSequence(7)
            val l5 = createSequence(7)
            val l6 = createSequence(7)
            val l7 = createSequence(7)

            cross(l1, l2, l3, l4, l5, l6, l7) { a, b, c, d, e, f, g -> a + 2 * b + 3 * c + 4 * d + 5 * e + 6 * f + 7 * g } shouldBeSequence
                    crossN(l1, l2, l3, l4, l5, l6, l7).map { (it[0] + 2 * it[1] + 3 * it[2] + 4 * it[3] + 5 * it[4] + 6 * it[5] + 7 * it[6])}
        }
    }

    @Test
    fun testCross8() {
        repeat(REPETITIONS) {
            val l1 = createSequence(8)
            val l2 = createSequence(8)
            val l3 = createSequence(8)
            val l4 = createSequence(8)
            val l5 = createSequence(8)
            val l6 = createSequence(8)
            val l7 = createSequence(8)
            val l8 = createSequence(8)

            cross(l1, l2, l3, l4, l5, l6, l7, l8) { a, b, c, d, e, f, g, h -> a + 2 * b + 3 * c + 4 * d + 5 * e + 6 * f + 7 * g + 8 * h } shouldBeSequence
                    crossN(l1, l2, l3, l4, l5, l6, l7, l8).map { (it[0] + 2 * it[1] + 3 * it[2] + 4 * it[3] + 5 * it[4] + 6 * it[5] + 7 * it[6] + 8 * it[7])}
        }
    }

    @Test
    fun testCross9() {
        repeat(REPETITIONS) {
            val l1 = createSequence(9)
            val l2 = createSequence(9)
            val l3 = createSequence(9)
            val l4 = createSequence(9)
            val l5 = createSequence(9)
            val l6 = createSequence(9)
            val l7 = createSequence(9)
            val l8 = createSequence(9)
            val l9 = createSequence(9)

            cross(l1, l2, l3, l4, l5, l6, l7, l8, l9) { a, b, c, d, e, f, g, h, i -> a + 2 * b + 3 * c + 4 * d + 5 * e + 6 * f + 7 * g + 8 * h + 9 * i } shouldBeSequence
                    crossN(l1, l2, l3, l4, l5, l6, l7, l8, l9).map { (it[0] + 2 * it[1] + 3 * it[2] + 4 * it[3] + 5 * it[4] + 6 * it[5] + 7 * it[6] + 8 * it[7] + 9 * it[8])}
        }
    }

    @Test
    fun testCross10() {
        repeat(REPETITIONS) {
            val l1 = createSequence(10)
            val l2 = createSequence(10)
            val l3 = createSequence(10)
            val l4 = createSequence(10)
            val l5 = createSequence(10)
            val l6 = createSequence(10)
            val l7 = createSequence(10)
            val l8 = createSequence(10)
            val l9 = createSequence(10)
            val l10 = createSequence(10)

            cross(l1, l2, l3, l4, l5, l6, l7, l8, l9, l10) { a, b, c, d, e, f, g, h, i, j -> a + 2 * b + 3 * c + 4 * d + 5 * e + 6 * f + 7 * g + 8 * h + 9 * i + 10 * j } shouldBeSequence
                    crossN(l1, l2, l3, l4, l5, l6, l7, l8, l9, l10).map { (it[0] + 2 * it[1] + 3 * it[2] + 4 * it[3] + 5 * it[4] + 6 * it[5] + 7 * it[6] + 8 * it[7] + 9 * it[8] + 10 * it[9])}
        }
    }

    companion object {
        const val REPETITIONS: Int = 10_000
    }
}