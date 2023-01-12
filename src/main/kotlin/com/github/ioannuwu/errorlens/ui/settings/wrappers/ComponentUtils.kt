package com.github.ioannuwu.errorlens.ui.settings.wrappers

import javax.swing.JComponent
import javax.swing.JPanel

object ComponentUtils {
    fun createPanel(vararg components: JComponent): JPanel {
        val builder = MyFormBuilder()
        components.forEach { builder.addComponent(it) }
        builder.fillVertically()
        return builder.build()
    }
}