package com.github.ioannuwu.errorlens.domain.settings

import com.github.ioannuwu.errorlens.data.DataSettingsService
import com.github.ioannuwu.errorlens.data.SettingsState
import com.github.ioannuwu.errorlens.domain.HideListParser
import com.github.ioannuwu.errorlens.ui.SettingsComponentService
import javax.swing.JComponent

class DomainSettingsService(private val dataSettingsService: DataSettingsService) {

    private val uiSettingsComponentService = SettingsComponentService(dataSettingsService, HideListParser.DividedByApostropheAndComa())

    fun createComponent(): JComponent =
            uiSettingsComponentService.createComponent()

    fun isModified(): Boolean =
            uiSettingsComponentService.currentState() != dataSettingsService.state

    fun applyChanges() {
        val uiState = uiSettingsComponentService.currentState()
        val dataState = dataSettingsService.state

        dataState.error = SettingsState.ErrorTypeSettingsState(uiState.error)
        dataState.warning = SettingsState.ErrorTypeSettingsState(uiState.warning)
        dataState.weakWarning = SettingsState.ErrorTypeSettingsState(uiState.weakWarning)
        dataState.information = SettingsState.ErrorTypeSettingsState(uiState.information)
        dataState.other = SettingsState.ErrorTypeSettingsState(uiState.other)

        dataState.numberOfWhitespaces = uiState.numberOfWhitespaces

        dataState.ignoreList = uiState.ignoreList
    }
}