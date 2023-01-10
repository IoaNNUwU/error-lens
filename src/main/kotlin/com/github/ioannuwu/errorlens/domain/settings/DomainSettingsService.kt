package com.github.ioannuwu.errorlens.domain.settings

import com.github.ioannuwu.errorlens.data.AbstractDataSettingsService
import com.github.ioannuwu.errorlens.data.SettingsState
import com.github.ioannuwu.errorlens.ui.SettingsComponentService
import com.jetbrains.rd.util.printlnError
import javax.swing.JComponent

class DomainSettingsService(
        private val dataSettingsService: AbstractDataSettingsService,
        private val uiSettingsComponentService: SettingsComponentService,
) {

    fun createComponent(): JComponent =
            uiSettingsComponentService.createComponent()

    fun isModified(): Boolean {
        return uiSettingsComponentService.currentState() != dataSettingsService.state
    }

    fun applyChanges() {
        val uiState = uiSettingsComponentService.currentState()
        printlnError(uiState.toString())
        val dataState = dataSettingsService.state

        dataState.error = SettingsState.ErrorTypeSettingsState(uiState.error)
        dataState.warning = SettingsState.ErrorTypeSettingsState(uiState.warning)
        dataState.weakWarning = SettingsState.ErrorTypeSettingsState(uiState.weakWarning)
        dataState.information = SettingsState.ErrorTypeSettingsState(uiState.information)
        dataState.other = SettingsState.ErrorTypeSettingsState(uiState.other)

        dataState.numberOfWhitespaces = uiState.numberOfWhitespaces
        printlnError("data: ${dataState.numberOfWhitespaces}")
        printlnError("ui  : ${uiState.numberOfWhitespaces}")
        dataState.hideList = uiState.hideList
    }
}