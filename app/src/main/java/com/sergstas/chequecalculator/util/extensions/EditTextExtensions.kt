package com.sergstas.chequecalculator.util.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.setOnNewValueListener(f: (String) -> Unit) =
    addTextChangedListener(object: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            f(p0?.toString() ?: return)
        }
    })