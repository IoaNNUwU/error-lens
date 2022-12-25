package com.github.ioannuwu.errorlens.errorRender

import com.intellij.openapi.editor.markup.MarkupModel
import com.intellij.openapi.editor.markup.RangeHighlighter
import com.intellij.openapi.editor.markup.TextAttributes
import java.awt.Color

// Adds metadata to line highlighter to be able to delete it later
interface SecretLock {

    fun addSecretLineHighlighter(markupModel: MarkupModel, line: Int, backgroundColor: Color)

    fun removeSecretLineHighlighters(highlighters: Array<out RangeHighlighter>)


    object TextAttributesSecretLock : SecretLock {

        private val secretForegroundColor = null
        private val secretEffectColor = Color(234, 212, 125)
        private val secretEffectType = null
        private const val secretFontType = 832501479

        private fun isSecret(highlighter: RangeHighlighter): Boolean {
            val attributes = highlighter.getTextAttributes(null) ?: return false
            return attributes.effectType == secretEffectType
                    && attributes.foregroundColor == secretForegroundColor
                    && attributes.effectColor == secretEffectColor
                    && attributes.fontType == secretFontType
        }

        override fun removeSecretLineHighlighters(highlighters: Array<out RangeHighlighter>) =
                highlighters.forEach { if (isSecret(it)) it.dispose() }

        override fun addSecretLineHighlighter(markupModel: MarkupModel, line: Int, backgroundColor: Color) {
            markupModel.addLineHighlighter(line, 500, SecretTextAttributes(backgroundColor))
        }

        private class SecretTextAttributes(backgroundColor: Color) : TextAttributes(
                secretForegroundColor,
                backgroundColor,
                secretEffectColor,
                secretEffectType,
                secretFontType,
        )
    }
}