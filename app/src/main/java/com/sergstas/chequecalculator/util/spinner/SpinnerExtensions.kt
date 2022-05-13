package com.sergstas.chequecalculator.util.spinner

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

fun Spinner.onItemSelected(f: (String) -> Unit) {
    onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            val item = p0?.getItemAtPosition(p2) as? String ?: return
            f(item)
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }
}