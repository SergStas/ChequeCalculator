package com.sergstas.chequecalculator

import android.app.Application
import com.sergstas.chequecalculator.di.DaggerIAppComponent
import com.sergstas.chequecalculator.di.IAppComponent

class App: Application() {
    lateinit var appComponent: IAppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerIAppComponent.create()
    }
}