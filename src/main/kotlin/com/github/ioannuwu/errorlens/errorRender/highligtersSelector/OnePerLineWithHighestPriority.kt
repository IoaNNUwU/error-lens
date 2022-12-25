package com.github.ioannuwu.errorlens.errorRender.highligtersSelector

import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.openapi.editor.markup.RangeHighlighter

typealias LineNumber = Int

class OnePerLineWithHighestPriority: HighlightersSelector {

    override fun select(highlighters: Array<out RangeHighlighter?>): Map<LineNumber, RangeHighlighter> {

        val toBeProcessed = mutableMapOf<LineNumber, RangeHighlighter>()

        for (highlighter in highlighters) {
            if (highlighter == null) continue

            if (highlighter.errorStripeTooltip !is HighlightInfo) continue

            val info = highlighter.errorStripeTooltip as HighlightInfo
            if (info.description == null) continue

            val lineNumber = highlighter.document.getLineNumber(highlighter.startOffset)

            val fromMap = toBeProcessed[lineNumber]
            if (fromMap == null) {
                toBeProcessed[lineNumber] = highlighter
                continue
            }
            val fromMapPriorityIsLower = ((fromMap.errorStripeTooltip as HighlightInfo).severity.myVal
                    <= (highlighter.errorStripeTooltip as HighlightInfo).severity.myVal)
            if (fromMapPriorityIsLower) {
                fromMap.gutterIconRenderer = null
                fromMap.customRenderer = null
                toBeProcessed[lineNumber] = highlighter
            }
        }

        return toBeProcessed
    }
}