package com.sergstas.network.apiprovider

import com.sergstas.network.IApi
import com.sergstas.network.keys.ApiUrl
import com.sergstas.network.retrofit.IRetrofitProvider
import javax.inject.Inject

class ApiProvider @Inject constructor(
    private val retrofitProvider: IRetrofitProvider
): IApiProvider {
    override fun getApi(): IApi =
        retrofitProvider.getClient(ApiUrl.get())
            .create(IApi::class.java)
}