package com.sergstas.chequecalculator.di.modules.network

import com.sergstas.network.apiprovider.IApiProvider
import dagger.Module
import dagger.Provides

@Module
class NetworkProvidesModule {
    @Provides fun provideApi(apiProvider: IApiProvider) = apiProvider.getApi()
}