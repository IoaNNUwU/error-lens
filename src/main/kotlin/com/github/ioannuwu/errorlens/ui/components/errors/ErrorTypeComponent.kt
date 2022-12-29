package com.github.ioannuwu.errorlens.ui.components.errors

import com.github.ioannuwu.errorlens.data.SettingsState.ErrorTypeSettingsState
import com.github.ioannuwu.errorlens.ui.MyFormBuilder
import com.github.ioannuwu.errorlens.ui.components.MyComponent
import com.github.ioannuwu.errorlens.ui.components.utils.TypeSettingsState
import com.intellij.ui.ColorPanel
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.Label
import java.awt.Color
import javax.swing.JPanel

private const val gutterIconText = "Gutter icon"
private const val backgroundColorText = "Background color"
private const val textColorText = "Text color"

class ErrorTypeComponent(data: Data) : MyComponent(), TypeSettingsState {

    data class Data(
            val name: String, val helpDescription: String, val errorTypeSettingsState: ErrorTypeSettingsState
    )

    private val gutterIconCheckBox = JBCheckBox(gutterIconText)
    private val textCheckBox = JBCheckBox(textColorText)
    private val backgroundCheckBox = JBCheckBox(backgroundColorText)

    private val textColorPanel = ColorPanel()
    private val backgroundColorPanel = ColorPanel()

    init {
        val builder = MyFormBuilder()

        val namePanel = JPanel()
        namePanel.add(Label(data.name, bold = true))

        val panel15 = JPanel()
        panel15.add(gutterIconCheckBox)
        builder.addComponent(panel15)

        val panel2 = JPanel()
        with(panel2) {
            add(backgroundCheckBox)
            add(Label("          "))
            add(backgroundColorPanel)
        }
        builder.addComponent(panel2)

        val panel3 = JPanel()
        with(panel3) {
            add(textCheckBox)
            add(Label("                        "))
            add(textColorPanel)
        }
        builder.addComponent(panel3)

        gutterIconCheckBox.isSelected = data.errorTypeSettingsState.showGutterIcon
        textCheckBox.isSelected = data.errorTypeSettingsState.showText
        backgroundCheckBox.isSelected = data.errorTypeSettingsState.showBackground

        textColorPanel.selectedColor = Color(data.errorTypeSettingsState.textColor)
        backgroundColorPanel.selectedColor = Color(data.errorTypeSettingsState.backgroundColor)

        this.add(builder.build())
    }

    override fun typeSettingsState() = ErrorTypeSettingsState(
            gutterIconCheckBox.isSelected,
            backgroundCheckBox.isSelected,
            backgroundColorPanel.selectedColor!!.rgb,
            textCheckBox.isSelected,
            textColorPanel.selectedColor!!.rgb)
}





















