package com.github.ioannuwu.errorlens.ui.settings.wrappers

import javax.swing.JComponent
import javax.swing.JPanel

class MyPanel(vararg components: JComponent) : JPanel() {
    init {
        components.forEach { this.add(it) }
    }
}

// TODO JBTextArea for HideList