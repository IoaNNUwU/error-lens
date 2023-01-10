package com.github.ioannuwu.errorlens.ui2

import com.github.ioannuwu.errorlens.data.SettingsState

interface SettingsComponentService {

    fun currentState(): SettingsState
}