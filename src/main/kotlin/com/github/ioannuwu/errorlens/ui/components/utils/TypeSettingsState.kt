package com.github.ioannuwu.errorlens.ui.components.utils

import com.github.ioannuwu.errorlens.data.SettingsState.ErrorTypeSettingsState

interface TypeSettingsState {

    fun typeSettingsState(): ErrorTypeSettingsState
}