package com.github.ioannuwu.errorlens.appearance

import com.intellij.icons.AllIcons
import javax.swing.Icon

object MyIconsList: ErrorTypeList<Icon> {

    override val ERROR: Icon = AllIcons.General.Error

    override val WARNING: Icon = AllIcons.General.Warning

    override val WEAK_WARNING: Icon = AllIcons.General.Warning

    override val INFORMATION: Icon = AllIcons.General.Information

    override val OTHER: Icon = AllIcons.General.ArrowUp
}