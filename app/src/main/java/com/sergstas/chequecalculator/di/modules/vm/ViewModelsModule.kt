package com.sergstas.chequecalculator.di.modules.vm

import com.sergstas.chequecalculator.vm.newevent.NewEventViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelsModule {
    @Provides
    fun provideNewEventViewModule() = NewEventViewModel()
}