package defaultmaps

import shouldBe
import org.junit.jupiter.api.Test

class DefaultHashMapTest {

    @Test
    fun get() {
        val nonnullable = hashMapOf(1 to "a").addDefault { "a".repeat(it) }
        nonnullable[1] shouldBe "a"
        nonnullable[0] shouldBe ""
        nonnullable[2] shouldBe "aa"
        nonnullable.size shouldBe 1

        val second = hashMapOf(1 to 10, -2 to -99)
            .addDefault { if (it <= 0) -99 else 10 * it }
        second[1] shouldBe 10
        second[-2] shouldBe -99
        second[0] shouldBe -99
        second[2] shouldBe 20

        second shouldBe hashMapOf(1 to 10, -2 to -99)

        second.getOrPut(1) shouldBe 10
        second.getOrPut(-2) shouldBe -99
        second[-3] shouldBe -99
        (-3 in second) shouldBe false
        second.getOrPut(-3) shouldBe -99
        second[-3] shouldBe -99
        (-3 in second) shouldBe true
//
//        nullable shouldBe hashMapOf(1 to 10, -2 to null, -3 to null)

        // nullability has been removed.
//        val nullable = hashMapOf(1 to 10, -2 to null)
//            .addDefault { if (it <= 0) null else 10 * it }
//        nullable[1] shouldBe 10
//        nullable[-2] shouldBe null
//        nullable[0] shouldBe null
//        nullable[2] shouldBe 20
//
//        nullable shouldBe hashMapOf(1 to 10, -2 to null)
//
//        nullable.getOrPut(1) shouldBe 10
//        nullable.getOrPut(-2) shouldBe null
//        nullable[-3] shouldBe null
//        (-3 in nullable) shouldBe false
//        nullable.getOrPut(-3) shouldBe null
//        nullable[-3] shouldBe null
//        (-3 in nullable) shouldBe true
//
//        nullable shouldBe hashMapOf(1 to 10, -2 to null, -3 to null)
    }
}