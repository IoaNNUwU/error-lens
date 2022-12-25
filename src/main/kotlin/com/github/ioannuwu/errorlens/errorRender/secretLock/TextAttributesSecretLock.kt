package com.github.ioannuwu.errorlens.errorRender.secretLock

import com.intellij.openapi.editor.markup.MarkupModel
import com.intellij.openapi.editor.markup.RangeHighlighter
import com.intellij.openapi.editor.markup.TextAttributes
import java.awt.Color

class TextAttributesSecretLock: SecretLock {

    override fun removeSecretLineHighlighters(highlighters: Array<out RangeHighlighter>) {

        highlighters
                .filter { it.isSecret() }
                .forEach { it.dispose() }
    }

    override fun addSecretLineHighlighter(markupModel: MarkupModel, line: Int, backgroundColor: Color) {
        markupModel.addLineHighlighter(line, 500, SecretTextAttributes(backgroundColor))
    }
}

private fun RangeHighlighter.isSecret(): Boolean {
    val attributes = this.getTextAttributes(null) ?: return false

    return attributes.effectType == secretEffectType
            && attributes.foregroundColor == secretForegroundColor
            && attributes.effectColor == secretEffectColor
            && attributes.fontType == secretFontType
}

private class SecretTextAttributes(backgroundColor: Color): TextAttributes(
        secretForegroundColor,
        backgroundColor,
        secretEffectColor,
        secretEffectType,
        secretFontType,
)

private val secretForegroundColor = null
private val secretEffectColor = Color(234,212,125)
private val secretEffectType = null
private const val secretFontType = 832501479