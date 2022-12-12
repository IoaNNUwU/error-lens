package com.github.ioannuwu.errorlens.utils

import com.intellij.icons.AllIcons
import java.awt.Color
import javax.swing.Icon

data class Property(val icon: Icon, val backgroundColor: Color, val textColor: Color)

fun choseProperty(description: String) = when (description) {
    else -> Property(AllIcons.General.Information, Color(255, 255, 255, 20), Color(200, 200, 200))
}