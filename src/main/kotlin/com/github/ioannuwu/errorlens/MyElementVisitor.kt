package com.github.ioannuwu.errorlens

import com.github.ioannuwu.errorlens.utils.HintData
import com.github.ioannuwu.errorlens.utils.MyTextAttributes
import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.icons.AllIcons
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.EditorCustomElementRenderer
import com.intellij.openapi.editor.Inlay
import com.intellij.openapi.editor.colors.EditorFontType
import com.intellij.openapi.editor.impl.DocumentMarkupModel
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.refactoring.suggested.endOffset
import com.jetbrains.rd.util.printlnError
import java.awt.Color
import java.awt.Graphics
import java.awt.Rectangle
import javax.swing.Icon

class MyElementVisitor(
    private val fileEditorManager: FileEditorManager,
) : PsiElementVisitor() {

    override fun visitFile(file: PsiFile) {

        ApplicationManager.getApplication().invokeLater {

            val highlighters = DocumentMarkupModel
                    .forDocument(file.viewProvider.document, file.project, false)
                    .allHighlighters

            val selectedEditor = fileEditorManager.selectedTextEditor ?: return@invokeLater

            selectedEditor.inlayModel.getAfterLineEndElementsInRange(0, file.endOffset).forEach { it.dispose() }
            selectedEditor.markupModel.allHighlighters.forEach { it.dispose() }



            val collection: MutableSet<HighlightInfo> = mutableSetOf()

            highlighters.asSequence()
                    .filterNotNull()
                    .filter { it.errorStripeTooltip is HighlightInfo }
                    .map { it.errorStripeTooltip as HighlightInfo }
                    .filter { it.severity.toString() != SYMBOL_TYPE_SEVERITY }
                    .filter { it.description != null }
                    .toCollection(collection)

            for (info in collection) {

                printlnError(info.toString())

                val startOffset = info.startOffset
                val description = info.description!!
                val line = selectedEditor.document.getLineNumber(startOffset)

                val hint = HintData(line, description)

                if (selectedEditor.inlayModel.getAfterLineEndElementsForLogicalLine(line).isEmpty()) {
                    selectedEditor.inlayModel.addAfterLineEndElement(startOffset, false, hint)
                }
                selectedEditor.markupModel.addLineHighlighter(line, 0, MyTextAttributes(hint))

            }
            printlnError("====")
        }
    }
}

private const val SYMBOL_TYPE_SEVERITY = "SYMBOL_TYPE_SEVERITY"

// TODO priority system
// TODO settings
