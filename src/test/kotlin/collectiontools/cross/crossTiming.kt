package collectiontools.cross


import iterablesequencetools.averageOf
import iterablesequencetools.empStandardDev
import iterablesequencetools.product
import nuples.Quadruple
import nuples.toQuadruple
import shouldBe
import kotlin.math.pow
import kotlin.random.Random
import kotlin.test.Test
import kotlin.time.Duration
import kotlin.time.Duration.Companion.microseconds
import kotlin.time.measureTime

class CrossTiming {
    @Test
    fun timeTest() {
        val N = 4_000_000.toDouble().pow(1 / 4.0).toInt().also { println(it) }
        val list1 = (1 .. N).map { Random.nextInt() }
        val list2 = (1 .. N).map { Random.nextInt() }
        val list3 = (1 .. N).map { Random.nextInt() }
        val list4 = (1 .. N).map { Random.nextInt() }
        val realN = listOf(list1, list2, list3, list4).map { it.size }.product()
        println(realN)
        val L = (1 .. realN).map { Random.nextInt() }

        val crossTimes = ArrayList<Duration>()
        val crossListTimes = ArrayList<Duration>()
        val forTimes = ArrayList<Duration>()
        val notCrossTime = ArrayList<Duration>() // for comparison, doesnt use any crossing

        repeat(10) {
            println(it)

            (measureTime {
                val result = crossToList(list1, list2, list3, list4)
            })

            (measureTime {
                val result = cross(list1, list2, list3, list4).toList()
            })
        }

        repeat(15) {
            println(it)

            crossTimes.add(measureTime {
                val result = crossToList(list1, list2, list3, list4)
            })

            crossListTimes.add(measureTime {
                val result = cross(list1, list2, list3, list4).toList()
            })

            forTimes.add(measureTime {
                val result = ArrayList<Quadruple<Int, Int, Int, Int>>(realN + 1)
                for (a in list1) {
                    for (b in list2) {
                        for (c in list3) {
                            for (d in list4) {
                                result += Quadruple(a, b, c, d)
                            }
                        }
                    }
                }
            })

            notCrossTime.add(measureTime {
                val result = L.map { Quadruple(it, it * 3, it * 5, it * 61) }
            })
        }


        println()
        println("Times for collectiontools.cross.cross:")
        val average = crossTimes.averageOf { it.inWholeMicroseconds.toDouble() }
        val std = crossTimes.map { it.inWholeMicroseconds.toDouble() }.empStandardDev()
        println("Average: ${average.microseconds}, Standard Deviation: ${std.microseconds}")
        println("Average per item: ${average.microseconds / realN}, Standard Deviation: ${std.microseconds / realN}")

        println()
        println("Times for crossList:")
        val averageList = crossListTimes.averageOf { it.inWholeMicroseconds.toDouble() }
        val stdList = crossListTimes.map { it.inWholeMicroseconds.toDouble() }.empStandardDev()
        println("Average: ${averageList.microseconds}, Standard Deviation: ${stdList.microseconds}")
        println("Average per item: ${averageList.microseconds / realN}, Standard Deviation per item: ${stdList.microseconds / realN}")

        println()
        println("Times for forTimes:")
        val averageFor = forTimes.averageOf { it.inWholeMicroseconds.toDouble() }
        val stdFor = forTimes.map { it.inWholeMicroseconds.toDouble() }.empStandardDev()
        println("Average: ${averageFor.microseconds}, Standard Deviation: ${stdFor.microseconds}")
        println("Average per item: ${averageFor.microseconds / realN}, Standard Deviation per item: ${stdFor.microseconds / realN}")

        println()
        println("Times for notCrossTime:")
        val averageNotCross = notCrossTime.averageOf { it.inWholeMicroseconds.toDouble() }
        val stdNotCross = notCrossTime.map { it.inWholeMicroseconds.toDouble() }.empStandardDev()
        println("Average: ${averageNotCross.microseconds}, Standard Deviation: ${stdNotCross.microseconds}")
        println("Average per item: ${averageNotCross.microseconds / realN}, Standard Deviation per item: ${stdNotCross.microseconds / realN}")

        1 + 2 shouldBe 3
    }

    /**
     * Crosses four lists, but calles toList directly, unlike the cross function that calls asSequence first.
     */
    fun <A, B, C, D> crossToList(first: Iterable<A>, second: Iterable<B>, third: Iterable<C>, fourth: Iterable<D>): List<Quadruple<A, B, C, D>> =
        IterableCrosser(first, second, third, fourth) { it.toQuadruple<A, B, C, D>() }.toList()
}