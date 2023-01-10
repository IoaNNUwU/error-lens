package com.github.ioannuwu.errorlens.ui2.components.errortype

import com.github.ioannuwu.errorlens.data.SettingsState.ErrorTypeSettingsState
import com.github.ioannuwu.errorlens.data.defaultsettings.DefaultSettingsList
import com.github.ioannuwu.errorlens.dbg
import com.github.ioannuwu.errorlens.dbgw
import com.github.ioannuwu.errorlens.ui2.State
import com.github.ioannuwu.errorlens.ui2.utils.wrappers.MyCheckBox
import com.github.ioannuwu.errorlens.ui2.utils.wrappers.MyColorPanel
import com.github.ioannuwu.errorlens.ui2.utils.wrappers.MyComponent
import com.github.ioannuwu.errorlens.ui2.utils.wrappers.MyPanel
import com.intellij.ui.ContextHelpLabel
import com.intellij.ui.components.Label
import java.awt.Color
import javax.swing.JPanel

private const val GUTTER_ICON_TEXT = "Gutter icon"
private const val BACKGROUND_COLOR_TEXT = "Background color"
private const val TEXT_COLOR_TEXT = "Text color"

abstract class ErrorTypeComponent(name: String, helpDescription: String, errorTypeSettingsState: ErrorTypeSettingsState)
    : JPanel(), State<ErrorTypeSettingsState> {

    protected val gutterIconCheckBox = MyCheckBox(GUTTER_ICON_TEXT, errorTypeSettingsState.showGutterIcon)
    protected val textCheckBox = MyCheckBox(TEXT_COLOR_TEXT, errorTypeSettingsState.showText)
    protected val backgroundCheckBox = MyCheckBox(BACKGROUND_COLOR_TEXT, errorTypeSettingsState.showBackground)

    protected val textColorPanel = MyColorPanel(Color(
            dbg("Error TYPE COMPONENT: ", errorTypeSettingsState.textColor)))
    protected val backgroundColorPanel = MyColorPanel(Color(errorTypeSettingsState.backgroundColor))

    init {
        dbgw("ERROR TYPE COMPONENT state: $errorTypeSettingsState")
        this.add(MyComponent(
                MyPanel(Label(name)),
                MyPanel(ContextHelpLabel(name, helpDescription)),
                MyPanel(gutterIconCheckBox),
                MyPanel(backgroundCheckBox, Label("          "), backgroundColorPanel),
                MyPanel(textCheckBox, Label("                        "), textColorPanel)))

        dbg("ERROR TYPE COMPONENT AFTER INIT: ", textColorPanel)
    }

    class ErrorComponent(state: ErrorTypeSettingsState) :
            ErrorTypeComponent("Error", "Compile Errors", state) {
        override fun getState(): ErrorTypeSettingsState =
                ErrorTypeSettingsState(gutterIconCheckBox.isSelected,
                        backgroundCheckBox.isSelected, backgroundColorPanel.selectedColor!!.rgb,
                        textCheckBox.isSelected, textColorPanel.selectedColor!!.rgb)
    }

    class WarningComponent(state: ErrorTypeSettingsState) :
            ErrorTypeComponent("Warning", "Big warnings", state) {
        override fun getState(): ErrorTypeSettingsState =
                ErrorTypeSettingsState(gutterIconCheckBox.isSelected,
                        backgroundCheckBox.isSelected, backgroundColorPanel.selectedColor!!.rgb,
                        textCheckBox.isSelected, textColorPanel.selectedColor!!.rgb)
    }

    class WeakWarningComponent(state: ErrorTypeSettingsState) :
            ErrorTypeComponent("Weak warning", "Small warnings", state) {
        override fun getState(): ErrorTypeSettingsState =
                ErrorTypeSettingsState(gutterIconCheckBox.isSelected,
                        backgroundCheckBox.isSelected, backgroundColorPanel.selectedColor!!.rgb,
                        textCheckBox.isSelected, textColorPanel.selectedColor!!.rgb)
    }

    class InformationComponent(state: ErrorTypeSettingsState) :
            ErrorTypeComponent("Information", "Small additions", state) {
        override fun getState(): ErrorTypeSettingsState {

            return ErrorTypeSettingsState(dbg("IN gutter selected:", gutterIconCheckBox.isSelected),
                    dbg("IN back select:", backgroundCheckBox.isSelected),
                    dbg("IN back color:", backgroundColorPanel.selectedColor!!.rgb),
                    dbg("IN text selected:", textCheckBox.isSelected),
                    dbg("IN text color:", textColorPanel.selectedColor!!.rgb))
        }
    }

    class OtherComponent(state: ErrorTypeSettingsState) :
            ErrorTypeComponent("Other", "Small hints like TYPOs etc.", state) {
        override fun getState(): ErrorTypeSettingsState =
                ErrorTypeSettingsState(gutterIconCheckBox.isSelected,
                        backgroundCheckBox.isSelected, backgroundColorPanel.selectedColor!!.rgb,
                        textCheckBox.isSelected, textColorPanel.selectedColor!!.rgb)
    }
}