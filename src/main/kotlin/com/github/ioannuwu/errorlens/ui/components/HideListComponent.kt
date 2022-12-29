package com.github.ioannuwu.errorlens.ui.components

import com.github.ioannuwu.errorlens.domain.HideListParser
import com.github.ioannuwu.errorlens.ui.components.utils.HideList
import com.intellij.ui.components.JBTextField

class HideListComponent(hideList: List<String>, parser: HideListParser): MyComponent(), HideList {

    private val myHideList: JBTextField

    init {
        val hideString = parser.parseToString(hideList)
        myHideList = JBTextField(hideString, 35)
    }

    override var hideList: String =
            myHideList.text
}