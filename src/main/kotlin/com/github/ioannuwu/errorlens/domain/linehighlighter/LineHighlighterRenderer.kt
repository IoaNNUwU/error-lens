package com.github.ioannuwu.errorlens.domain.linehighlighter

import com.intellij.openapi.editor.markup.MarkupModel
import java.awt.Color

interface LineHighlighterRenderer {

    fun addHighlighter(line: Int, backgroundColor: Color)

    fun clearHighlighters()
}