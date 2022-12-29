package com.github.ioannuwu.errorlens.domain

import junit.framework.TestCase
import org.junit.Test

class HideListParserTest {

    @Test
    fun `apostrophe parser test easy`() {
        val hideListParser = HideListParser.DividedByApostropheAndComa()
        val actual = hideListParser.parseToList("'TYPO','TODO','is never used'")
        val expected = listOf("TYPO", "TODO", "is never used")
        TestCase.assertEquals("without whitespaces", expected, actual)
    }

    @Test
    fun `apostrophe parser test hard`() {
        val hideListParser = HideListParser.DividedByApostropheAndComa()
        val actual3 = hideListParser.parseToList(" 'TYPO   ' ,'TODO   ', '    is never used' ")
        val expected = listOf("TYPO", "TODO", "is never used")
        TestCase.assertEquals("additional whitespaces", expected, actual3)
    }
}