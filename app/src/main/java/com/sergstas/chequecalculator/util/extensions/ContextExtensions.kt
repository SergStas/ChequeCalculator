package com.sergstas.chequecalculator.util.extensions

import android.content.Context
import com.sergstas.chequecalculator.App
import com.sergstas.chequecalculator.di.IAppComponent

fun Context.findAppComponent(): IAppComponent =
    (this.applicationContext as App).appComponent
