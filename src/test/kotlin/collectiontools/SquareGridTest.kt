package collectiontools

import collectiontools.SquareGrid
import collectiontools.cross.crossSelf
import shouldBe
import kotlin.test.Test

class SquareGridTest {

    @Test
    fun test() {
        SquareGrid(3, 2, SquareGrid.Uniqueness.NOT_UNIQUE).toList() shouldBe
                listOf(listOf(0, 0), listOf(0, 1), listOf(0, 2), listOf(1, 0), listOf(1, 1), listOf(1, 2), listOf(2, 0), listOf(2, 1), listOf(2, 2))
        SquareGrid(3, 2, SquareGrid.Uniqueness.UNIQUE).toList() shouldBe
                listOf(listOf(0, 0), listOf(0, 1), listOf(0, 2), listOf(1, 1), listOf(1, 2), listOf(2, 2))
        SquareGrid(3, 2, SquareGrid.Uniqueness.UNIQUE_NO_REPLACEMENT).toList() shouldBe
                listOf(listOf(0, 1), listOf(0, 2), listOf(1, 2))
    }

    @Test
    fun equivalenceTest() {

        SquareGrid(7, 4, SquareGrid.Uniqueness.UNIQUE).sortedBy { it.toString() } shouldBe
                SquareGrid(7, 4, SquareGrid.Uniqueness.NOT_UNIQUE).asSequence()
                    .distinctBy { it.sorted() }.toList()
                    .sortedBy { it.toString() }

        SquareGrid(7, 4, SquareGrid.Uniqueness.UNIQUE_NO_REPLACEMENT).sortedBy { it.toString() } shouldBe
                SquareGrid(7, 4, SquareGrid.Uniqueness.NOT_UNIQUE).asSequence()
                    .distinctBy { it.sorted() }.filter { it.size == it.toSet().size }
                    .asIterable()
                    .sortedBy { it.toString() }
    }

    @Test
    fun crossTest() {
        SquareGrid(7, 4, SquareGrid.Uniqueness.NOT_UNIQUE).sortedBy { it.toString() } shouldBe
         (0..6).toList().crossSelf(4).toList().sortedBy { it.toString() }
    }
}