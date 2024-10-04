package numbertools

/**
 * Sign of a number. NaN is not a number (such as 0.0/0.0 or 0.0f/0.0f).
 */
enum class Sign {
    Negative, Zero, Positive, NaN;
}

/**
 * The [sign] of a number. If the number is not a built-in number, .toDouble() is used.
 */
val Number.sign: Sign
    get() = when (this) {
        is Short -> when {
            this < 0 -> Sign.Negative
            this > 0 -> Sign.Positive
            else -> Sign.Zero
        }

        is Int -> when {
            this < 0 -> Sign.Negative
            this > 0 -> Sign.Positive
            else -> Sign.Zero
        }

        is Long -> when {
            this < 0L -> Sign.Negative
            this > 0L -> Sign.Positive
            else -> Sign.Zero
        }

        is Float -> when {
            this.isNaN() -> Sign.NaN
            this < 0.0f -> Sign.Negative
            this > 0.0f -> Sign.Positive
            else -> Sign.Zero
        }

        is Double -> when {
            this.isNaN() -> Sign.NaN
            this < 0.0 -> Sign.Negative
            this > 0.0 -> Sign.Positive
            else -> Sign.Zero
        }

        else -> {
            val d = this.toDouble()
            when {
                d.isNaN() -> Sign.NaN
                d < 0.0 -> Sign.Negative
                d > 0.0 -> Sign.Positive
                else -> Sign.Zero
            }
        }
    }