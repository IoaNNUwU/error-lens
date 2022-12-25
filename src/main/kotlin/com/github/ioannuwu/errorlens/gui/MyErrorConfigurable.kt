package com.github.ioannuwu.errorlens.gui

import com.github.ioannuwu.errorlens.gui.appSettingsComponent.AppSettingsComponent
import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

class MyErrorConfigurable : Configurable {

    private lateinit var mySettingsComponent: AppSettingsComponent

    override fun createComponent(): JComponent {
        mySettingsComponent = AppSettingsComponent()
        return mySettingsComponent.myMainPanel
    }

    override fun isModified(): Boolean {
        val settings = MySettingsService.getInstance()
        return mySettingsComponent.hasChanged(settings)
    }

    override fun apply() {
        val settings = MySettingsService.getInstance()
        mySettingsComponent.apply(settings)
    }

    override fun getDisplayName() = "Error Lens Settings"
}