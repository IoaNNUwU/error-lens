package com.github.ioannuwu.errorlens.ui

import com.github.ioannuwu.errorlens.data.AbstractDataSettingsService
import com.github.ioannuwu.errorlens.data.SettingsState
import com.github.ioannuwu.errorlens.data.defaultsettings.DefaultSettingsList
import com.github.ioannuwu.errorlens.domain.HideListParser
import com.github.ioannuwu.errorlens.ui.components.HideListComponent
import com.github.ioannuwu.errorlens.ui.components.MyComponent
import com.github.ioannuwu.errorlens.ui.components.NameComponent
import com.github.ioannuwu.errorlens.ui.components.NumberOfWhitespacesComponent
import com.github.ioannuwu.errorlens.ui.components.errors.ErrorTypeComponent
import com.github.ioannuwu.errorlens.ui.components.errors.ErrorTypesComponent
import com.jetbrains.rd.util.printlnError
import javax.swing.JComponent

class SettingsComponentService(
        dataSettingsService: AbstractDataSettingsService,
        private val hideListParser: HideListParser,
) {

    private val mySettingsState = dataSettingsService.state

    private val numberOfWhiteSpacesComponent = NumberOfWhitespacesComponent(mySettingsState.numberOfWhitespaces)

    private val errorTypeComponent = ErrorTypeComponent(ErrorTypeComponent.Data(
            "Error", "critical errors", mySettingsState.error
    ))
    private val warningTypeComponent = ErrorTypeComponent(ErrorTypeComponent.Data(
            "Warning", "warnings", mySettingsState.warning
    ))
    private val weakWarningTypeComponent = ErrorTypeComponent(ErrorTypeComponent.Data(
            "Weak warning", "weak warnings", mySettingsState.weakWarning
    ))
    private val informationTypeComponent = ErrorTypeComponent(ErrorTypeComponent.Data(
            "Information", "small hints", mySettingsState.information
    ))
    private val otherTypeComponent = ErrorTypeComponent(ErrorTypeComponent.Data(
            "Other", "super small hints like TYPOs", mySettingsState.other
    ))

    private val hideListComponent = HideListComponent(mySettingsState.hideList, hideListParser)

    fun createComponent(): JComponent {
        val components = mutableListOf<MyComponent>()

        components.add(NameComponent())

        components.add(numberOfWhiteSpacesComponent)

        val errorComponents = mutableListOf<ErrorTypeComponent>()

        errorComponents.add(errorTypeComponent)
        errorComponents.add(warningTypeComponent)
        errorComponents.add(weakWarningTypeComponent)
        errorComponents.add(informationTypeComponent)
        errorComponents.add(otherTypeComponent)

        components.add(ErrorTypesComponent(errorComponents))

        components.add(hideListComponent)

        return SettingsComponent(components)
    }

    fun currentState(): SettingsState {
        val settingsState = SettingsState()

        var uiNumberOfWhitespaces: Int? = numberOfWhiteSpacesComponent.numberOfWhitespaces.toIntOrNull()
        printlnError("uiNumberOfWhitespaces: " + uiNumberOfWhitespaces.toString())

        if (uiNumberOfWhitespaces == null) {
            printlnError("uiNumberOfWhitespaces = null")
            uiNumberOfWhitespaces = DefaultSettingsList.NUMBER_OF_WHITESPACES
            numberOfWhiteSpacesComponent.numberOfWhitespaces = DefaultSettingsList.NUMBER_OF_WHITESPACES.toString()
        }

        val uiIgnoreList = hideListParser.parseToList(hideListComponent.hideList)
        hideListComponent.hideList = hideListParser.parseToString(uiIgnoreList)

        settingsState.numberOfWhitespaces = uiNumberOfWhitespaces
        settingsState.hideList = uiIgnoreList

        settingsState.error = SettingsState.ErrorTypeSettingsState(errorTypeComponent.typeSettingsState())
        settingsState.warning = SettingsState.ErrorTypeSettingsState(warningTypeComponent.typeSettingsState())
        settingsState.weakWarning = SettingsState.ErrorTypeSettingsState(weakWarningTypeComponent.typeSettingsState())
        settingsState.information = SettingsState.ErrorTypeSettingsState(informationTypeComponent.typeSettingsState())
        settingsState.other = SettingsState.ErrorTypeSettingsState(otherTypeComponent.typeSettingsState())

        return settingsState
    }
}



























