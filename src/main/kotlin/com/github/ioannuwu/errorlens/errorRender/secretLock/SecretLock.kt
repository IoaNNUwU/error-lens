package com.github.ioannuwu.errorlens.errorRender.secretLock

import com.intellij.openapi.editor.markup.MarkupModel
import com.intellij.openapi.editor.markup.RangeHighlighter
import java.awt.Color

// Adds metadata to line highlighter to be able to delete it later
interface SecretLock {
    fun addSecretLineHighlighter(markupModel: MarkupModel, line: Int, backgroundColor: Color)

    fun removeSecretLineHighlighters(highlighters: Array<out RangeHighlighter>)
}