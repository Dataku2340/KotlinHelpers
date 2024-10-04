package collectiontools

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import shouldBe

class HelpersKtTest {

    @Test
    fun println() {
        "abc".println()
    }

    @Test
    fun allUnique() {
        listOf(1, 2, 3, 4).allDistinct() shouldBe true
        listOf(1, 2, 3, 4, 2, 5).allDistinct() shouldBe false
        setOf(1, 2, 3, 4, 2, 5).allDistinct() shouldBe true

        listOf(1, 2, 3).allDistinctBy { it } shouldBe true
        listOf(1, 2, 3).allDistinctBy { it / 2 } shouldBe false
    }

    @Test
    fun zipValues() {
        val map1 = mapOf("a" to 1, "b" to 2)
        val map2 = mapOf("b" to 3, "c" to 4)
        val zipped = map1.zipValues(map2, 0, 0)
        zipped shouldBe mapOf("a" to Pair(1, 0), "b" to Pair(2, 3), "c" to Pair(0, 4))
    }

    @Test
    fun zipValuesOrNull() {
        val map1 = mapOf("a" to 1, "b" to 2)
        val map2 = mapOf("b" to 3, "c" to 4)
        val zipped = map1.zipValuesOrNull(map2)
        zipped shouldBe  mapOf("a" to Pair(1, null), "b" to Pair(2, 3), "c" to Pair(null, 4))
    }

    @Test
    fun zipValuesOrThrow() {
        assertThrows<IllegalArgumentException> {
            val map1 = mapOf("a" to 1, "b" to 2)
            val map2 = mapOf("b" to 3, "c" to 4)
            map1.zipValuesOrThrow(map2)
        }

        assertThrows<IllegalArgumentException> {
            val map1 = mapOf("a" to 1, "b" to 2, "c" to 3)
            val map2 = mapOf("b" to 3, "c" to 4)
            map1.zipValuesOrThrow(map2)
        }

        run {
            val map1 = mapOf("a" to 1, "b" to 2, "c" to 3)
            val map2 = mapOf("b" to 3, "c" to 4, "a" to 5)
            map1.zipValuesOrThrow(map2) shouldBe mapOf("a" to Pair(1, 5), "b" to Pair(2, 3), "c" to Pair(3, 4))
        }
    }
}