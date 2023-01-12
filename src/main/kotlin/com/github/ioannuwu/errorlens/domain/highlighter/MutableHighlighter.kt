package com.github.ioannuwu.errorlens.domain.highlighter

import com.github.ioannuwu.errorlens.domain.renderdata.MutableErrorRenderData
import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.markup.CustomHighlighterRenderer
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.openapi.editor.markup.RangeHighlighter

interface MutableHighlighter : Highlighter {
    fun setGutterIconRenderer(gutterIconRenderer: GutterIconRenderer?)

    fun setCustomRenderer(customHighlighterRenderer: CustomHighlighterRenderer?)

    override fun getRenderData(): MutableErrorRenderData


    class Impl(
            private val rangeHighlighter: RangeHighlighter
    ): MutableHighlighter {

        private val myRenderData = MutableErrorRenderData.Impl(null, null, null, null)

        init {
            if (rangeHighlighter.errorStripeTooltip == null || rangeHighlighter.errorStripeTooltip !is HighlightInfo)
                error("RangeHighlighter should have HighlightInfo as errorStripeTooltip")

            myRenderData.text = (rangeHighlighter.errorStripeTooltip as HighlightInfo).description
        }
        override fun getLineNumber(): Int = rangeHighlighter.document.getLineNumber(rangeHighlighter.startOffset)

        override fun getSeverity(): HighlightSeverity = (rangeHighlighter.errorStripeTooltip as HighlightInfo).severity

        override fun getRenderData(): MutableErrorRenderData = myRenderData

        override fun setGutterIconRenderer(gutterIconRenderer: GutterIconRenderer?) {
            rangeHighlighter.gutterIconRenderer = gutterIconRenderer
        }

        override fun setCustomRenderer(customHighlighterRenderer: CustomHighlighterRenderer?) {
            rangeHighlighter.customRenderer = customHighlighterRenderer
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Impl

            if (rangeHighlighter != other.rangeHighlighter) return false
            if (myRenderData != other.myRenderData) return false

            return true
        }

        override fun hashCode(): Int {
            var result = rangeHighlighter.hashCode()
            result = 31 * result + myRenderData.hashCode()
            return result
        }

    }
}