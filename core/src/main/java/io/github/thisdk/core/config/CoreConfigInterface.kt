package io.github.thisdk.core.config

import okhttp3.Interceptor
import retrofit2.Converter
import java.util.concurrent.TimeUnit

interface CoreConfigInterface {

    fun networkConnectTimeout(): Long

    fun networkReadTimeout(): Long

    fun networkWriteTimeout(): Long

    fun networkTimeoutTimeUnit(): TimeUnit

    fun networkInterceptorList(): MutableList<Interceptor>

    fun networkServiceBaseUrl(): String

    fun retrofitConverterFactory(): Converter.Factory

}