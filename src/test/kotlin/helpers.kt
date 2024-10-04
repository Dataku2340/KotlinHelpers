import kotlin.test.assertEquals
import kotlin.test.assertTrue

infix fun <T> T.shouldBe(other: T) = assertEquals(other, this)

infix fun <T> Iterable<T>.shouldBeIterable(other: Iterable<T>) {
    val thisIter = this.iterator()
    val otherIter = other.iterator()

    if (thisIter.hasNext() && !otherIter.hasNext()) assertTrue(false, "First iterable is empty, but second iterable is not.")
    if (!thisIter.hasNext() && otherIter.hasNext()) assertTrue(false, "Second iterable is empty, but first is not.")
    if (!thisIter.hasNext() || !otherIter.hasNext()) return

    var thisItem = this.iterator().next()
    var otherItem = other.iterator().next()
    var index = 0

    while (thisIter.hasNext() && otherIter.hasNext()) {
        assertEquals(otherItem, thisItem, "Different at index $index: $thisItem vs $otherItem")
        thisItem = thisIter.next()
        otherItem = otherIter.next()
        index++
    }

    // Did both iterators reach the end?
    if (thisIter.hasNext()) assertTrue(false, "First iterable has $index elements, but second iterable has more.")
    if (otherIter.hasNext()) assertTrue(false, "Second iterable has $index elements, but first iterable has more.")
}


infix fun <T> Sequence<T>.shouldBeSequence(other: Sequence<T>) {
    val thisIter = this.iterator()
    val otherIter = other.iterator()

    if (thisIter.hasNext() && !otherIter.hasNext()) assertTrue(false, "First sequence is empty, but second sequence is not.")
    if (!thisIter.hasNext() && otherIter.hasNext()) assertTrue(false, "Second sequence is empty, but first is not.")
    if (!thisIter.hasNext() || !otherIter.hasNext()) return

    var thisItem = this.iterator().next()
    var otherItem = other.iterator().next()
    var index = 0

    while (thisIter.hasNext() && otherIter.hasNext()) {
        assertEquals(otherItem, thisItem, "Different at index $index: $thisItem vs $otherItem")
        thisItem = thisIter.next()
        otherItem = otherIter.next()
        index++
    }

    // Did both iterators reach the end?
    if (thisIter.hasNext()) assertTrue(false, "First sequence has $index elements, but second sequence has more.")
    if (otherIter.hasNext()) assertTrue(false, "Second sequence has $index elements, but first sequence has more.")
}

infix fun <A, B> List<A>.shouldBeDeepNoOrder(other: List<A>) where A : List<B>, B : Comparable<B> {
    val firstSorted = this.map { it.sorted() }.sortedBy { it.toString() }
    val secondSorted = other.map { it.sorted() }.sortedBy { it.toString() }
    firstSorted shouldBe secondSorted
}

infix fun <T> Iterable<T>.shouldBeNoOrder(other: Iterable<T>) {
    assertEquals(other.sortedBy { it.toString() }, this.sortedBy { it.toString() })
}