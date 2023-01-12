package com.github.ioannuwu.errorlens.ui.settings.components

import com.github.ioannuwu.errorlens.domain.settings.HideListParser
import com.github.ioannuwu.errorlens.ui.settings.State
import javax.swing.JTextArea

class HideListComponent(hideList: List<String>, private val parser: HideListParser) :
        JTextArea(parser.parseToString(hideList), 15, 15), State<List<String>> {

    override fun getState(): List<String> = parser.parseToList(text)
}