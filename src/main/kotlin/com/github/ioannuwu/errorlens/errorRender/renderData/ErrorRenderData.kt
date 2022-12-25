package com.github.ioannuwu.errorlens.errorRender.renderData

import java.awt.Color
import javax.swing.Icon

interface ErrorRenderData {
    val backgroundColor: Color
    val textColor: Color
    val gutterIcon: Icon
}