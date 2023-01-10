package com.github.ioannuwu.errorlens.ui2

import com.github.ioannuwu.errorlens.data.AbstractDataSettingsService
import com.github.ioannuwu.errorlens.data.SettingsState
import com.github.ioannuwu.errorlens.data.SettingsState.ErrorTypeSettingsState
import com.github.ioannuwu.errorlens.dbg
import junit.framework.TestCase.*
import org.junit.Test
import java.awt.Color

private val TEST_BLACK_COLOR = Color(0, 0, 0)
private val TEST_RED_COLOR = Color(200, 0, 0)

private val TEST_FALSE_NULL_TYPE_SETTINGS_STATE =
        ErrorTypeSettingsState(false, false, TEST_BLACK_COLOR.rgb, false, TEST_BLACK_COLOR.rgb)

private val TEST_INFORMATION_TYPE_SETTINGS_STATE =
        ErrorTypeSettingsState(true, true, TEST_RED_COLOR.rgb, true, TEST_RED_COLOR.rgb)

private const val TEST_NUMBER_OF_WHITESPACES = 5

private val TEST_HIDE_LIST = listOf("A", "B", "C")

private class TestDataSettingsService : AbstractDataSettingsService {
    private var mySettingsState: SettingsState = SettingsState()

    init {
        mySettingsState.error = TEST_FALSE_NULL_TYPE_SETTINGS_STATE
        mySettingsState.warning = TEST_FALSE_NULL_TYPE_SETTINGS_STATE
        mySettingsState.weakWarning = TEST_FALSE_NULL_TYPE_SETTINGS_STATE
        mySettingsState.other = TEST_FALSE_NULL_TYPE_SETTINGS_STATE

        mySettingsState.information = TEST_INFORMATION_TYPE_SETTINGS_STATE

        mySettingsState.numberOfWhitespaces = TEST_NUMBER_OF_WHITESPACES
        mySettingsState.hideList = TEST_HIDE_LIST
    }

    override fun getState(): SettingsState = mySettingsState

    override fun loadState(state: SettingsState) {
        mySettingsState = state
    }
}

class SettingsComponentTest {

    private val testDataSettingsService = TestDataSettingsService()

    private val settingsComponent = SettingsComponent.Main(testDataSettingsService)

    @Test
    fun `settings applied`() {
        val state = settingsComponent.getState()
        dbg("STATE: $state")
        assertEquals(TEST_HIDE_LIST, state.hideList)
        assertEquals(TEST_NUMBER_OF_WHITESPACES, state.numberOfWhitespaces)
        assertEquals(TEST_INFORMATION_TYPE_SETTINGS_STATE.showGutterIcon, state.information.showGutterIcon)
        assertEquals(TEST_INFORMATION_TYPE_SETTINGS_STATE.backgroundColor, state.information.backgroundColor)
        assertEquals(TEST_INFORMATION_TYPE_SETTINGS_STATE.textColor, state.information.textColor)
    }

    @Test
    fun `number of whitespaces changes`() {

    }
}