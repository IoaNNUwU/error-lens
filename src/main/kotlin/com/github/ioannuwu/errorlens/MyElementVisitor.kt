package com.github.ioannuwu.errorlens

import com.github.ioannuwu.errorlens.errorRender.highligtersSelector.HighlightersSelector
import com.github.ioannuwu.errorlens.errorRender.inlineHighlightModel.LineHighlightMarkupModel
import com.github.ioannuwu.errorlens.errorRender.inlineRenderer.MyCustomHighlighterRenderer
import com.github.ioannuwu.errorlens.errorRender.renderData.ErrorRenderData
import com.github.ioannuwu.errorlens.errorRender.renderData.ErrorRenderDataSelector
import com.github.ioannuwu.errorlens.errorRender.renderData.ErrorRenderDataSelectorImpl
import com.github.ioannuwu.errorlens.errorRender.secretLock.TextAttributesSecretLock
import com.github.ioannuwu.errorlens.gui.MySettingsService
import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.impl.DocumentMarkupModel
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import javax.swing.Icon

class MyElementVisitor(
        private val numberOfWhitespaces: Int,
        private val highlightersSelector: HighlightersSelector,
        private val errorDataSelector: ErrorRenderDataSelector,
) : PsiElementVisitor() {

    override fun visitFile(file: PsiFile) = ApplicationManager.getApplication().invokeLater {

        val markupModel = DocumentMarkupModel.forDocument(file.viewProvider.document,
                file.project, false) ?: return@invokeLater

        val lock = TextAttributesSecretLock()
        val lineHighlightModel = LineHighlightMarkupModel(markupModel, lock)

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

class MyCustomIconRenderer(data: ErrorRenderData) : GutterIconRenderer() {
    private val icon = data.gutterIcon

    override fun getIcon() = icon

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Icon) return false
        return (icon == other)
    }

    override fun hashCode() = icon.hashCode()
}
