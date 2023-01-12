package com.github.ioannuwu.errorlens.domain

import com.github.ioannuwu.errorlens.data.AbstractDataSettingsService
import com.github.ioannuwu.errorlens.data.DataSettingsService
import com.github.ioannuwu.errorlens.data.icons.MyIcons
import com.github.ioannuwu.errorlens.domain.highlighter.Highlighter
import com.github.ioannuwu.errorlens.domain.highlighter.MutableHighlighter
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.markup.RangeHighlighter
import java.awt.Color

interface MutatingHighlightersFilter : (MutableHighlighter) -> Boolean {

    override fun invoke(highlighter: MutableHighlighter): Boolean

    class ChangeNumberOfWhitespaces(private val numberOfWhitespaces: Int) : MutatingHighlightersFilter {

        override fun invoke(highlighter: MutableHighlighter): Boolean {
            val realText = highlighter.getRenderData().text ?: return false
            highlighter.getRenderData().text = " ".repeat(numberOfWhitespaces) + realText
            return true
        }
    }

    class ChangeSettings(private val settings: AbstractDataSettingsService) : MutatingHighlightersFilter {

        override fun invoke(highlighter: MutableHighlighter): Boolean {

            val data = highlighter.getRenderData()

            when (highlighter.getSeverity()) {
                HighlightSeverity.ERROR -> {
                    val state = settings.state.error
                    with(data) {
                        gutterIcon = if (state.showGutterIcon) MyIcons.ERROR else null
                        data.textColor = if (state.showText) Color(state.textColor) else null
                        data.backgroundColor = if (state.showBackground) Color(state.backgroundColor) else null
                    }
                }

                HighlightSeverity.WARNING -> {
                    val state = settings.state.warning
                    with(data) {
                        gutterIcon = if (state.showGutterIcon) MyIcons.WARNING else null
                        data.textColor = if (state.showText) Color(state.textColor) else null
                        data.backgroundColor = if (state.showBackground) Color(state.backgroundColor) else null
                    }
                }

                HighlightSeverity.WEAK_WARNING -> {
                    val state = settings.state.weakWarning
                    with(data) {
                        gutterIcon = if (state.showGutterIcon) MyIcons.WEAK_WARNING else null
                        data.textColor = if (state.showText) Color(state.textColor) else null
                        data.backgroundColor = if (state.showBackground) Color(state.backgroundColor) else null
                    }
                }

                HighlightSeverity.INFORMATION -> {
                    val state = settings.state.information
                    with(data) {
                        gutterIcon = if (state.showGutterIcon) MyIcons.INFORMATION else null
                        data.textColor = if (state.showText) Color(state.textColor) else null
                        data.backgroundColor = if (state.showBackground) Color(state.backgroundColor) else null
                    }
                }

                else -> {
                    val state = settings.state.other
                    with(data) {
                        gutterIcon = if (state.showGutterIcon) MyIcons.OTHER else null
                        data.textColor = if (state.showText) Color(state.textColor) else null
                        data.backgroundColor = if (state.showBackground) Color(state.backgroundColor) else null
                    }
                }
            }
            return true
        }
    }
}