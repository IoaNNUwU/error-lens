package com.github.ioannuwu.errorlens

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.psi.PsiElementVisitor

class TestLocalInspectionTool: LocalInspectionTool() {

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean) =
            MyTestElementVisitor(FileEditorManager.getInstance(holder.project))
}