package com.github.ioannuwu.errorlens.ui.inlineerrors

import com.github.ioannuwu.errorlens.utils.dbg
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.colors.EditorFontType
import com.intellij.openapi.editor.markup.CustomHighlighterRenderer
import com.intellij.openapi.editor.markup.RangeHighlighter
import java.awt.Color
import java.awt.Graphics

class MyCustomTextRenderer(
        private val text: String,
        private val color: Color
) : CustomHighlighterRenderer {

    override fun paint(editor: Editor, highlighter: RangeHighlighter, g: Graphics) {
        val document = editor.document
        val lineNumber = document.getLineNumber(highlighter.startOffset)
        val position = editor.offsetToXY(document.getLineEndOffset(lineNumber))

        g.font = editor.colorsScheme.getFont(EditorFontType.PLAIN)
        g.color = color

        g.drawString(
                text,
                position.x,
                position.y + editor.ascent,
        )

    }
}