package com.github.ioannuwu.errorlens.ui.components.errors

import com.github.ioannuwu.errorlens.ui.MyFormBuilder
import com.github.ioannuwu.errorlens.ui.components.MyComponent

class ErrorTypesComponent(errorComponents: List<ErrorTypeComponent>): MyComponent() {

    init {
        val builder = MyFormBuilder()
        errorComponents.forEach { builder.addComponent(it) }
        add(builder.build())
    }

}