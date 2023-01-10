package com.github.ioannuwu.errorlens.domain.settings

import com.github.ioannuwu.errorlens.data.DataSettingsService
import com.github.ioannuwu.errorlens.domain.HideListParser
import com.github.ioannuwu.errorlens.ui.SettingsComponentService
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

class MyErrorConfigurable : Configurable {

    private val domainSettingsService: DomainSettingsService

    init {
        val dataSettingsService = ApplicationManager.getApplication().getService(DataSettingsService::class.java)
        domainSettingsService = DomainSettingsService(dataSettingsService,
                SettingsComponentService(dataSettingsService, HideListParser.DividedByApostropheAndComa()))
    }

    override fun createComponent(): JComponent =
            domainSettingsService.createComponent()

    override fun isModified(): Boolean =
            domainSettingsService.isModified()

    override fun apply() =
            domainSettingsService.applyChanges()

    override fun getDisplayName(): String = "Error Lens Settings"
}