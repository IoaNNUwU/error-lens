package com.github.ioannuwu.errorlens.errorRender.inlineHighlightModel

import java.awt.Color

interface LineHighlightModel {

    fun addLineHighlighter(line: Int, backgroundColor: Color)

    // It is important to remove custom line highlighters on every update because
    // there is no anchors for them
    fun removeLineHighlighters()
}