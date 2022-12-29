package com.github.ioannuwu.errorlens.ui.components

import com.github.ioannuwu.errorlens.ui.components.utils.NumberOfWhitespaces
import com.intellij.ui.components.JBTextField
import com.intellij.ui.components.Label

class NumberOfWhitespacesComponent(numberOfWhitespaces: Int): MyComponent(), NumberOfWhitespaces {

    private val myNumberOfWhiteSpaces = JBTextField(numberOfWhitespaces.toString(), 5)

    init {
        add(Label("Whitespaces after line end     "))
        add(myNumberOfWhiteSpaces)
    }

    override var numberOfWhitespaces = myNumberOfWhiteSpaces.text
}