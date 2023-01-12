package com.github.ioannuwu.errorlens.domain

import com.github.ioannuwu.errorlens.domain.highlighter.Highlighter
import com.github.ioannuwu.errorlens.domain.highlighter.MutableHighlighter
import com.github.ioannuwu.errorlens.utils.HideList
import com.github.ioannuwu.errorlens.utils.LineNumber
import com.github.ioannuwu.errorlens.utils.dbg
import com.github.ioannuwu.errorlens.utils.dbgw
import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.openapi.editor.markup.RangeHighlighter

// Those filters cannot modify Highlighters
interface HighlightersFilter : (Highlighter) -> Boolean {

    override fun invoke(highlighter: Highlighter): Boolean

    class OnePerLineWithHighestPriority(highlighters: List<RangeHighlighter>) : HighlightersFilter {
        private val myMap = mutableMapOf<LineNumber, RangeHighlighter>()

        init {
            for (highlighter in highlighters) {
                val lineNumber = highlighter.document.getLineNumber(highlighter.startOffset)

                val fromMap = myMap[lineNumber]
                if (fromMap == null) {
                    myMap[lineNumber] = highlighter
                    continue
                }

                val fromMapPriorityIsLower = ((fromMap.errorStripeTooltip as HighlightInfo).severity.myVal
                        <= ((highlighter.errorStripeTooltip as HighlightInfo).severity.myVal))

                if (fromMapPriorityIsLower) {
                    // fromMap.setGutterIconRenderer(null)
                    // fromMap.setCustomRenderer(null)
                    myMap[lineNumber] = highlighter
                }
            }
        }

        override fun invoke(highlighter: Highlighter): Boolean {
            val lineNumber = highlighter.getLineNumber()
            val fromMap = myMap[lineNumber] ?: return false // error("myMap should contain that element")
            if (highlighter.getRenderData().notEquals(fromMap)) return false
            return true
        }
    }

    class ByHideList(private val hideList: HideList) : HighlightersFilter {

        override fun invoke(highlighter: Highlighter): Boolean {
            val description = highlighter.getRenderData().text ?: return false

            dbg(description)
            dbg(hideList)
            if (description.containsAny(hideList)) return false
            dbg("Among us")

            return true
        }
    }
}

private fun String.containsAny(list: Collection<String>): Boolean {
    val thisString = this

    var flag = false
    for (text in list) {
        if (thisString.contains(text)) flag = true
        dbgw("desc: $thisString")
        dbgw("list: $list")
        dbgw("text: $text")
        dbgw("flag = $flag")
        dbgw("\n")
    }
    return flag
}











































