package collectiontools.cross

import collectiontools.cross.cross
import collectiontools.cross.crossN
import collectiontools.cross.crossSelf
import collectiontools.cross.crossWith
import nuples.toList
import shouldBeSequence
import org.junit.jupiter.api.Test

import kotlin.math.pow
import kotlin.random.Random

class CrossIterableNoTransformationKtTest {

    private val GOAL_TOTAL_SIZE = 50_000

    private fun createIterable(totalIterables: Int): List<Int> {
        val maxSize = GOAL_TOTAL_SIZE.toDouble().pow(1.0 / totalIterables).toInt()
        val size = (1 .. maxSize).random()
        return (0 until size).map { Random.nextInt() }
    }

    fun crossNFlatMap(vararg iterables: Iterable<Int>): Sequence<List<Int>> {
        return iterables
            .fold(sequenceOf(emptyList())) { acc, iterable ->
                acc.flatMap { list -> iterable.map { list + it } }
            }
    }

    @Test
    fun crossNEdgeCases() {
        /**
         * Crosses an arbitrary number of iterables (cartesian product).
         *
         * `collectiontools.cross.crossN()` is equivalent to `sequenceOf(emptyList())`.
         *
         * `collectiontools.cross.crossN(list) is equivalent to `list.map { listOf(it) }.asSequence()`.
         *
         * `collectiontools.cross.crossN(l1, l2, emptyList(), l3, l4)` is equivalent to `emptySequence()`.
         */
        crossN(*emptyArray<Iterable<*>>()) shouldBeSequence sequenceOf(emptyList())

        val list = createIterable(1)

        crossN(list) shouldBeSequence list.asSequence().map { listOf(it) }

        val l1 = createIterable(6)
        val l2 = createIterable(6)
        val l3 = createIterable(6)
        val l4 = createIterable(6)
        val l5 = createIterable(6)
        val l6 = createIterable(6)

        crossN(l1, l2, emptyList(), l3, l4, l5, l6) shouldBeSequence emptySequence()

    }

    @Test
    fun testCrossN() {
        // 1 - 10
        repeat(REPETITIONS / 10) {
            val number = (1 .. 10).random()

            val lists = (0 until number).map { createIterable(6) }.toTypedArray()

            crossNFlatMap(*lists) shouldBeSequence crossN(*lists)
        }
    }

    // All following tests just test against the collectiontools.cross.crossN function.

    @Test
    fun crossWithCount() {
        repeat(REPETITIONS) {
            val l1 = createIterable(6)
            val l2 = createIterable(6)
            val l3 = createIterable(6)

            collectiontools.cross.crossWithCount(l1 to 1, l2 to 1, l3 to 1).map { it.toList() } shouldBeSequence crossN(l1, l2, l3)
            collectiontools.cross.crossWithCount(l1 to 1, l2 to 1, l3 to 1, l1 to 2).map { it.toList() } shouldBeSequence crossN(l1, l2, l3, l1, l1)
            collectiontools.cross.crossWithCount(l1 to 3, l2 to 2, l3 to 1).map { it.toList() } shouldBeSequence crossN(l1, l1, l1, l2, l2, l3)
            collectiontools.cross.crossWithCount(l1 to 1, l2 to 0, l3 to 1).map { it.toList() } shouldBeSequence crossN(l1, l3)
        }
    }

    @Test
    fun testCrossWith() {
        repeat(REPETITIONS) {
            val l1 = createIterable(2)
            val l2 = createIterable(2)

            l1.crossWith(l2).map { it.toList() } shouldBeSequence crossN(l1, l2)
        }
    }

    @Test
    fun crossSelfEdgeCases() {
        repeat(REPETITIONS) {
            val l1 = createIterable(10)

            l1.crossSelf(0) shouldBeSequence sequenceOf(emptyList())
            l1.crossSelf(1) shouldBeSequence l1.asSequence().map { listOf(it) }
        }
    }

    @Test
    fun crossSelf() {
        repeat(REPETITIONS) {
            val l1 = createIterable(8)

            l1.crossSelf(2).map { it.toList() } shouldBeSequence crossN(l1, l1)
            l1.crossSelf(3).map { it.toList() } shouldBeSequence crossN(l1, l1, l1)
            l1.crossSelf(8).map { it.toList() } shouldBeSequence crossN(l1, l1, l1, l1, l1, l1, l1, l1)
        }
    }

    @Test
    fun testCross2() {
        repeat(REPETITIONS) {
            val l1 = createIterable(2)
            val l2 = createIterable(2)

            cross(l1, l2).map { it.toList() } shouldBeSequence crossN(l1, l2)
        }
    }

    @Test
    fun testCross3() {
        repeat(REPETITIONS) {
            val l1 = createIterable(3)
            val l2 = createIterable(3)
            val l3 = createIterable(3)

            cross(l1, l2, l3).map { it.toList() } shouldBeSequence crossN(l1, l2, l3)
        }
    }

    @Test
    fun testCross4() {
        repeat(REPETITIONS) {
            val l1 = createIterable(4)
            val l2 = createIterable(4)
            val l3 = createIterable(4)
            val l4 = createIterable(4)

            cross(l1, l2, l3, l4).map { it.toList() } shouldBeSequence crossN(l1, l2, l3, l4)
        }
    }

    @Test
    fun testCross5() {
        repeat(REPETITIONS) {
            val l1 = createIterable(5)
            val l2 = createIterable(5)
            val l3 = createIterable(5)
            val l4 = createIterable(5)
            val l5 = createIterable(5)

            cross(l1, l2, l3, l4, l5).map { it.toList() } shouldBeSequence crossN(l1, l2, l3, l4, l5)
        }
    }

    @Test
    fun testCross6() {
        repeat(REPETITIONS) {
            val l1 = createIterable(6)
            val l2 = createIterable(6)
            val l3 = createIterable(6)
            val l4 = createIterable(6)
            val l5 = createIterable(6)
            val l6 = createIterable(6)

            cross(l1, l2, l3, l4, l5, l6).map { it.toList() } shouldBeSequence crossN(l1, l2, l3, l4, l5, l6)
        }
    }

    @Test
    fun testCross7() {
        repeat(REPETITIONS) {
            val l1 = createIterable(7)
            val l2 = createIterable(7)
            val l3 = createIterable(7)
            val l4 = createIterable(7)
            val l5 = createIterable(7)
            val l6 = createIterable(7)
            val l7 = createIterable(7)

            cross(l1, l2, l3, l4, l5, l6, l7).map { it.toList() } shouldBeSequence crossN(l1, l2, l3, l4, l5, l6, l7)
        }
    }

    @Test
    fun testCross8() {
        repeat(REPETITIONS) {
            val l1 = createIterable(8)
            val l2 = createIterable(8)
            val l3 = createIterable(8)
            val l4 = createIterable(8)
            val l5 = createIterable(8)
            val l6 = createIterable(8)
            val l7 = createIterable(8)
            val l8 = createIterable(8)

            cross(l1, l2, l3, l4, l5, l6, l7, l8).map { it.toList() } shouldBeSequence crossN(l1, l2, l3, l4, l5, l6, l7, l8)
        }
    }

    @Test
    fun testCross9() {
        repeat(REPETITIONS) {
            val l1 = createIterable(9)
            val l2 = createIterable(9)
            val l3 = createIterable(9)
            val l4 = createIterable(9)
            val l5 = createIterable(9)
            val l6 = createIterable(9)
            val l7 = createIterable(9)
            val l8 = createIterable(9)
            val l9 = createIterable(9)

            cross(l1, l2, l3, l4, l5, l6, l7, l8, l9).map { it.toList() } shouldBeSequence crossN(l1, l2, l3, l4, l5, l6, l7, l8, l9)
        }
    }

    @Test
    fun testCross10() {
        repeat(REPETITIONS) {
            val l1 = createIterable(10)
            val l2 = createIterable(10)
            val l3 = createIterable(10)
            val l4 = createIterable(10)
            val l5 = createIterable(10)
            val l6 = createIterable(10)
            val l7 = createIterable(10)
            val l8 = createIterable(10)
            val l9 = createIterable(10)
            val l10 = createIterable(10)

            cross(l1, l2, l3, l4, l5, l6, l7, l8, l9, l10).map { it.toList() } shouldBeSequence crossN(l1, l2, l3, l4, l5, l6, l7, l8, l9, l10)
        }
    }

    companion object {
        const val REPETITIONS: Int = 10_000
    }
}