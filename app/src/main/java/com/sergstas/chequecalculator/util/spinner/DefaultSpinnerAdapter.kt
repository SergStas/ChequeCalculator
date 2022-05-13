package com.sergstas.chequecalculator.util.spinner

import android.widget.ArrayAdapter
import com.sergstas.chequecalculator.App
import androidx.appcompat.R

class DefaultSpinnerAdapter(labels: List<String>): ArrayAdapter<String>(
    App.instance,
    R.layout.support_simple_spinner_dropdown_item,
    labels,
) {
    init {
        setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
    }
}