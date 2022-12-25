package com.github.ioannuwu.errorlens.appearance

import com.github.ioannuwu.errorlens.appearance.ErrorTypeList
import com.github.ioannuwu.errorlens.gui.Settings
import java.awt.Color

object DefaultSettingsList: ErrorTypeList<Settings> {

    val SPACES = 2

    val HIDE_LIST = ""

    override val ERROR: Settings = Settings(
            true,
            true, Color(40, 20, 0).rgb,
            true, Color(200, 60, 60).rgb)

    override val WARNING: Settings = Settings(
            true,
            true, Color(60, 60, 0).rgb,
            true, Color(200, 140, 40).rgb)

    override val WEAK_WARNING: Settings = Settings(
            false,
            true, Color(60, 30, 0).rgb,
            true, Color(200, 200, 100).rgb)

    override val INFORMATION: Settings = Settings(
            false,
            false, Color(35, 35, 35).rgb,
            false, Color(200, 200, 200).rgb)

    override val OTHER: Settings = Settings(
            false,
            false, Color(15, 10, 10).rgb,
            false, Color(100, 100, 100).rgb)
}