package com.github.ioannuwu.errorlens.domain.renderdata

import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.openapi.editor.markup.RangeHighlighter
import java.awt.Color
import javax.swing.Icon

interface ErrorRenderData {
    val gutterIcon: Icon?
    val backgroundColor: Color?
    val textColor: Color?
    val text: String?

    // returns true if not equals
    fun notEquals(rangeHighlighter: RangeHighlighter): Boolean

    data class Impl(
            override val gutterIcon: Icon?,
            override val backgroundColor: Color?,
            override val textColor: Color?,
            override val text: String?,
    ) : ErrorRenderData {

        override fun notEquals(rangeHighlighter: RangeHighlighter): Boolean {
            if (rangeHighlighter.errorStripeTooltip == null) return false
            if (rangeHighlighter.errorStripeTooltip !is HighlightInfo) return false

            if ((rangeHighlighter.errorStripeTooltip as HighlightInfo).description == text) return true

            return false
        }
    }
}