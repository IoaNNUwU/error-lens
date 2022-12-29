package com.github.ioannuwu.errorlens.ui.components.errors

import com.github.ioannuwu.errorlens.ui.components.MyComponent

class ErrorTypesComponent(errorComponents: List<ErrorTypeComponent>): MyComponent() {

    init {
        errorComponents.forEach { this.add(it) }
    }

}