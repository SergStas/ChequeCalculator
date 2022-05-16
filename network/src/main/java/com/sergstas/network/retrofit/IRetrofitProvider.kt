package com.sergstas.network.retrofit

import retrofit2.Retrofit

interface IRetrofitProvider {
    fun getClient(baseUrl: String): Retrofit
}