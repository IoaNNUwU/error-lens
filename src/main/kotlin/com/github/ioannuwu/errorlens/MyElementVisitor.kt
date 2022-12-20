package com.github.ioannuwu.errorlens

import com.github.ioannuwu.errorlens.utils.ErrorData
import com.github.ioannuwu.errorlens.utils.SecretTextAttributes
import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.colors.EditorFontType
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.impl.DocumentMarkupModel
import com.intellij.openapi.editor.markup.*
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.jetbrains.rd.util.getOrCreate
import com.jetbrains.rd.util.printlnError
import java.awt.Color
import java.awt.Graphics
import javax.swing.Icon

typealias LineNumber = Int

class MyElementVisitor : PsiElementVisitor() {

    override fun visitFile(file: PsiFile) = ApplicationManager.getApplication().invokeLater {
        val markupModel = DocumentMarkupModel.forDocument(file.viewProvider.document,
                file.project, false) ?: return@invokeLater

        val allHighlightersInDocument = markupModel.allHighlighters

        val toBeProcessed = mutableMapOf<LineNumber, RangeHighlighter>()

        for (highlighter in allHighlightersInDocument) {
            if (highlighter == null) continue

            if (highlighter.isSecret()) {
                highlighter.dispose()
                continue
            }
            if (highlighter.errorStripeTooltip !is HighlightInfo) continue

            val info = highlighter.errorStripeTooltip as HighlightInfo
            if (info.description == null) continue

            val lineNumber = highlighter.document.getLineNumber(highlighter.startOffset)

            val fromMap = toBeProcessed[lineNumber]
            if (fromMap == null) {
                toBeProcessed[lineNumber] = highlighter
                continue
            }
            val fromMapPriorityLower = ((fromMap.errorStripeTooltip as HighlightInfo).severity.myVal
                    <= (highlighter.errorStripeTooltip as HighlightInfo).severity.myVal)
            if (fromMapPriorityLower) {
                fromMap.gutterIconRenderer = null
                fromMap.customRenderer = null
                toBeProcessed[lineNumber] = highlighter
            }
        }

        for ((line, highlighter) in toBeProcessed) {
            val data = ErrorData.select(highlighter.errorStripeTooltip as HighlightInfo)

            highlighter.customRenderer = MyCustomHighlighterRenderer(data)
            markupModel.addLineHighlighter(line, 500,
                    // TODO There is better way to do this
                    // TODO layer 2000 - caret row, layer 1000 - text
                    // maybe ok but caret row overrides line
                    SecretTextAttributes(data.backgroundColor()))
        }
    }
}

class MyCustomHighlighterRenderer(private val data: ErrorData) : CustomHighlighterRenderer {

    override fun paint(editor: Editor, highlighter: RangeHighlighter, g: Graphics) {
        val info = highlighter.errorStripeTooltip as HighlightInfo

        val document = editor.document
        val lineNumber = document.getLineNumber(highlighter.startOffset)
        val position = editor.offsetToXY(document.getLineEndOffset(lineNumber))

        highlighter.gutterIconRenderer = MyCustomIconRenderer(data)
        g.font = editor.colorsScheme.getFont(EditorFontType.PLAIN)
        g.color = data.textColor()

        g.drawString(
                info.description,
                position.x + CLOWN,
                position.y + editor.ascent,
        )
    }
}

fun RangeHighlighter.isSecret(): Boolean =
        SecretTextAttributes.isSecret(this.getTextAttributes(null))

class MyCustomIconRenderer(data: ErrorData) : GutterIconRenderer() {
    private val icon = data.gutterIcon()

    override fun getIcon() = icon

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Icon) return false
        return (icon == other)
    }

    override fun hashCode() = icon.hashCode()
}

private const val CLOWN = 15

// TODO filtering
// TODO settings
