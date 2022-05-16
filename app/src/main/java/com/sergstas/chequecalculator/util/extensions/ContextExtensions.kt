package com.sergstas.chequecalculator.util.extensions

import android.content.Context
import android.widget.Toast
import com.sergstas.chequecalculator.App
import com.sergstas.chequecalculator.di.IAppComponent

fun Context.findAppComponent(): IAppComponent =
    (this.applicationContext as App).appComponent

fun Context.toast(msg: String) =
    Toast.makeText(this.applicationContext, msg, Toast.LENGTH_LONG).show()
