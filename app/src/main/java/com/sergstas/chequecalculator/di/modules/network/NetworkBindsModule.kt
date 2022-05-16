package com.sergstas.chequecalculator.di.modules.network

import com.sergstas.network.apiprovider.ApiProvider
import com.sergstas.network.apiprovider.IApiProvider
import com.sergstas.network.retrofit.IRetrofitProvider
import com.sergstas.network.retrofit.RetrofitProvider
import dagger.Binds
import dagger.Module

@Module
abstract class NetworkBindsModule {
    @Binds abstract fun bindRetrofitProvider(retrofitProvider: RetrofitProvider) : IRetrofitProvider
    @Binds abstract fun bindApiProvider(apiProvider: ApiProvider) : IApiProvider
}