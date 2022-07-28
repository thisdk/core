package io.github.thisdk.bootstrap.config

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.github.thisdk.core.config.CoreConfigInterface
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import java.util.concurrent.TimeUnit

class AppConfig : CoreConfigInterface {

    companion object {

        const val DB_NAME = "bootstrap.db"

        const val BASE_URL = "https://www.wanandroid.com"

        const val NETWORK_TIMEOUT: Long = 15

        val NETWORK_TIMEOUT_UNIT: TimeUnit = TimeUnit.SECONDS

        var INTERCEPTOR_HTTP_LOGGING: HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        @OptIn(ExperimentalSerializationApi::class)
        val RETROFIT_CONVERTER_FACTORY: Converter.Factory =
            Json.asConverterFactory("application/json".toMediaType())

    }

    override fun networkConnectTimeout(): Long {
        return NETWORK_TIMEOUT
    }

    override fun networkReadTimeout(): Long {
        return NETWORK_TIMEOUT
    }

    override fun networkWriteTimeout(): Long {
        return NETWORK_TIMEOUT
    }

    override fun networkTimeoutTimeUnit(): TimeUnit {
        return NETWORK_TIMEOUT_UNIT
    }

    override fun networkInterceptorList(): MutableList<Interceptor> {
        return mutableListOf(INTERCEPTOR_HTTP_LOGGING)
    }

    override fun networkServiceBaseUrl(): String {
        return BASE_URL
    }

    override fun retrofitConverterFactory(): Converter.Factory {
        return RETROFIT_CONVERTER_FACTORY
    }

}