package com.github.ioannuwu.errorlens.domain.linehighlighter

import com.intellij.openapi.editor.markup.MarkupModel
import com.intellij.openapi.editor.markup.RangeHighlighter
import com.intellij.openapi.editor.markup.TextAttributes
import java.awt.Color

class LineHighlighterRendererWithSecretAttributes(
        private val markupModel: MarkupModel,
): LineHighlighterRenderer {

    override fun addHighlighter(line: Int, backgroundColor: Color) {
        markupModel.addLineHighlighter(line, 500, SecretTextAttributes(backgroundColor))
    }

    override fun clearHighlighters() {
        markupModel.allHighlighters.forEach { if (it.isSecret()) it.dispose() }
    }
}

private fun RangeHighlighter.isSecret(): Boolean {
    val attributes = this.getTextAttributes(null) ?: return false
    return attributes.effectType == secretEffectType
            && attributes.foregroundColor == secretForegroundColor
            && attributes.effectColor == secretEffectColor
            && attributes.fontType == secretFontType
}

private val secretForegroundColor = null
private val secretEffectColor = Color(234, 212, 125)
private val secretEffectType = null
private const val secretFontType = 832501479

private class SecretTextAttributes(backgroundColor: Color) : TextAttributes(
        secretForegroundColor,
        backgroundColor,
        secretEffectColor,
        secretEffectType,
        secretFontType
)