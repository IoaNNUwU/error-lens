package com.github.ioannuwu.errorlens.gui.appSettingsComponent

import com.github.ioannuwu.errorlens.gui.HighlightComponentSelector
import com.github.ioannuwu.errorlens.gui.Settings
import com.intellij.ui.ContextHelpLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.Label
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.UIUtil

class ComponentInfo(private val selector: HighlightComponentSelector, private val name: String, private val helpDescription: String) {

    fun hasChanged(settings: Settings): Boolean = selector.hasChanged(settings)

    fun currentState() = selector.currentState()

    fun addComponent(builder: FormBuilder) {

        val panel1 = JBPanel<Nothing>()
        panel1.add(Label(name, UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL, true))
        panel1.add(ContextHelpLabel.create(helpDescription))

        builder.addLabeledComponent(panel1, JBPanel<Nothing>())

        selector.addComponent(builder)
    }
}