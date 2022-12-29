package com.github.ioannuwu.errorlens.ui

import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

class MyFormBuilder {

    private val builder = FormBuilder.createFormBuilder()!!

    fun addComponent(component: JComponent) {
        builder.addComponent(component)
        builder.addLabeledComponent(component, JPanel())
    }

    fun fillVertically() {
        builder.addComponentFillVertically(JPanel(), 0)
    }

    fun build() = builder.panel!!
}