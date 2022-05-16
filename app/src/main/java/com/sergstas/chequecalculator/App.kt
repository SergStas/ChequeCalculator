package com.sergstas.chequecalculator

import android.app.Application
import com.sergstas.chequecalculator.di.DaggerIAppComponent
import com.sergstas.chequecalculator.di.IAppComponent
import com.sergstas.chequecalculator.di.modules.AppModule

class App: Application() {
    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: IAppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerIAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}