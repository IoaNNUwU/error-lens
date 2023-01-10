package com.github.ioannuwu.errorlens.ui2.components

import com.github.ioannuwu.errorlens.ui2.State
import javax.swing.JTextField

class NumberOfWhitespacesComponent(numberOfWhiteSpaces: Int, private val defaultNumberOfWhitespaces: Int)
    : JTextField(numberOfWhiteSpaces.toString()), State<Int> {

    override fun getState(): Int {
        val number = text.toIntOrNull()
        if (number == null) {
            text = defaultNumberOfWhitespaces.toString()
            return defaultNumberOfWhitespaces
        }
        return number
    }
}