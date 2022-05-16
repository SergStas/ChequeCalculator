package com.sergstas.network.apiprovider

import com.sergstas.network.IApi

interface IApiProvider {
    fun getApi(): IApi
}