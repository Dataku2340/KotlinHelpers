package numbertools


/**
 * Return the mean of two integers. Works for any two integers.
 * If the exact mean is not representable as an integer, the result is the neighboring closer to 0.
 *
 * Examples:
 *  * 0 mean 0 = 0
 *  * x mean x = x
 *
 *  * 0 mean -2 = -1
 *  * 0 mean -1 = 0
 *  * 0 mean 0 = 0
 *  * 0 mean 1 = 0
 *  * 0 mean 2 = 1
 *
 */
infix fun Int.mean(other: Int): Int {
    if ((this < 0) == (other < 0)) {  // this,other same sign
        return this / 2 + other / 2 + (this % 2 + other % 2) / 2
    }
    return (this + other) / 2
}

/**
 * Return the mean of two longs. Works for any two longs.
 * If the exact mean is not representable as a long, the result is the neighboring closer to 0.
 *
 * Examples:
 *  * 0 mean 0 = 0
 *  * x mean x = x
 *
 *  * 0 mean -2 = -1
 *  * 0 mean -1 = 0
 *  * 0 mean 0 = 0
 *  * 0 mean 1 = 0
 *  * 0 mean 2 = 1
 *
 */
infix fun Long.mean(other: Long): Long {
    if ((this < 0) == (other < 0)) {  // this,other same sign
        return this / 2 + other / 2 + (this % 2 + other % 2) / 2
    }
    return (this + other) / 2
}