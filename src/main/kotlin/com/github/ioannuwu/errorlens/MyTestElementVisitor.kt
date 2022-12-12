package com.github.ioannuwu.errorlens

import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.codeInsight.daemon.impl.SeverityRegistrar
import com.intellij.icons.AllIcons
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorCustomElementRenderer
import com.intellij.openapi.editor.Inlay
import com.intellij.openapi.editor.colors.EditorFontType
import com.intellij.openapi.editor.impl.DocumentMarkupModel
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiFile
import com.intellij.refactoring.suggested.endOffset
import com.intellij.refactoring.suggested.startOffset
import com.jetbrains.rd.util.printlnError
import java.awt.Color
import java.awt.Graphics
import java.awt.Rectangle
import javax.swing.Icon

class MyTestElementVisitor(
    private val fileEditorManager: FileEditorManager,
) : PsiElementVisitor() {

    override fun visitFile(file: PsiFile) {

        ApplicationManager.getApplication().invokeLater {

            val highlighters = DocumentMarkupModel
                    .forDocument(file.viewProvider.document, file.project, false)
                    .allHighlighters

            highlighters.forEach { printlnError("it: $it") }

            val selectedEditor = fileEditorManager.selectedTextEditor
            if (selectedEditor == null) {
                printlnError("Selected editor is null")
                return@invokeLater
            }

            selectedEditor.inlayModel.getAfterLineEndElementsInRange(0, file.endOffset).forEach { it.dispose() }
            selectedEditor.markupModel.allHighlighters.forEach { it.dispose() }

            highlighters.asSequence()
                    .filterNotNull()
                    .filter { it.errorStripeTooltip is HighlightInfo }
                    .map { it.errorStripeTooltip as HighlightInfo }
                    .filter { it.severity.toString() != "SYMBOL_TYPE_SEVERITY" }
                    .forEach { info ->

                        printlnError("info: $info")
                        val startOffset = info.startOffset
                        val description = info.description ?: "Description is null"
                        val line = selectedEditor.document.getLineNumber(startOffset)

                        val hint = HintData(line, description)

                        if (selectedEditor.inlayModel.getAfterLineEndElementsForLogicalLine(line).isEmpty()) {
                            selectedEditor.inlayModel.addAfterLineEndElement(startOffset, false, hint)
                        }
                        selectedEditor.markupModel.addLineHighlighter(line, 0, MyTextAttributes(hint))

                    }
            printlnError("CLOWN ENDED")
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
    "';' expected" -> Property(AllIcons.General.Error, Color(50, 0, 0, 50), Color.ORANGE)
    else -> Property(AllIcons.General.Information, Color(255, 255, 255, 50), Color.BLUE)
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