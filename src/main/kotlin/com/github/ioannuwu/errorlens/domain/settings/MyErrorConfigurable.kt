package com.github.ioannuwu.errorlens.domain.settings

import com.github.ioannuwu.errorlens.data.DataSettingsService
import com.github.ioannuwu.errorlens.ui.settings.SettingsComponent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

class MyErrorConfigurable : Configurable {

    private val dataSettingsService = ApplicationManager.getApplication().getService(DataSettingsService::class.java)!!
    private val settingsComponent = SettingsComponent.Main(dataSettingsService)

    override fun createComponent(): JComponent = settingsComponent.component()

    override fun isModified(): Boolean = settingsComponent.hasChanged()

    override fun apply() = dataSettingsService.loadState(settingsComponent.getState())

    override fun getDisplayName(): String = "Error Lens Settings"
}