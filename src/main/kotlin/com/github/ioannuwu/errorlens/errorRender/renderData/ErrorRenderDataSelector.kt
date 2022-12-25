package com.github.ioannuwu.errorlens.errorRender.renderData

import com.intellij.lang.annotation.HighlightSeverity

interface ErrorRenderDataSelector {
    fun select(severity: HighlightSeverity): ErrorRenderData
}