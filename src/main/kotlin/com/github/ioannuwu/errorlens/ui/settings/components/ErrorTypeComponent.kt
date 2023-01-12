package com.github.ioannuwu.errorlens.ui.settings.components

import com.github.ioannuwu.errorlens.data.SettingsState
import com.github.ioannuwu.errorlens.ui.settings.State
import com.github.ioannuwu.errorlens.ui.settings.wrappers.ComponentUtils
import com.github.ioannuwu.errorlens.ui.settings.wrappers.MyCheckBox
import com.github.ioannuwu.errorlens.ui.settings.wrappers.MyColorPanel
import com.github.ioannuwu.errorlens.ui.settings.wrappers.MyPanel
import com.intellij.ui.ContextHelpLabel
import com.intellij.ui.components.Label
import com.intellij.util.ui.UIUtil
import org.jetbrains.annotations.TestOnly
import java.awt.Color
import javax.swing.JPanel

private const val GUTTER_ICON_TEXT = "Gutter icon"
private const val BACKGROUND_COLOR_TEXT = "Background color"
private const val TEXT_COLOR_TEXT = "Text color"


abstract class ErrorTypeComponent(name: String, helpDescription: String, errorTypeSettingsState: SettingsState.ErrorTypeSettingsState)
    : JPanel(), State<SettingsState.ErrorTypeSettingsState> {

    protected val gutterIconCheckBox = MyCheckBox(GUTTER_ICON_TEXT, errorTypeSettingsState.showGutterIcon)
    protected val textCheckBox = MyCheckBox(TEXT_COLOR_TEXT, errorTypeSettingsState.showText)
    protected val backgroundCheckBox = MyCheckBox(BACKGROUND_COLOR_TEXT, errorTypeSettingsState.showBackground)

    protected val textColorPanel = MyColorPanel(Color(
            errorTypeSettingsState.textColor))
    protected val backgroundColorPanel = MyColorPanel(Color(errorTypeSettingsState.backgroundColor))

    init {
        this.add(ComponentUtils.createPanel(
                MyPanel(Label(name, null, UIUtil.FontColor.NORMAL, true),
                        ContextHelpLabel.create(name, helpDescription)),
                MyPanel(gutterIconCheckBox),
                MyPanel(backgroundCheckBox, Label("          "), backgroundColorPanel),
                MyPanel(textCheckBox, Label("                        "), textColorPanel)))
    }

    class ErrorComponent(state: SettingsState.ErrorTypeSettingsState) :
            ErrorTypeComponent("Error", "Compile Errors", state) {
        override fun getState(): SettingsState.ErrorTypeSettingsState =
                SettingsState.ErrorTypeSettingsState(gutterIconCheckBox.isSelected,
                        backgroundCheckBox.isSelected, backgroundColorPanel.selectedColor.rgb,
                        textCheckBox.isSelected, textColorPanel.selectedColor.rgb)
    }

    class WarningComponent(state: SettingsState.ErrorTypeSettingsState) :
            ErrorTypeComponent("Warning", "Big warnings", state) {
        override fun getState(): SettingsState.ErrorTypeSettingsState =
                SettingsState.ErrorTypeSettingsState(gutterIconCheckBox.isSelected,
                        backgroundCheckBox.isSelected, backgroundColorPanel.selectedColor.rgb,
                        textCheckBox.isSelected, textColorPanel.selectedColor.rgb)
    }

    class WeakWarningComponent(state: SettingsState.ErrorTypeSettingsState) :
            ErrorTypeComponent("Weak warning", "Small warnings", state) {
        override fun getState(): SettingsState.ErrorTypeSettingsState =
                SettingsState.ErrorTypeSettingsState(gutterIconCheckBox.isSelected,
                        backgroundCheckBox.isSelected, backgroundColorPanel.selectedColor.rgb,
                        textCheckBox.isSelected, textColorPanel.selectedColor.rgb)
    }

    class InformationComponent(state: SettingsState.ErrorTypeSettingsState) :
            ErrorTypeComponent("Information", "Small additions", state) {
        override fun getState(): SettingsState.ErrorTypeSettingsState =
                SettingsState.ErrorTypeSettingsState(gutterIconCheckBox.isSelected,
                        backgroundCheckBox.isSelected,
                        backgroundColorPanel.selectedColor.rgb,
                        textCheckBox.isSelected,
                        textColorPanel.selectedColor.rgb)

        @TestOnly
        fun getGutterCheckBoxAndTextColorPanel() = arrayOf(gutterIconCheckBox, textColorPanel)
    }

    class OtherComponent(state: SettingsState.ErrorTypeSettingsState) :
            ErrorTypeComponent("Other", "Small hints like TYPOs etc.", state) {
        override fun getState(): SettingsState.ErrorTypeSettingsState =
                SettingsState.ErrorTypeSettingsState(gutterIconCheckBox.isSelected,
                        backgroundCheckBox.isSelected, backgroundColorPanel.selectedColor.rgb,
                        textCheckBox.isSelected, textColorPanel.selectedColor.rgb)
    }
}