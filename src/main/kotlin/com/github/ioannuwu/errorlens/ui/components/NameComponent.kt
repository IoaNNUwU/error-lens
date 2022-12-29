package com.github.ioannuwu.errorlens.ui.components

import com.intellij.ui.components.Label

class NameComponent: MyComponent() {
    init {
        add(Label("Error lens settings", bold = true))
    }
}