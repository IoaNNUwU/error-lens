package com.github.ioannuwu.errorlens

import com.github.ioannuwu.errorlens.errorRender.HighlightersSelector
import com.github.ioannuwu.errorlens.errorRender.LineHighlightModel
import com.github.ioannuwu.errorlens.errorRender.MyCustomHighlighterRenderer
import com.github.ioannuwu.errorlens.errorRender.SecretLock
import com.github.ioannuwu.errorlens.errorRender.renderData.ErrorRenderDataSelector
import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.impl.DocumentMarkupModel
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile

class MyElementVisitor(
        private val numberOfWhitespaces: Int,
        private val highlightersSelector: HighlightersSelector,
        private val errorDataSelector: ErrorRenderDataSelector,
) : PsiElementVisitor() {

    override fun visitFile(file: PsiFile) = ApplicationManager.getApplication().invokeLater {

        val markupModel = DocumentMarkupModel.forDocument(file.viewProvider.document,
                file.project, false) ?: return@invokeLater

        val lock: SecretLock = SecretLock.TextAttributesSecretLock
        val lineHighlightModel: LineHighlightModel = LineHighlightModel.MarkupModel(markupModel, lock)

        lineHighlightModel.removeLineHighlighters()

        val highlightersToShow = highlightersSelector.select(markupModel.allHighlighters)

        for ((line, highlighter) in highlightersToShow) {

            val info = highlighter.errorStripeTooltip as HighlightInfo
            val data = errorDataSelector.select(info.severity)

            lineHighlightModel.addLineHighlighter(line, data.backgroundColor)

            highlighter.customRenderer = MyCustomHighlighterRenderer(errorDataSelector, numberOfWhitespaces)
        }
    }
}