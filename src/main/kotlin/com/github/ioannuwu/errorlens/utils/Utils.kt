package com.github.ioannuwu.errorlens.utils

import com.github.ioannuwu.errorlens.ui.settings.components.HideListComponent
import com.github.ioannuwu.errorlens.ui.settings.components.NumberOfWhitespacesComponent
import com.github.ioannuwu.errorlens.ui.settings.wrappers.MyCheckBox
import com.github.ioannuwu.errorlens.ui.settings.wrappers.MyColorPanel
import com.jetbrains.rd.util.printlnError
import org.jetbrains.annotations.TestOnly

fun <T> dbg(something: T): T {
    printlnError(something.toString())
    return something
}

fun <T> dbg(prefix: String, something: T, suffix: String = ""): T {
    printlnError(prefix + something.toString() + suffix)
    return something
}

fun <T> dbgw(something: T): T {
    println(something.toString())
    return something
}

fun <T> dbgw(prefix: String, something: T, suffix: String = ""): T {
    println(prefix + something.toString() + suffix)
    return something
}

@TestOnly
data class MyTestComponents(
        val numberOfWhitespacesComponent: NumberOfWhitespacesComponent,
        val hideListComponent: HideListComponent,
        val infoGutterCheckBox: MyCheckBox,
        val infoColorPanel: MyColorPanel)
