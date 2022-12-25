package com.github.ioannuwu.errorlens.appearance

interface ErrorTypeList<T> {

    val ERROR: T
    val WARNING: T
    val WEAK_WARNING: T
    val INFORMATION: T
    val OTHER: T
}