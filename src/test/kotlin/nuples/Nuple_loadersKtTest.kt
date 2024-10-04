package nuples

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import shouldBe

class NupleLoadersKtTest {

    @Test
    fun toPair() {
        listOf(1, "a").toPair<Int, String>() shouldBe Pair(1, "a")
        assertThrows<IndexOutOfBoundsException> { listOf(1, "a", 2).toPair<Int, String>() }
        assertThrows<NoSuchElementException> { listOf(1).toPair<Int, String>() }

        // unfortunately, this cannot be tested
        assertDoesNotThrow { listOf(1, "a").toPair<String, Int>() }
    }

    @Test
    fun toTriple() {
        listOf(1, "a", 2).toTriple<Int, String, Int>() shouldBe Triple(1, "a", 2)
        assertThrows<IndexOutOfBoundsException> { listOf(1, "a", 1, 1).toTriple<Int, String, Int>() }
        assertThrows<NoSuchElementException> { listOf(1).toTriple<Int, String, Int>() }
    }

    @Test
    fun toQuadruple() {
        listOf(1, "a", 2, 3).toQuadruple<Int, String, Int, Int>() shouldBe Quadruple(1, "a", 2, 3)
        assertThrows<IndexOutOfBoundsException> { listOf(1, "a", 1, 1, 1).toQuadruple<Int, String, Int, Int>() }
        assertThrows<NoSuchElementException> { listOf(1).toQuadruple<Int, String, Int, Int>() }
    }

    @Test
    fun toQuintuple() {
        listOf(1, "a", 2, 3, 4).toQuintuple<Int, String, Int, Int, Int>() shouldBe Quintuple(1, "a", 2, 3, 4)
        assertThrows<IndexOutOfBoundsException> { listOf(1, "a", 1, 1, 1, 1).toQuintuple<Int, String, Int, Int, Int>() }
        assertThrows<NoSuchElementException> { listOf(1).toQuintuple<Int, String, Int, Int, Int>() }
    }

    @Test
    fun toSextuple() {
        listOf(1, "a", 2, 3, 4, 5).toSextuple<Int, String, Int, Int, Int, Int>() shouldBe Sextuple(1, "a", 2, 3, 4, 5)
        assertThrows<IndexOutOfBoundsException> { listOf(1, "a", 1, 1, 1, 1, 1).toSextuple<Int, String, Int, Int, Int, Int>() }
        assertThrows<NoSuchElementException> { listOf(1).toSextuple<Int, String, Int, Int, Int, Int>() }
    }

    @Test
    fun toSeptuple() {
        listOf(1, "a", 2, 3, 4, 5, 6).toSeptuple<Int, String, Int, Int, Int, Int, Int>() shouldBe Septuple(1, "a", 2, 3, 4, 5, 6)
        assertThrows<IndexOutOfBoundsException> { listOf(1, "a", 1, 1, 1, 1, 1, 1).toSeptuple<Int, String, Int, Int, Int, Int, Int>() }
        assertThrows<NoSuchElementException> { listOf(1).toSeptuple<Int, String, Int, Int, Int, Int, Int>() }
    }

    @Test
    fun toOctuple() {
        listOf(1, "a", 2, 3, 4, 5, 6, 7).toOctuple<Int, String, Int, Int, Int, Int, Int, Int>() shouldBe Octuple(1, "a", 2, 3, 4, 5, 6, 7)
        assertThrows<IndexOutOfBoundsException> { listOf(1, "a", 1, 1, 1, 1, 1, 1, 1).toOctuple<Int, String, Int, Int, Int, Int, Int, Int>() }
        assertThrows<NoSuchElementException> { listOf(1).toOctuple<Int, String, Int, Int, Int, Int, Int, Int>() }
    }

    @Test
    fun toNonuple() {
        listOf(1, "a", 2, 3, 4, 5, 6, 7, 8).toNonuple<Int, String, Int, Int, Int, Int, Int, Int, Int>() shouldBe Nonuple(1, "a", 2, 3, 4, 5, 6, 7, 8)
        assertThrows<IndexOutOfBoundsException> { listOf(1, "a", 1, 1, 1, 1, 1, 1, 1, 1).toNonuple<Int, String, Int, Int, Int, Int, Int, Int, Int>() }
        assertThrows<NoSuchElementException> { listOf(1).toNonuple<Int, String, Int, Int, Int, Int, Int, Int, Int>() }
    }

    @Test
    fun toDecuple() {
        listOf(1, "a", 2, 3, 4, 5, 6, 7, 8, 9).toDecuple<Int, String, Int, Int, Int, Int, Int, Int, Int, Int>() shouldBe Decuple(1, "a", 2, 3, 4, 5, 6, 7, 8, 9)
        assertThrows<IndexOutOfBoundsException> { listOf(1, "a", 1, 1, 1, 1, 1, 1, 1, 1, 1).toDecuple<Int, String, Int, Int, Int, Int, Int, Int, Int, Int>() }
        assertThrows<NoSuchElementException> { listOf(1).toDecuple<Int, String, Int, Int, Int, Int, Int, Int, Int, Int>() }
    }
}