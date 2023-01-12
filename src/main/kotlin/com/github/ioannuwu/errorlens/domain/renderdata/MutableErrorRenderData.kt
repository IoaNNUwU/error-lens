package com.github.ioannuwu.errorlens.domain.renderdata

import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.openapi.editor.markup.RangeHighlighter
import java.awt.Color
import javax.swing.Icon

interface MutableErrorRenderData : ErrorRenderData {

    override var gutterIcon: Icon?
    override var backgroundColor: Color?
    override var textColor: Color?
    override var text: String?

    data class Impl(
            override var gutterIcon: Icon?,
            override var backgroundColor: Color?,
            override var textColor: Color?,
            override var text: String?,
    ) : MutableErrorRenderData {

        override fun notEquals(rangeHighlighter: RangeHighlighter): Boolean {
            if (rangeHighlighter.errorStripeTooltip == null) return false
            if (rangeHighlighter.errorStripeTooltip !is HighlightInfo) return false

            if ((rangeHighlighter.errorStripeTooltip as HighlightInfo).description == text) return true

            return false
        }
    }
}