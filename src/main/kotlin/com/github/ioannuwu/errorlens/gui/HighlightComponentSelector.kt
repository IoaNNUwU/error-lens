package com.github.ioannuwu.errorlens.gui

import com.intellij.ui.ColorPanel
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.Label
import com.intellij.util.ui.FormBuilder
import java.awt.Color

private const val gutterIconText = "Gutter icon"
private const val backgroundColorText = "Background color"
private const val textColorText = "Text color"

class HighlightComponentSelector(settings: Settings) {
    private val myGutterCheckBox = JBCheckBox(gutterIconText)
    private val myBackgroundCheckBox = JBCheckBox(backgroundColorText)
    private val myBackgroundColorPanel = ColorPanel()
    private val myTextCheckBox = JBCheckBox(textColorText)
    private val myTextColorPanel = ColorPanel()

    init {
        myGutterCheckBox.isSelected = settings.showGutterIcon

        myBackgroundCheckBox.isSelected = settings.showBackground
        myTextCheckBox.isSelected = settings.showText

        myBackgroundColorPanel.selectedColor = Color(settings.backgroundColor)
        myTextColorPanel.selectedColor = Color(settings.textColor)
    }

    fun addComponent(builder: FormBuilder) {
        val panel15 = JBPanel<Nothing>()
        panel15.add(myGutterCheckBox)
        builder.addLabeledComponent(panel15, JBPanel<Nothing>())

        val panel2 = JBPanel<Nothing>()
        with(panel2) {
            add(myBackgroundCheckBox)
            add(Label("          "))
            add(myBackgroundColorPanel)
        }
        builder.addLabeledComponent(panel2, JBPanel<Nothing>())

        val panel3 = JBPanel<Nothing>()
        with(panel3) {
            add(myTextCheckBox)
            add(Label("                        "))
            add(myTextColorPanel)
        }
        builder.addLabeledComponent(panel3, JBPanel<Nothing>())
    }

    fun hasChanged(settings: Settings): Boolean {
        if (settings.showText != myTextCheckBox.isSelected) return true
        if (settings.showBackground != myBackgroundCheckBox.isSelected) return true
        if (settings.showGutterIcon != myGutterCheckBox.isSelected) return true
        if (settings.backgroundColor != myBackgroundColorPanel.selectedColor?.rgb) return true
        if (settings.textColor != myTextColorPanel.selectedColor?.rgb) return true
        return false
    }

    fun currentState(): Settings = Settings(
            myGutterCheckBox.isSelected,
            myBackgroundCheckBox.isSelected,
            myBackgroundColorPanel.selectedColor!!.rgb,
            myTextCheckBox.isSelected,
            myTextColorPanel.selectedColor!!.rgb,
    )
}