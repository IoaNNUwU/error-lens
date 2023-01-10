package com.github.ioannuwu.errorlens

import com.jetbrains.rd.util.printlnError

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

