package numbertools

import numbertools.mean
import shouldBe
import org.junit.jupiter.api.Test

import kotlin.random.Random

class MathKtTest {

    @Test
    fun testIntRandom() {
        repeat(100_000_000) {
            val a = Random.nextInt()
            val b = Random.nextInt()
            val mean = a.mean(b)
            val expected = ((a.toLong() + b.toLong()) / 2).toInt()
            mean shouldBe expected
        }

        repeat(100000) {
            val a = Random.nextInt()
            val mean = a mean a
            mean shouldBe a
        }
    }

    @Test
    fun testInt(){

        0 mean 0 shouldBe  0
        0 mean -2 shouldBe  -1
        0 mean -1 shouldBe  0
        0 mean 0 shouldBe  0
        0 mean 1 shouldBe  0
        0 mean 2 shouldBe  1

    }

}