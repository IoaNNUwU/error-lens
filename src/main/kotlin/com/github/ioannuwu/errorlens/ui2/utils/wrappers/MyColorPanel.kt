package com.github.ioannuwu.errorlens.ui2.utils.wrappers

import com.intellij.ui.ColorPanel
import java.awt.Color

class MyColorPanel(color: Color): ColorPanel() {
    init {
        this.selectedColor = color
    }

    override fun toString(): String = "MyColorPanel{ selectedColor = $selectedColor}"
}