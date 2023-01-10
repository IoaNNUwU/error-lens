package com.github.ioannuwu.errorlens.ui2

import com.github.ioannuwu.errorlens.data.AbstractDataSettingsService
import com.github.ioannuwu.errorlens.data.SettingsState
import com.github.ioannuwu.errorlens.data.defaultsettings.DefaultSettingsList
import com.github.ioannuwu.errorlens.dbg
import com.github.ioannuwu.errorlens.dbgw
import com.github.ioannuwu.errorlens.domain.HideListParser
import com.github.ioannuwu.errorlens.ui.components.NameComponent
import com.github.ioannuwu.errorlens.ui2.components.HideListComponent
import com.github.ioannuwu.errorlens.ui2.components.NumberOfWhitespacesComponent
import com.github.ioannuwu.errorlens.ui2.components.errortype.ErrorTypeComponent
import com.github.ioannuwu.errorlens.ui2.utils.wrappers.MyComponent
import com.github.ioannuwu.errorlens.ui2.utils.wrappers.MyPanel
import javax.swing.JComponent

interface SettingsComponent : State<SettingsState> {

    class Main(dataSettingsService: AbstractDataSettingsService) : SettingsComponent {
        private val state = dataSettingsService.state

        private val parser = HideListParser.DividedByApostropheAndComa()
        private val myComponent: JComponent

        private val numberOfWhitespacesComponent =
                NumberOfWhitespacesComponent(state.numberOfWhitespaces, DefaultSettingsList.NUMBER_OF_WHITESPACES)

        private val errorComponent = ErrorTypeComponent.ErrorComponent(state.error)
        private val warningComponent = ErrorTypeComponent.WarningComponent(state.warning)
        private val weakWarningComponent = ErrorTypeComponent.WeakWarningComponent(state.weakWarning)
        private val informationComponent = ErrorTypeComponent.InformationComponent(dbgw("State.Info: ", state.information))
        private val otherComponent = ErrorTypeComponent.OtherComponent(state.other)

        private val hideListComponent = HideListComponent(state.hideList, parser)

        init {
            dbgw("\n AMONG US INPUT state: $state")
            dbgw("\n AMONG US INFO state: ${informationComponent.getState()}")
            myComponent = MyComponent(
                    MyPanel(NameComponent()),
                    MyPanel(numberOfWhitespacesComponent),
                    MyPanel(errorComponent,
                            warningComponent,
                            weakWarningComponent,
                            informationComponent,
                            otherComponent),
                    MyPanel(hideListComponent)
            )

            dbgw("After INIT Information Component STATE: ", informationComponent.getState())
        }

        override fun getState(): SettingsState {

            dbg("Information component current state after creation: ", informationComponent)

            val state = SettingsState()
            state.numberOfWhitespaces = numberOfWhitespacesComponent.getState()
            state.hideList = hideListComponent.getState()

            state.error = errorComponent.getState()
            state.warning = warningComponent.getState()
            state.weakWarning = weakWarningComponent.getState()
            state.information = informationComponent.getState()
            state.other = otherComponent.getState()

            dbg("State in settings component: ", state)

            return state
        }
    }
}