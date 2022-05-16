package com.sergstas.network.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitProvider @Inject constructor(): IRetrofitProvider {
    private var retrofit: Retrofit? = null

    override fun getClient(baseUrl: String): Retrofit =
        retrofit ?: run {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit!!
        }
}