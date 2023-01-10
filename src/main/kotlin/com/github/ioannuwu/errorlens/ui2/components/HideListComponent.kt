package com.github.ioannuwu.errorlens.ui2.components

import com.github.ioannuwu.errorlens.domain.HideListParser
import com.github.ioannuwu.errorlens.ui2.State
import javax.swing.JTextField

class HideListComponent(hideList: List<String>, private val parser: HideListParser) :
        JTextField(parser.parseToString(hideList)), State<List<String>> {

    override fun getState(): List<String> {
        val myList = parser.parseToList(text)
        text = parser.parseToString(myList)
        return myList
    }
}