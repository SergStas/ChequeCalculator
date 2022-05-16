package com.sergstas.chequecalculator.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.sergstas.chequecalculator.App
import com.sergstas.chequecalculator.di.modules.vm.ViewModelModule
import dagger.Module
import dagger.Provides

@Module(includes = [
    ViewModelModule::class,
    ValidationModule::class,
    DataModule::class,
    ServicesModule::class,
])
class AppModule(private val app: App) {
    @Provides fun provideContext(): Context = app
    @Provides fun provideSharedPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(
            "com.sergstas.chequecalculator",
            Context.MODE_PRIVATE,
        )
}