package io.bloco.template.domain.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

val logger = run {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.apply {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }
}

val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(logger)
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .build()