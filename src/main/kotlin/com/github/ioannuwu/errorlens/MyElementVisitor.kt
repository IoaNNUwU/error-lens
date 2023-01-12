package com.github.ioannuwu.errorlens

import com.github.ioannuwu.errorlens.data.AbstractDataSettingsService
import com.github.ioannuwu.errorlens.data.DataSettingsService
import com.github.ioannuwu.errorlens.domain.ErrorModel
import com.github.ioannuwu.errorlens.domain.HighlightersFilter
import com.github.ioannuwu.errorlens.domain.MutatingHighlightersFilter
import com.github.ioannuwu.errorlens.domain.highlighter.Highlighter
import com.github.ioannuwu.errorlens.domain.highlighter.MutableHighlighter
import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.impl.DocumentMarkupModel
import com.intellij.openapi.editor.markup.RangeHighlighter
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile

class MyElementVisitor : PsiElementVisitor() {

    override fun visitFile(file: PsiFile) = ApplicationManager.getApplication().invokeLater {

        val markupModel = DocumentMarkupModel.forDocument(file.viewProvider.document,
                file.project, false) ?: return@invokeLater

        val settingsService: AbstractDataSettingsService =
                ApplicationManager.getApplication().getService(DataSettingsService::class.java)

        val inlineModel = ErrorModel.Impl(markupModel,
                MyMutatingFilter(settingsService),
                MyFilter(settingsService, markupModel.allHighlighters))

        inlineModel.update()
    }
}

private class MyMutatingFilter(settingsService: AbstractDataSettingsService) : MutatingHighlightersFilter {

    private val changeNumberOfWhitespaces = MutatingHighlightersFilter.ChangeNumberOfWhitespaces(settingsService.state.numberOfWhitespaces)
    private val changeErrorsSettings = MutatingHighlightersFilter.ChangeSettings(settingsService)

    override fun invoke(highlighter: MutableHighlighter): Boolean =
            changeNumberOfWhitespaces.invoke(highlighter) and changeErrorsSettings.invoke(highlighter)
}

private class MyFilter(
        settingsService: AbstractDataSettingsService,
        highlighters: Array<out RangeHighlighter?>,
) : HighlightersFilter {

    private val byHideList = HighlightersFilter.ByHideList(settingsService.state.hideList)
    private val onePerLine = HighlightersFilter.OnePerLineWithHighestPriority(
            highlighters.filterNotNull()
                    .filter { it.errorStripeTooltip != null }
                    .filter { it.errorStripeTooltip is HighlightInfo }
                    .filter { (it.errorStripeTooltip as HighlightInfo).description != null }
                    .filter { (it.errorStripeTooltip as HighlightInfo).description.isNotBlank() }
    )

    override fun invoke(highlighter: Highlighter): Boolean =
            byHideList.invoke(highlighter) and onePerLine.invoke(highlighter)

}