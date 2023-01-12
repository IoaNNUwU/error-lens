package com.github.ioannuwu.errorlens.domain.highlighter

import com.github.ioannuwu.errorlens.domain.renderdata.ErrorRenderData
import com.intellij.lang.annotation.HighlightSeverity

interface Highlighter {
    fun getLineNumber(): Int

    fun getSeverity(): HighlightSeverity

    fun getRenderData(): ErrorRenderData
}