package com.github.ioannuwu.errorlens.ui.settings.wrappers

import com.intellij.ui.ColorPanel
import java.awt.Color

class MyColorPanel(color: Color): ColorPanel() {
    init {
        this.selectedColor = color
    }

    override fun getSelectedColor(): Color {
        return super.getSelectedColor()!!
    }

    override fun toString(): String = "MyColorPanel{ selectedColor = $selectedColor}"
}