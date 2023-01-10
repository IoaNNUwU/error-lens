package com.github.ioannuwu.errorlens.ui2.utils.wrappers

import javax.swing.JComponent

class MyComponent(
        vararg components: MyPanel
) : JComponent() {

    init {
        val builder = MyFormBuilder()
        components.forEach { builder.addComponent(it) }
        builder.fillVertically()
        val mainComponent = builder.build()

        this.add(mainComponent)
    }
}