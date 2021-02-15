package cn.ac.lz233.acwear.module.network

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginAPI {

    @GET("rest/pc-direct/qr/start?type=WEB_LOGIN")
    fun startScanProcess(@Query("_") timeStamp: String): Call<JsonObject>

    @GET("rest/pc-direct/qr/scanResult")
    fun getScanResult(@Query("qrLoginToken") qrLoginToken: String, @Query("qrLoginSignature") qrLoginSignature: String, @Query("_") timeStamp: String): Call<JsonObject>

    @GET("rest/pc-direct/qr/acceptResult")
    fun getAcceptResult(@Query("qrLoginToken") qrLoginToken: String, @Query("qrLoginSignature") qrLoginSignature: String, @Query("_") timeStamp: String): Call<JsonObject>
}