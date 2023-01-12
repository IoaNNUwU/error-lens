package com.github.ioannuwu.errorlens.domain

import com.github.ioannuwu.errorlens.domain.highlighter.MutableHighlighter
import com.github.ioannuwu.errorlens.domain.linehighlighter.LineHighlighterRenderer
import com.github.ioannuwu.errorlens.domain.linehighlighter.LineHighlighterRendererWithSecretAttributes
import com.github.ioannuwu.errorlens.domain.renderdata.ErrorRenderData
import com.github.ioannuwu.errorlens.ui.inlineerrors.MyCustomTextRenderer
import com.github.ioannuwu.errorlens.ui.inlineerrors.MyGutterIconRenderer
import com.github.ioannuwu.errorlens.utils.dbg
import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.openapi.editor.markup.MarkupModel

interface ErrorModel {

    fun update()


    class Impl(
            private val markupModel: MarkupModel,
            private val mutatingFilter: MutatingHighlightersFilter,
            private val filter: HighlightersFilter,
    ) : ErrorModel {

        private val lineHighlightModel: LineHighlighterRenderer =
                LineHighlighterRendererWithSecretAttributes(markupModel)

        override fun update() {
            lineHighlightModel.clearHighlighters()
            for (highlighter in markupModel.allHighlighters) {
                if (highlighter.customRenderer is MyCustomTextRenderer) highlighter.customRenderer = null
                if (highlighter.gutterIconRenderer is MyGutterIconRenderer) highlighter.gutterIconRenderer = null
            }

            val highlighters: MutableList<MutableHighlighter> = markupModel.allHighlighters
                    .filterNotNull()
                    .filter { it.errorStripeTooltip is HighlightInfo }
                    .filter { (it.errorStripeTooltip as HighlightInfo).description != null }
                    .map { MutableHighlighter.Impl(it) }
                    .toMutableList()

            highlighters.forEach { elementInList -> filter.invoke(elementInList) }

            highlighters.forEach { elementInList -> mutatingFilter.invoke(elementInList) }

            // After filtering done
            // renderData will not change at this point
            for (highlighter in highlighters) {
                val renderData = highlighter.getRenderData() as ErrorRenderData

                if (renderData.backgroundColor != null)
                    lineHighlightModel.addHighlighter(highlighter.getLineNumber(), renderData.backgroundColor!!)

                if (renderData.gutterIcon != null)
                    highlighter.setGutterIconRenderer(MyGutterIconRenderer(renderData.gutterIcon!!))

                if (renderData.text != null && renderData.backgroundColor != null) {
                    dbg("Among us inside: $renderData")
                    highlighter.setCustomRenderer(
                            MyCustomTextRenderer(renderData.text!!,
                                    renderData.backgroundColor!!))

                }
            }
        }
    }
}