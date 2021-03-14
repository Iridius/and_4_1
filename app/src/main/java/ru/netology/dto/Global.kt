package ru.netology.dto

object Global {
    const val CONTENT: String = "content"
    const val LINK: String = "link"

    fun formatNumber(number: Int): String {
        return when {
            number >= 1_000_000 -> roundNumber(number / 1_000_000.0) + "M"
            number >= 1_000 -> roundNumber(number / 1_000.0) + "K"
            else -> number.toString()
        }
    }

    private fun roundNumber(number: Double): String {
        val value = number.toString().take(3)

        return when {
            value.endsWith(".") -> value.take(2)
            value.endsWith(".0") -> value.take(1)
            else -> value
        }
    }
}

