package com.example.fince.core

import okhttp3.Interceptor
import okhttp3.Response

object InterceptorCustom: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val apiKey = Config.apiKey

        var request = chain.request()
        request = request.newBuilder()
            .header("Authorization", apiKey?:"")
            .header("Content-Type", "application/json")
            .build()
        return chain.proceed(request)
    }
}