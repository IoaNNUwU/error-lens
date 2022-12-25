package com.github.ioannuwu.errorlens.errorRender

import java.awt.Color

interface LineHighlightModel {

    fun addLineHighlighter(line: Int, backgroundColor: Color)

    // It is important to remove custom line highlighters on every update because
    // there is no anchors for them
    fun removeLineHighlighters()


    class MarkupModel(private val markupModel: com.intellij.openapi.editor.markup.MarkupModel, private val lock: SecretLock) : LineHighlightModel {

        override fun addLineHighlighter(line: Int, backgroundColor: Color) =
                lock.addSecretLineHighlighter(markupModel, line, backgroundColor)

        override fun removeLineHighlighters() =
                lock.removeSecretLineHighlighters(markupModel.allHighlighters)
    }
}