package com.github.ioannuwu.errorlens.errorRender.renderData

import com.github.ioannuwu.errorlens.appearance.DefaultSettingsList
import com.github.ioannuwu.errorlens.appearance.MyIconsList
import com.github.ioannuwu.errorlens.gui.MySettingsService
import com.github.ioannuwu.errorlens.gui.Settings
import com.intellij.lang.annotation.HighlightSeverity
import java.awt.Color
import javax.swing.Icon

interface ErrorRenderDataSelector {

    fun select(severity: HighlightSeverity): ErrorRenderData


    class DataSelectorFromSettingsService(settingsService: MySettingsService) : ErrorRenderDataSelector {

        private val error = settingsService.error
        private val warning = settingsService.warning
        private val weakWarning = settingsService.weakWarning
        private val information = settingsService.information
        private val other = settingsService.other

        override fun select(severity: HighlightSeverity): ErrorRenderData =
                when (severity) {
                    HighlightSeverity.ERROR -> MyErrorRenderData(error, MyIconsList.ERROR)
                    HighlightSeverity.WARNING -> MyErrorRenderData(warning, MyIconsList.WARNING)
                    HighlightSeverity.WEAK_WARNING -> MyErrorRenderData(weakWarning, MyIconsList.WEAK_WARNING)
                    HighlightSeverity.INFORMATION -> MyErrorRenderData(information, MyIconsList.INFORMATION)
                    else -> MyErrorRenderData(other, MyIconsList.OTHER)
                }
    }

    object DefaultDataSelector : ErrorRenderDataSelector {
        override fun select(severity: HighlightSeverity): ErrorRenderData =
                when (severity) {
                    HighlightSeverity.ERROR -> MyErrorRenderData(DefaultSettingsList.ERROR, MyIconsList.ERROR)
                    HighlightSeverity.WARNING -> MyErrorRenderData(DefaultSettingsList.WARNING, MyIconsList.WARNING)
                    HighlightSeverity.WEAK_WARNING -> MyErrorRenderData(DefaultSettingsList.WEAK_WARNING, MyIconsList.WEAK_WARNING)
                    HighlightSeverity.INFORMATION -> MyErrorRenderData(DefaultSettingsList.INFORMATION, MyIconsList.INFORMATION)
                    else -> MyErrorRenderData(DefaultSettingsList.OTHER, MyIconsList.OTHER)
                }

    }

    private class MyErrorRenderData(colors: Settings, override val gutterIcon: Icon) : ErrorRenderData {
        override val backgroundColor: Color = Color(colors.backgroundColor)
        override val textColor: Color = Color(colors.textColor)
    }
}