package collectiontools

import org.junit.jupiter.api.Test
import shouldBe
import shouldBeDeepNoOrder
import shouldBeNoOrder
import kotlin.math.pow
import kotlin.time.measureTimedValue

class CombinatoricsKtTest {


    /**
     * Subsets, but using the grid.
     */
    fun <E> Collection<E>.subSetsGrid(includeEmptySet: Boolean = true, includeOriginalSet: Boolean = true): Sequence<Set<E>> {
        val base = when (this) {
            is Set -> this
            else -> this.asOrToRAList().distinct()
        }.asOrToRAList()

        var sequence = SquareGrid(base = 2, count = base.size, uniqueness = SquareGrid.Uniqueness.NOT_UNIQUE)
            .asSequence()
            .map { base.filterIndexedTo(HashSet()) { index, _ -> it[index] == 1 } }

        if (!includeEmptySet) sequence = sequence.drop(1)
        if (!includeOriginalSet) sequence = sequence.filter { it.size != base.size }

        return sequence
    }


    @Test
    fun subSets() {
        val list = listOf(1, 2, 3, 4, 1)

        val expected = setOf(
            setOf(1, 2, 3, 4),
            setOf(1, 2, 3),
            setOf(1, 2, 4),
            setOf(1, 3, 4),
            setOf(2, 3, 4),
            setOf(1, 2),
            setOf(1, 3),
            setOf(1, 4),
            setOf(2, 3),
            setOf(2, 4),
            setOf(3, 4),
            setOf(1),
            setOf(2),
            setOf(3),
            setOf(4),
            setOf()
        )

        val actual = list.subSets().toSet()

        actual shouldBe expected
    }

    @Test
    fun subSetsFlatMap() {
        for (k in 1 .. 14) {
            val list = (0 .. k).toList()
            val flatMap = measureTimedValue { list.subSets().toSet() }
            val gridBased = measureTimedValue { list.subSetsGrid().toSet() }
            println("k=$k: flatMap time: ${flatMap.duration}, grid based time: ${gridBased.duration}.")

            gridBased.value shouldBe flatMap.value
        }
    }

    @Test
    fun select() {
        for (n in listOf(0, 1, 3, 4, 5, 8))
            for (k in listOf(0, 1, 3, 4, 5, 8))
                testSelectUsingCross(n, k)

        testSelectUsingCross(3, 10)
        testSelectUsingCross(10, 3)
    }

    fun testSelectUsingCross(n: Int, k: Int) {
        val list = (1 .. n).toList()

        // false false
        list.select(k, false, false).toList() shouldBeDeepNoOrder
                list.select(k, true, true)
                    .filter { it.distinct().size == k }
                    .distinctBy { it.sorted() }
                    .toList()

        // false true
        list.select(k, false, true).toList() shouldBeNoOrder
                list.select(k, true, true)
                    .filter { it.distinct().size == k }
                    .toList()

        // true false
        list.select(k, true, false).toList() shouldBeDeepNoOrder
                list.select(k, true, true)
                    .distinctBy { it.sorted() }
                    .toList()
    }

    @Test
    fun combinations() {
        // this is only a small test. Main test is in the select method.
        val list = listOf(1, 2, 3, 4, 5)

        val actual = list.combinations(3).toList()

        val expected = listOf(
            setOf(1, 2, 3),
            setOf(1, 2, 4),
            setOf(1, 2, 5),
            setOf(1, 3, 4),
            setOf(1, 3, 5),
            setOf(1, 4, 5),
            setOf(2, 3, 4),
            setOf(2, 3, 5),
            setOf(2, 4, 5),
            setOf(3, 4, 5)
        )

        expected shouldBe actual.map { it.toSet() }
    }

    /**
     * Also tests the edge cases (n = 0, k = 0, n = 0 and k = 0)
     */
    @Test
    fun selectCount() {
        val N = listOf(0, 1, 3, 5, 8)
        val K = listOf(0, 1, 3, 5, 8)

        for (n in N) {
            for (k in K) {
                selectCount(n, k)
            }
        }

    }

    private fun Int.factorial(): Long {
        require(this >= 0) { "Factorial is not defined for negative numbers." }

        var result = 1L
        for (i in 1 .. this) result *= i
        return result
    }

    fun selectCount(n: Int, k: Int) {
        require(k >= 0)
        require(n >= 0)
        val list = (1 .. n).toList()

        fun (() -> Number).handleDefaultCases() = when {
            k == 0 -> 1
            n == 0 -> 0
            else -> this().toInt()
        }

        fun expectedCountTrueTrue() = n.toDouble().pow(k).toInt()
        fun expectedCountTrueFalse() = (n + k - 1).factorial() / (k.factorial() * (n - 1).factorial())
        fun expectedCountFalseTrue() = if (k > n) 0 else (n).factorial() / ((n - k).factorial())
        fun expectedCountFalseFalse() = if (k > n) 0 else (n).factorial() / (k.factorial() * (n - k).factorial())

        val actualTrueTrue = list.select(k, true, true).count()
        val actualTrueFalse = list.select(k, true, false).count()
        val actualFalseTrue = list.select(k, false, true).count()
        val actualFalseFalse = list.select(k, false, false).count()


        println("n = $n, k = $k")
        val results = mapOf(
            "True-True" to Pair(::expectedCountTrueTrue.handleDefaultCases(), actualTrueTrue),
            "True-False" to Pair(::expectedCountTrueFalse.handleDefaultCases(), actualTrueFalse),
            "False-True" to Pair(::expectedCountFalseTrue.handleDefaultCases(), actualFalseTrue),
            "False-False" to Pair(::expectedCountFalseFalse.handleDefaultCases(), actualFalseFalse)
        )
        println(results)

        results.forEach { (key, value) ->
            value.first shouldBe value.second
        }
    }

    @Test
    fun permutations() {
        // this is only a small test. Main test is in the select method.

        val list = (1 .. 4).toList()

        val expected = setOf(
            listOf(1, 2, 3, 4),
            listOf(1, 2, 4, 3),
            listOf(1, 3, 2, 4),
            listOf(1, 3, 4, 2),
            listOf(1, 4, 2, 3),
            listOf(1, 4, 3, 2),
            listOf(2, 1, 3, 4),
            listOf(2, 1, 4, 3),
            listOf(2, 3, 1, 4),
            listOf(2, 3, 4, 1),
            listOf(2, 4, 1, 3),
            listOf(2, 4, 3, 1),
            listOf(3, 1, 2, 4),
            listOf(3, 1, 4, 2),
            listOf(3, 2, 1, 4),
            listOf(3, 2, 4, 1),
            listOf(3, 4, 1, 2),
            listOf(3, 4, 2, 1),
            listOf(4, 1, 2, 3),
            listOf(4, 1, 3, 2),
            listOf(4, 2, 1, 3),
            listOf(4, 2, 3, 1),
            listOf(4, 3, 1, 2),
            listOf(4, 3, 2, 1)
        )

        val actual = list.permutations().toSet()

        actual shouldBe expected
    }

    @Test
    fun permutationsN() {
        // this is only a small test. Main test is in the select method.

        val list = (1 .. 4).toList()

        val expected = setOf(
            listOf(1, 2, 3),
            listOf(1, 2, 4),
            listOf(1, 3, 2),
            listOf(1, 3, 4),
            listOf(1, 4, 2),
            listOf(1, 4, 3),
            listOf(2, 1, 3),
            listOf(2, 1, 4),
            listOf(2, 3, 1),
            listOf(2, 3, 4),
            listOf(2, 4, 1),
            listOf(2, 4, 3),
            listOf(3, 1, 2),
            listOf(3, 1, 4),
            listOf(3, 2, 1),
            listOf(3, 2, 4),
            listOf(3, 4, 1),
            listOf(3, 4, 2),
            listOf(4, 1, 2),
            listOf(4, 1, 3),
            listOf(4, 2, 1),
            listOf(4, 2, 3),
            listOf(4, 3, 1),
            listOf(4, 3, 2)
        )

        val actual = list.permutations(3).toSet()

        actual shouldBe expected
    }

    @Test
    fun permutationsInplace() {
        val list = (1 .. 4).toList()

        val expected = setOf(
            listOf(1, 2, 3, 4),
            listOf(1, 2, 4, 3),
            listOf(1, 3, 2, 4),
            listOf(1, 3, 4, 2),
            listOf(1, 4, 2, 3),
            listOf(1, 4, 3, 2),
            listOf(2, 1, 3, 4),
            listOf(2, 1, 4, 3),
            listOf(2, 3, 1, 4),
            listOf(2, 3, 4, 1),
            listOf(2, 4, 1, 3),
            listOf(2, 4, 3, 1),
            listOf(3, 1, 2, 4),
            listOf(3, 1, 4, 2),
            listOf(3, 2, 1, 4),
            listOf(3, 2, 4, 1),
            listOf(3, 4, 1, 2),
            listOf(3, 4, 2, 1),
            listOf(4, 1, 2, 3),
            listOf(4, 1, 3, 2),
            listOf(4, 2, 1, 3),
            listOf(4, 2, 3, 1),
            listOf(4, 3, 1, 2),
            listOf(4, 3, 2, 1)
        )

        // As it is inplace, we need to compare the streams!!! .toList is useless! (List of all same values)
        val actual = list.permutationsInplace().map { it.toList() }.toSet()

        actual shouldBe  expected

        val actualWithoutCopy = list.permutationsInplace().toList()
        actualWithoutCopy.all { it === actualWithoutCopy.first() } shouldBe true
    }

    @Test
    fun edgeCasesSelect() {

        emptyList<Int>().select(0, false, false).toList() shouldBe listOf(emptyList())
        emptyList<Int>().select(0, false, true).toList() shouldBe listOf(emptyList())
        emptyList<Int>().select(0, true, false).toList() shouldBe listOf(emptyList())
        emptyList<Int>().select(0, true, true).toList() shouldBe listOf(emptyList())

        val l = listOf(1, 2, 3)
        l.select(0, false, false).toList() shouldBe listOf(emptyList())
        l.select(0, false, true).toList() shouldBe listOf(emptyList())
        l.select(0, true, false).toList() shouldBe listOf(emptyList())
        l.select(0, true, true).toList() shouldBe listOf(emptyList())

        emptyList<Int>().select(1, false, false).toList() shouldBe emptyList()
        emptyList<Int>().select(2, false, true).toList() shouldBe emptyList()
        emptyList<Int>().select(3, true, false).toList() shouldBe emptyList()
        emptyList<Int>().select(4, true, true).toList() shouldBe emptyList()

    }

    @Test
    fun selectExplicitExamples() {
        val list = listOf(1, 2, 3)
        list.select(2, withReplacement = false, orderMatters = false).toList() shouldBe
                listOf(listOf(1, 2), listOf(1, 3), listOf(2, 3))
        list.select(2, withReplacement = false, orderMatters = true).toList() shouldBe
                listOf(listOf(1, 2), listOf(2, 1), listOf(1, 3), listOf(3, 1), listOf(2, 3), listOf(3, 2))
        list.select(2, withReplacement = true, orderMatters = false).toList() shouldBe
                listOf(listOf(1, 1), listOf(1, 2), listOf(1, 3), listOf(2, 2), listOf(2, 3), listOf(3, 3))
        list.select(2, withReplacement = true, orderMatters = true).toList() shouldBe
                listOf(listOf(1, 1), listOf(1, 2), listOf(1, 3), listOf(2, 1), listOf(2, 2), listOf(2, 3), listOf(3, 1), listOf(3, 2), listOf(3, 3))
    }

    @Test
    fun selectExplicitExamplesWithDuplicates() {
        val list = listOf(1, 2, 1)
        list.select(2, withReplacement = false, orderMatters = false).toList() shouldBe
                listOf(listOf(1, 2), listOf(1, 1), listOf(2, 1))
        list.select(2, withReplacement = false, orderMatters = true).toList() shouldBe
                listOf(listOf(1, 2), listOf(2, 1), listOf(1, 1), listOf(1, 1), listOf(2, 1), listOf(1, 2))
        list.select(2, withReplacement = true, orderMatters = false).toList() shouldBe
                listOf(listOf(1, 1), listOf(1, 2), listOf(1, 1), listOf(2, 2), listOf(2, 1), listOf(1, 1))
        list.select(2, withReplacement = true, orderMatters = true).toList() shouldBe
                listOf(listOf(1, 1), listOf(1, 2), listOf(1, 1), listOf(2, 1), listOf(2, 2), listOf(2, 1), listOf(1, 1), listOf(1, 2), listOf(1, 1))
    }
}