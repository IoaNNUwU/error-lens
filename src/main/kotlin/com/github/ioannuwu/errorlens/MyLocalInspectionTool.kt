package com.github.ioannuwu.errorlens

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.fileEditor.FileEditorManager

class MyLocalInspectionTool: LocalInspectionTool() {

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean) =
            MyElementVisitor(FileEditorManager.getInstance(holder.project))
}