package io.github.thisdk.bootstrap.data

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface TestService {

    @POST("/")
    suspend fun index(@Body hello: String): ResponseBody

}