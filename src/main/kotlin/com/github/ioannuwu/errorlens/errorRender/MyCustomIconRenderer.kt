package com.github.ioannuwu.errorlens.errorRender

import com.github.ioannuwu.errorlens.errorRender.renderData.ErrorRenderData
import com.intellij.openapi.editor.markup.GutterIconRenderer
import javax.swing.Icon

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
