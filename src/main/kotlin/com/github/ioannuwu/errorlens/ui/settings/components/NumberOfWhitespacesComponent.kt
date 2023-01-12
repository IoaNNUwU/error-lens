package com.github.ioannuwu.errorlens.ui.settings.components

import com.github.ioannuwu.errorlens.ui.settings.State
import javax.swing.JTextField

class NumberOfWhitespacesComponent(numberOfWhiteSpaces: Int, private val defaultNumberOfWhitespaces: Int)
    : JTextField(numberOfWhiteSpaces.toString(), 5), State<Int> {

    override fun getState(): Int = text.toIntOrNull() ?: defaultNumberOfWhitespaces
}