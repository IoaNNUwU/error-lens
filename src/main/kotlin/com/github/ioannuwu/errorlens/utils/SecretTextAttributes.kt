package com.github.ioannuwu.errorlens.utils

import com.intellij.openapi.editor.markup.EffectType
import com.intellij.openapi.editor.markup.TextAttributes
import java.awt.Color

class SecretTextAttributes(backgroundColor: Color): TextAttributes(
        secretForegroundColor,
        backgroundColor,
        secretEffectColor,
        secretEffectType,
        secretFontType,
) {

    companion object {
        fun isSecret(attributes: TextAttributes?): Boolean {
            if (attributes == null) return false
            return attributes.effectType == secretEffectType
                    && attributes.foregroundColor == secretForegroundColor
                    && attributes.effectColor == secretEffectColor
                    && attributes.fontType == secretFontType
        }
    }
}

private val secretForegroundColor = null
private val secretEffectColor = Color(234,212,125)
private val secretEffectType = null
private const val secretFontType = 832501479