package com.github.ioannuwu.errorlens.ui2.utils.wrappers

import javax.swing.JComponent
import javax.swing.JPanel

class MyPanel(vararg components: JComponent) : JPanel() {
    init {
        components.forEach { this.add(it) }
    }
}