package com.sergstas.chequecalculator.ui.newevent.models

import com.sergstas.chequecalculator.util.rv.models.AbstractItem

data class SingleButtonItem(
    val title: String,
    val buttonText: String,
    val onButtonPressed: () -> Unit,
    val onTouch: () -> Unit = {},
): AbstractItem()