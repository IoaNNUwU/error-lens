package com.github.ioannuwu.errorlens

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.colors.EditorFontType
import com.intellij.openapi.editor.impl.DocumentMarkupModel
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.refactoring.suggested.endOffset
import com.jetbrains.rd.util.printlnError
import java.awt.Color

class MyElementVisitor(
    private val fileEditorManager: FileEditorManager,
) : PsiElementVisitor() {

    override fun visitFile(file: PsiFile) {

        ApplicationManager.getApplication().invokeLater {
            val selectedEditor = fileEditorManager.selectedTextEditor ?: return@invokeLater

            selectedEditor.inlayModel.getAfterLineEndElementsInRange(0, file.endOffset).forEach { it.dispose() }

            DocumentMarkupModel.forDocument(file.viewProvider.document, file.project, false).allHighlighters.asSequence()
                    .filterNotNull()
                    .forEach { it.setCustomRenderer { editor, highlighter, g ->
                        g.font = editor.colorsScheme.getFont(EditorFontType.PLAIN)
                        g.color = Color.RED

                        val document = editor.document
                        val lineNumber = document.getLineNumber(highlighter.startOffset)
                        val lineEndOffset = document.getLineEndOffset(lineNumber)

                        val position = editor.offsetToXY(lineEndOffset)

                        g.drawString(
                                highlighter.errorStripeTooltip.toString(),
                                position.x + CLOWN,
                                position.y + editor.ascent,
                        )
                    } }

            selectedEditor.markupModel.allHighlighters.forEach {
                printlnError("""
                    real: $it
                """.trimIndent())
            }
        }

    }
}

private const val CLOWN = 15
private const val SYMBOL_TYPE_SEVERITY = "SYMBOL_TYPE_SEVERITY"

// TODO priority system
// TODO settings
