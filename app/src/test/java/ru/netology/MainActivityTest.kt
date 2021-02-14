package ru.netology

import org.junit.Assert.*
import org.junit.Test
import ru.netology.activity.formatNumber

class MainActivityTest {
    @Test
    fun getNumber_before_1000() {
        val expected = "900"
        val actual = formatNumber(900)

        assertEquals("Ожидалось, что для небольших чисел формитирования не будет",
            expected, actual)
    }

    @Test
    fun getNumber_thousands() {
        assertEquals("Ожидалось, числа от тысячи будут отображаться в тысячах.",
            "1K", formatNumber(1_000))

        assertEquals("Ожидалось, числа больше тысячи будут отображаться с округлением.",
            "1.1K", formatNumber(1_111))

        assertEquals("Ожидалось, что в округлённом числе будет только два знака.",
            "11K", formatNumber(11_111))
    }

    @Test
    fun getNumber_millions() {
        assertEquals("Ожидалось, числа от тысячи будут отображаться в тысячах.",
            "1M", formatNumber(1_000_000))

        assertEquals("Ожидалось, числа больше тысячи будут отображаться с округлением.",
            "1.1M", formatNumber(1_111_000))

        assertEquals("Ожидалось, что в округлённом числе будет только два знака.",
            "11M", formatNumber(11_111_000))
    }
}