package com.github.ioannuwu.errorlens

import com.github.ioannuwu.errorlens.errorRender.HighlightersSelector
import com.github.ioannuwu.errorlens.errorRender.renderData.ErrorRenderDataSelector
import com.github.ioannuwu.errorlens.gui.MySettingsService
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.application.ApplicationManager

class MyLocalInspectionTool : LocalInspectionTool() {

    private val settingsService = ApplicationManager.getApplication().getService(MySettingsService::class.java)
    private val highlightersSelector = HighlightersSelector.OnePerLineWithHighestPriority()
    private val errorDataSelector = ErrorRenderDataSelector.DataSelectorFromSettingsService(settingsService)

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean) =
            MyElementVisitor(settingsService.numberWhitespaces, highlightersSelector, errorDataSelector)
}
