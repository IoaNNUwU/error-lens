package com.github.ioannuwu.errorlens.ui.colors

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.awt.Color

// I spend 2 days to figure this out
class ColorsTest {
    @Test
    fun notEqualColorsTest() {
        val rgb = 11
        // Color(int rgb)
        val color = Color(rgb)
        assertNotEquals(rgb, color.rgb)
    }

    @Test
    fun equalColorsTest() {
        val rgb = Color(0, 0, 11).rgb
        val color = Color(rgb)
        assertEquals(rgb, color.rgb)
    }
}