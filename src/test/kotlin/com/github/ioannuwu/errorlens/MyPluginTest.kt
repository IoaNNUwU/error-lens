package com.github.ioannuwu.errorlens

import com.github.ioannuwu.errorlens.domain.HideListParser
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.jetbrains.rd.util.printlnError
import junit.framework.TestCase
import org.junit.Test

@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class MyPluginTest : BasePlatformTestCase() {

    @Test
    fun testTest() {
        TestCase.assertEquals(1, 1)
    }
}
