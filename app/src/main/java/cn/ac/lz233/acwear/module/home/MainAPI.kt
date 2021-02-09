package cn.ac.lz233.acwear.module.home

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MainAPI {
    @GET("rest/pc-direct/user/personalInfo")
    fun getPersonalInfo(): Call<JsonObject>

    @GET("rest/pc-direct/feed/followFeed?isGroup=0&gid=-1")
    fun getFeedList(@Query("count") count: String, @Query("pcursor") cursor: String): Call<JsonObject>
}