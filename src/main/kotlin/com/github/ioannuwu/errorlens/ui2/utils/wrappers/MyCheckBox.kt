package com.github.ioannuwu.errorlens.ui2.utils.wrappers

import javax.swing.JCheckBox

class MyCheckBox(text: String, isSelected: Boolean): JCheckBox(text, isSelected) {

    override fun toString(): String = "MyCheckBox{ isSelected = $isSelected }"
}