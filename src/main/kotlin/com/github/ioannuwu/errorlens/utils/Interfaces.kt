package com.github.ioannuwu.errorlens.utils

import java.awt.Color

interface Description {
    fun description(): String
}

interface TextColor {
    fun textColor(): Color
}

interface BackGroundColor {
    fun backgroundColor(): Color
}