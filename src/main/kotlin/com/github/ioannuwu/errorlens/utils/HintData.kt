package com.github.ioannuwu.errorlens.utils

import com.intellij.openapi.editor.EditorCustomElementRenderer
import com.intellij.openapi.editor.Inlay
import com.intellij.openapi.editor.colors.EditorFontType
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.openapi.editor.markup.TextAttributes
import java.awt.Graphics
import java.awt.Rectangle

class HintData(private val line: Int, private val description: String):
        GutterIconRenderer(),
        EditorCustomElementRenderer,
        Description,
        TextColor,
        BackGroundColor
{
    private val property = choseProperty(description)

    override fun description(): String = description

    override fun textColor() = property.textColor

    override fun backgroundColor() = property.backgroundColor

    override fun hashCode() = 31 * (31 * line + description.hashCode())
    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        if (other !is HintData) return false
        return other.hashCode() == hashCode()
    }

    override fun calcWidthInPixels(inlay: Inlay<*>) = 1

    override fun getIcon() = property.icon

    override fun paint(inlay: Inlay<*>, g: Graphics, targetRegion: Rectangle, textAttributes: TextAttributes) {
        g.font = inlay.editor.colorsScheme.getFont(EditorFontType.PLAIN)
        g.color = property.textColor
        g.drawString(
                description,
                targetRegion.x,
                targetRegion.y + inlay.editor.ascent,
        )
    }

}
