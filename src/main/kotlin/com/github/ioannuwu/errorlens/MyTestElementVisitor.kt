package com.github.ioannuwu.errorlens

import com.intellij.icons.AllIcons
import com.intellij.openapi.application.Application
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.EditorCustomElementRenderer
import com.intellij.openapi.editor.Inlay
import com.intellij.openapi.editor.colors.EditorFontType
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiErrorElement
import com.intellij.refactoring.suggested.endOffset
import com.intellij.refactoring.suggested.startOffset
import com.jetbrains.rd.util.printlnError
import java.awt.Color
import java.awt.Graphics
import java.awt.Rectangle
import javax.swing.Icon

class MyTestElementVisitor(
    private val fileEditorManager: FileEditorManager,
    private val application: Application,
) : PsiElementVisitor() {

    override fun visitErrorElement(element: PsiErrorElement) {

        ApplicationManager.getApplication().invokeLater {
            val selectedEditor = fileEditorManager.selectedTextEditor ?: return@invokeLater

            val startOffset = element.startOffset
            val endOffset = element.endOffset

            val description = element.errorDescription

            val line = selectedEditor.document.getLineNumber(startOffset)

            val hint = HintData(line, description)

            selectedEditor.inlayModel.addAfterLineEndElement(
                    startOffset,
                    false,
                    hint
            )

            selectedEditor.markupModel.addLineHighlighter(line, 0, MyTextAttributes(hint))
        }
    }
}

class MyTextAttributes(hint: BackGroundColor) : TextAttributes(
        null,
        hint.backgroundColor(), // TODO settings
        null,
        null,
        0
)

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

data class Property(val icon: Icon, val backgroundColor: Color, val textColor: Color)

fun choseProperty(description: String) = when (description) {
    "';' expected" -> Property(AllIcons.General.Error, Color.RED, Color.ORANGE)
    else -> Property(AllIcons.General.Information, Color.WHITE, Color.BLUE)
}

interface Description {
    fun description(): String
}

interface TextColor {
    fun textColor(): Color
}

interface BackGroundColor {
    fun backgroundColor(): Color
}