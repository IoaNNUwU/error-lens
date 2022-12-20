package com.github.ioannuwu.errorlens

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.editor.impl.DocumentMarkupModel
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.rd.util.printlnError

class MyLocalInspectionTool: LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean) = MyElementVisitor()
}
