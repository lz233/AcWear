package cn.ac.lz233.acwear.util

import cn.ac.lz233.acwear.AcWearApp
import cn.ac.lz233.acwear.module.home.MainAPI
import cn.ac.lz233.acwear.module.login.LoginAPI
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLEncoder
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object NetworkService {
    val mainService by lazy { create(MainAPI::class.java, mainUrl) }
    val scanService by lazy { create(LoginAPI::class.java, scanUrl) }

    private const val mainUrl = "https://www.acfun.cn/"
    private const val scanUrl = "https://scan.acfun.cn/"
    private val okHttpClient =
        OkHttpClient().newBuilder().connectTimeout(500, TimeUnit.SECONDS).addInterceptor {
            if (AcWearApp.sp.getString("passToken", "") != "") {
                val request = it.request().newBuilder().addHeader(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"
                ).addHeader(
                    "Cookie", "auth_key=${
                        AcWearApp.sp.getString(
                            "userId", ""
                        )
                    }; " + "ac_username=${
                        URLEncoder.encode(
                            AcWearApp.sp.getString("userName", ""), "utf-8"
                        )
                    }; " + "ac_userimg=${
                        AcWearApp.sp.getString(
                            "userImg", ""
                        )
                    }; " + "acPasstoken=${
                        AcWearApp.sp.getString(
                            "passToken", ""
                        )
                    }; " + "clientlanguage=${Locale.getDefault().language}_${Locale.getDefault().country}"
                ).build()
                it.proceed(request)
            } else {
                val request = it.request().newBuilder().addHeader(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"
                    //"${BuildConfig.APPLICATION_ID} ${BuildConfig.VERSION_NAME} ${BuildConfig.VERSION_CODE}"
                ).build()
                it.proceed(request)
            }

        }.build()

    private fun <T> create(serviceClass: Class<T>, baseUrl: String): T =
        Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build().create(serviceClass)
}

suspend fun <T> Call<T>.await(): T {
    return suspendCoroutine { continuation ->
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                if (body != null) continuation.resume(body)
                else continuation.resumeWithException(RuntimeException("response body is null"))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
    }
}