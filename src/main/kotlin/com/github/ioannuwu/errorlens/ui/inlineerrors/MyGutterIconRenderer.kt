package com.github.ioannuwu.errorlens.ui.inlineerrors

import com.intellij.openapi.editor.markup.GutterIconRenderer
import javax.swing.Icon

class MyGutterIconRenderer(private val gutterIcon: Icon) : GutterIconRenderer() {

    override fun equals(other: Any?): Boolean = gutterIcon == other

    override fun hashCode(): Int = gutterIcon.hashCode()

    override fun getIcon(): Icon = gutterIcon
}