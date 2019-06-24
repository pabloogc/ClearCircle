package com.minikorp.clearscore.core.data.network

import retrofit2.http.GET

/**
 * ClearScore API, with one one modest call.
 */
interface ClearScoreApi {
    @GET("mockcredit/values")
    suspend fun getScore(): NetworkScore
}