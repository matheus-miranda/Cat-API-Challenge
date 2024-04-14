package com.msmlabs.outcoding.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader(Constants.API_KEY, apiKey)
            .build()

        return chain.proceed(newRequest)
    }

    private object Constants {
        const val API_KEY = "x-api-key"
    }
}
