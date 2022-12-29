package com.github.ioannuwu.errorlens

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.impl.DocumentMarkupModel
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile

class MyElementVisitor : PsiElementVisitor() {

    override fun visitFile(file: PsiFile) = ApplicationManager.getApplication().invokeLater {

        val markupModel = DocumentMarkupModel.forDocument(file.viewProvider.document,
                file.project, false) ?: return@invokeLater


    }
}