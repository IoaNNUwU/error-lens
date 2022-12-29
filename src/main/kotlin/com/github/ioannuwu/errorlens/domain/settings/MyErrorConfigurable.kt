package com.github.ioannuwu.errorlens.domain.settings

import com.github.ioannuwu.errorlens.data.DataSettingsService
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

class MyErrorConfigurable : Configurable {

    private val settingsComponentService =
            DomainSettingsService(ApplicationManager.getApplication().getService(DataSettingsService::class.java))

    override fun createComponent(): JComponent =
            settingsComponentService.createComponent()

    override fun isModified(): Boolean =
            settingsComponentService.isModified()

    override fun apply() =
            settingsComponentService.applyChanges()

    override fun getDisplayName(): String = "Error Lens Settings"
}