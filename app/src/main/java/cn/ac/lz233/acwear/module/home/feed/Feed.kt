package cn.ac.lz233.acwear.module.home.feed

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Feed(
    @SerializedName("username") val userName: String,
    @SerializedName("userImg") val avatarUrl: String,
    @SerializedName("sign") val userSign: String,
    @SerializedName("title") val videoTitle: String,
    @SerializedName("titleImg") val videoImageUrl: String
)