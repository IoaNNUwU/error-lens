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
        fun isSecret(attributes: TextAttributes) =
                attributes.effectType == secretEffectType
                        && attributes.foregroundColor == secretForegroundColor
                        && attributes.effectColor == secretEffectColor
                        && attributes.fontType == secretFontType }
}

private val secretForegroundColor = Color(211,182,126)
private val secretEffectColor = Color(234,212,125)
private val secretEffectType = null
private const val secretFontType = 15