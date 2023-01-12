package com.github.ioannuwu.errorlens.ui.settings

import com.github.ioannuwu.errorlens.data.AbstractDataSettingsService
import com.github.ioannuwu.errorlens.data.SettingsState
import com.github.ioannuwu.errorlens.data.defaultsettings.DefaultSettingsList
import com.github.ioannuwu.errorlens.domain.settings.HideListParser
import com.github.ioannuwu.errorlens.ui.settings.components.ErrorTypeComponent
import com.github.ioannuwu.errorlens.ui.settings.components.HideListComponent
import com.github.ioannuwu.errorlens.ui.settings.components.NumberOfWhitespacesComponent
import com.github.ioannuwu.errorlens.ui.settings.wrappers.ComponentUtils
import com.github.ioannuwu.errorlens.ui.settings.wrappers.MyCheckBox
import com.github.ioannuwu.errorlens.ui.settings.wrappers.MyColorPanel
import com.github.ioannuwu.errorlens.ui.settings.wrappers.MyPanel
import com.github.ioannuwu.errorlens.utils.MyTestComponents
import com.intellij.icons.AllIcons
import com.intellij.ui.ContextHelpLabel
import com.intellij.ui.components.Label
import com.intellij.util.ui.UIUtil
import org.jetbrains.annotations.TestOnly
import javax.swing.JComponent
import javax.swing.JLabel

interface SettingsComponent : State<SettingsState> {

    fun component(): JComponent

    fun hasChanged(): Boolean


    class Main(
            private val dataSettingsService: AbstractDataSettingsService,
            parser: HideListParser = HideListParser.DividedByNewLine(),
    ) : SettingsComponent {
        private val myComponent: JComponent

        private val numberOfWhitespacesComponent =
                NumberOfWhitespacesComponent(dataSettingsService.state.numberOfWhitespaces,
                        DefaultSettingsList.NUMBER_OF_WHITESPACES)

        private val errorComponent = ErrorTypeComponent.ErrorComponent(dataSettingsService.state.error)
        private val warningComponent = ErrorTypeComponent.WarningComponent(dataSettingsService.state.warning)
        private val weakWarningComponent = ErrorTypeComponent.WeakWarningComponent(dataSettingsService.state.weakWarning)
        private val informationComponent =
                ErrorTypeComponent.InformationComponent(dataSettingsService.state.information)
        private val otherComponent = ErrorTypeComponent.OtherComponent(dataSettingsService.state.other)

        private val hideListComponent = HideListComponent(dataSettingsService.state.hideList, parser)

        init {
            myComponent = ComponentUtils.createPanel(
                    MyPanel(JLabel(AllIcons.Plugins.PluginLogo), Label("     Error Lens settings",
                            UIUtil.ComponentStyle.LARGE, UIUtil.FontColor.NORMAL, true)),
                    MyPanel(Label("    Number of whitespaces:        "), numberOfWhitespacesComponent),
                    MyPanel(errorComponent),
                    MyPanel(warningComponent),
                    MyPanel(weakWarningComponent),
                    MyPanel(informationComponent),
                    MyPanel(otherComponent),
                    MyPanel(Label("Hide hints with words: ", UIUtil.ComponentStyle.LARGE, UIUtil.FontColor.NORMAL, false),
                            ContextHelpLabel.create("<h2 id=\"errors-containing-those-words-in-the-description-will-be-ignored\">Errors containing those words in the description will be ignored</h2>\n" +
                                    "<p>Example:</p>\n" +
                                    "<p><em>TODO</em><br><em>TYPO</em><br><em>&#39;is never used&#39;</em></p>\n" +
                                    "<h3 id=\"plugin-will-ignore-all-hints-which-contain-those-words-and-phrases-todo-class-is-never-used-variable-is-never-used-etc-\">Plugin will ignore all hints which contain those words and phrases: &#39;TODO&#39;, &#39;Class is never used&#39;, &#39;variable is never used&#39; etc.</h3>\n"), ),
                    MyPanel(hideListComponent)
            )
        }

        override fun getState(): SettingsState {
            val state = SettingsState()

            state.numberOfWhitespaces = numberOfWhitespacesComponent.getState()
            state.hideList = hideListComponent.getState()

            state.error = errorComponent.getState()
            state.warning = warningComponent.getState()
            state.weakWarning = weakWarningComponent.getState()
            state.information = informationComponent.getState()
            state.other = otherComponent.getState()

            return state
        }

        override fun component() = myComponent

        override fun hasChanged() = getState() != dataSettingsService.state

        @TestOnly
        fun getTestComponents() = MyTestComponents(
                numberOfWhitespacesComponent, hideListComponent,
                informationComponent.getGutterCheckBoxAndTextColorPanel()[0] as MyCheckBox,
                informationComponent.getGutterCheckBoxAndTextColorPanel()[1] as MyColorPanel,
        )
    }
}