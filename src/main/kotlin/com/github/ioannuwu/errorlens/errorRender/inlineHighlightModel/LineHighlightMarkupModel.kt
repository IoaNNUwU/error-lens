package com.github.ioannuwu.errorlens.errorRender.inlineHighlightModel

import com.github.ioannuwu.errorlens.errorRender.secretLock.SecretLock
import com.intellij.openapi.editor.markup.MarkupModel
import java.awt.Color

class LineHighlightMarkupModel(private val markupModel: MarkupModel, private val lock: SecretLock) : LineHighlightModel {

    override fun addLineHighlighter(line: Int, backgroundColor: Color) =
            lock.addSecretLineHighlighter(markupModel, line, backgroundColor)

    override fun removeLineHighlighters() =
            lock.removeSecretLineHighlighters(markupModel.allHighlighters)
}