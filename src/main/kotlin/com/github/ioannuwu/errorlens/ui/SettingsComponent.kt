package com.github.ioannuwu.errorlens.ui

import com.github.ioannuwu.errorlens.ui.components.MyComponent

class SettingsComponent(
        componentList: List<MyComponent>
) : MyComponent() {

    private val builder = MyFormBuilder()

    init {
        componentList.forEach { builder.addComponent(it) }
        builder.fillVertically()

        add(builder.build())
    }
}