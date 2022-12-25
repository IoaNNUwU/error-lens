package com.github.ioannuwu.errorlens.errorRender.highligtersSelector

import com.intellij.openapi.editor.markup.RangeHighlighter

interface HighlightersSelector {
    fun select(highlighters: Array<out RangeHighlighter?>): Map<LineNumber, RangeHighlighter>
}