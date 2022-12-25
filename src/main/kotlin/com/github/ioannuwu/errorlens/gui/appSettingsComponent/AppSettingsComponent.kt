package com.github.ioannuwu.errorlens.gui.appSettingsComponent

import com.github.ioannuwu.errorlens.appearance.DefaultSettingsList
import com.github.ioannuwu.errorlens.gui.HighlightComponentSelector
import com.github.ioannuwu.errorlens.gui.MySettingsService
import com.github.ioannuwu.errorlens.gui.SettingsState
import com.intellij.ui.ContextHelpLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBTextField
import com.intellij.ui.components.Label
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.UIUtil
import javax.swing.JPanel

class AppSettingsComponent {

    fun apply(settingsService: MySettingsService) {

        val myNumberOfWhiteSpaces =
                numberOfWhiteSpaces.text.toIntOrNull()
                        ?: DefaultSettingsList.SPACES.also {
                            numberOfWhiteSpaces.text = DefaultSettingsList.SPACES.toString() }

        val myHideList = hideList.text ?: "".also { hideList.text = "" }

        val newSettingsState = SettingsState(
                myNumberOfWhiteSpaces,
                error.currentState(),
                warning.currentState(),
                weakWarning.currentState(),
                information.currentState(),
                other.currentState(),
                myHideList)

        settingsService.applyChanges(newSettingsState)
    }

    fun hasChanged(settingsState: MySettingsService): Boolean {
        if (settingsState.numberWhitespaces != numberOfWhiteSpaces.text.toIntOrNull()) return true
        if (settingsState.hideList != hideList.text) return true

        if (error.hasChanged(settingsState.error)) return true
        if (warning.hasChanged(settingsState.warning)) return true
        if (weakWarning.hasChanged(settingsState.weakWarning)) return true
        if (information.hasChanged(settingsState.information)) return true
        if (other.hasChanged(settingsState.other)) return true
        return false
    }

    private val numberOfWhiteSpaces = JBTextField(DefaultSettingsList.SPACES.toString(), 5)

    private var error: ComponentInfo
    private var warning: ComponentInfo
    private var weakWarning: ComponentInfo
    private var information: ComponentInfo
    private var other: ComponentInfo

    private val hideList = JBTextField("TODO, unused, Typo", 35)

    val myMainPanel: JPanel

    init {
        val settingsState = MySettingsService.getInstance().state
        error = ComponentInfo(HighlightComponentSelector(settingsState.error), "Error",
                "Compile errors (missing ';' etc)")
        warning = ComponentInfo(HighlightComponentSelector(settingsState.warning), "Warning",
                "Smaller errors (TODO add example)")
        weakWarning = ComponentInfo(HighlightComponentSelector(settingsState.weakWarning), "Weak warning",
                "Super small errors (TODO add example)")
        information = ComponentInfo(HighlightComponentSelector(settingsState.information), "Information",
                "Not errors (TODO add example)")
        other = ComponentInfo(HighlightComponentSelector(DefaultSettingsList.OTHER), "Other",
                "Small hints (like TYPOs)")

        hideList.text = settingsState.hideList
        numberOfWhiteSpaces.text = settingsState.numberWhitespaces.toString()
    }

    init {
        val builder = FormBuilder.createFormBuilder()!!

        val panel01 = JBPanel<Nothing>()
        panel01.add(Label("Error Lens settings", UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.NORMAL, true))
        builder.addLabeledComponent(panel01, JBPanel<Nothing>())

        val panel0 = JBPanel<Nothing>()
        panel0.add(Label("Whitespaces after line end     "))
        panel0.add(numberOfWhiteSpaces)
        builder.addLabeledComponent(panel0, JBPanel<Nothing>())

        val options = arrayOf(error, warning, weakWarning, information, other)

        for (level in options) {
            level.addComponent(builder)
        }

        builder.addComponent(JBPanel<Nothing>())

        /*
        val panel4 = JBPanel<Nothing>()
        panel4.add(rainbowModeCheckBox)
        panel4.add(Label("Rainbow mode", UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.BRIGHTER, false))
        builder.addLabeledComponent(panel4, JBPanel<Nothing>())
         */

        val panel20 = JBPanel<Nothing>()
        panel20.add(Label("Ignore hints with words        ", UIUtil.ComponentStyle.REGULAR, UIUtil.FontColor.BRIGHTER, false))
        panel20.add(hideList)
        panel20.add(ContextHelpLabel.create("Example: 'TODO, is never used, Typo' ignores hints about unused variables, typos and todos)"))
        builder.addLabeledComponent(panel20, JBPanel<Nothing>())

        myMainPanel = builder.addComponentFillVertically(JPanel(), 0).panel
    }
}