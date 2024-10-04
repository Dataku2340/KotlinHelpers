package collectiontools

import collectiontools.RectangleGrid
import collectiontools.cross.crossN
import shouldBe
import kotlin.test.Test

class RectangleGridTest {

    @Test
    fun crossTest() {
        RectangleGrid(5, 1, 3, 5).sortedBy { it.toString() } shouldBe
                crossN((0..< 5).toList(), (0..< 1).toList(), (0..< 3).toList(), (0..< 5).toList()).sortedBy { it.toString() }.toList()
    }

    @Test
    fun emptyTest() {
        RectangleGrid(5, 1, 3, 5).isEmpty shouldBe false
        RectangleGrid(0, 1, 3, 5).isEmpty shouldBe true
        RectangleGrid().isEmpty shouldBe true
    }
}