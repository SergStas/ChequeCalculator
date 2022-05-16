package com.sergstas.chequecalculator.di.modules.network

import dagger.Module

@Module(includes = [
    NetworkProvidesModule::class,
    NetworkBindsModule::class
])
class NetworkModule