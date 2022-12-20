package com.github.ioannuwu.errorlens.utils

import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.icons.AllIcons
import com.intellij.lang.annotation.HighlightSeverity
import java.awt.Color
import javax.swing.Icon

interface ErrorData {

    fun backgroundColor(): Color
    fun textColor(): Color
    fun gutterIcon(): Icon

    companion object {
        fun select(highlightInfo: HighlightInfo) = selectData(highlightInfo) as ErrorData
    }
}

private fun selectData(highlightInfo: HighlightInfo) = when (highlightInfo.severity) {
    HighlightSeverity.ERROR ->
        ErrorDataImpl(Color(60, 35, 0), Color(240, 0, 0), AllIcons.General.Error)
    HighlightSeverity.WARNING ->
        ErrorDataImpl(Color(60, 60, 0), Color(240, 140, 0), AllIcons.General.Warning)
    HighlightSeverity.WEAK_WARNING ->
        ErrorDataImpl(Color(60, 40, 120), Color(240, 240, 0), AllIcons.General.Warning)
    HighlightSeverity.INFORMATION ->
        ErrorDataImpl(Color(35, 35, 35), Color(200, 200, 200), AllIcons.General.Information)
    else ->
        // TYPO etc
        ErrorDataImpl(Color.RED, Color.ORANGE, AllIcons.General.Error)
}

private class ErrorDataImpl(private val backgroundColor: Color, private val textColor: Color, private val gutterIcon: Icon): ErrorData {
    override fun backgroundColor() = backgroundColor
    override fun textColor() = textColor
    override fun gutterIcon() = gutterIcon
}