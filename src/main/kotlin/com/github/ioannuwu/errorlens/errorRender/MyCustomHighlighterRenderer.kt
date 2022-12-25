package com.github.ioannuwu.errorlens.errorRender

import com.github.ioannuwu.errorlens.errorRender.renderData.ErrorRenderDataSelector
import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.colors.EditorFontType
import com.intellij.openapi.editor.markup.CustomHighlighterRenderer
import com.intellij.openapi.editor.markup.RangeHighlighter
import java.awt.Graphics

class MyCustomHighlighterRenderer(
        private val selector: ErrorRenderDataSelector,
        private val numberOfWhitespaces: Int
) : CustomHighlighterRenderer {

    override fun paint(editor: Editor, highlighter: RangeHighlighter, g: Graphics) {
        val info = highlighter.errorStripeTooltip as HighlightInfo

        val document = editor.document
        val lineNumber = document.getLineNumber(highlighter.startOffset)
        val position = editor.offsetToXY(document.getLineEndOffset(lineNumber))

        val data = selector.select(info.severity)

        highlighter.gutterIconRenderer = MyCustomIconRenderer(data)
        g.font = editor.colorsScheme.getFont(EditorFontType.PLAIN)
        g.color = data.textColor

        g.drawString(
                info.description,
                position.x + numberOfWhitespaces * editor.colorsScheme.editorFontSize,
                position.y + editor.ascent,
        )
    }
}