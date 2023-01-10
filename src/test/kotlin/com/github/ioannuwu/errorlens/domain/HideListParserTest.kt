package com.github.ioannuwu.errorlens.domain

import junit.framework.TestCase
import org.junit.Test

class HideListParserTest {

    private val dividedByApostropheAndComaHideListParser: HideListParser = HideListParser.DividedByApostropheAndComa()

    @Test
    fun `apostrophe parser test easy`() {
        val actual = dividedByApostropheAndComaHideListParser.parseToList("'TYPO','TODO','is never used'")
        val expected = listOf("TYPO", "TODO", "is never used")
        TestCase.assertEquals("without whitespaces", expected, actual)
    }

    @Test
    fun `apostrophe parser test hard`() {
        val actual3 = dividedByApostropheAndComaHideListParser.parseToList(" 'TYPO   ' ,'TODO   ', '    is never used' ")
        val expected = listOf("TYPO", "TODO", "is never used")
        TestCase.assertEquals("additional whitespaces", expected, actual3)
    }

    @Test
    fun `apostrophe parser revers test`() {
        val actual = dividedByApostropheAndComaHideListParser.parseToString(listOf("TYPO", "TODO", "is never used"))
        val expected = "'TYPO', 'TODO', 'is never used'"
        TestCase.assertEquals("parseToString test", expected, actual)
    }
}