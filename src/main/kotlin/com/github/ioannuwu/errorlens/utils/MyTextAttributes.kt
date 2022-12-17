package com.github.ioannuwu.errorlens.utils

import com.intellij.openapi.editor.markup.TextAttributes

class MyTextAttributes(hint: BackGroundColor) : TextAttributes(
        null,
        hint.backgroundColor(), // TODO settings
        null,
        null,
        0
)