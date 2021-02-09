package cn.ac.lz233.acwear.util

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import cn.ac.lz233.acwear.AcWearApp
import cn.ac.lz233.acwear.BuildConfig
import cn.ac.lz233.acwear.TAG
import com.heytap.wearable.support.widget.HeyToast
import android.util.Log as ALog

object LogUtil {

    private val handler by lazy { Handler(Looper.getMainLooper()) }

    fun toast(msg: String, force: Boolean = true) {
        if (!force && (BuildConfig.BUILD_TYPE != "debug")) return
        handler.post {
            when(isRound){
                true -> Toast.makeText(AcWearApp.context,msg,Toast.LENGTH_SHORT).show()
                false -> HeyToast.showToast(AcWearApp.context, msg, HeyToast.LENGTH_SHORT)
            }
        }
    }

    @JvmStatic
    private fun doLog(f: (String, String) -> Int, obj: Any?) {
        val str = if (obj is Throwable) ALog.getStackTraceString(obj) else obj.toString()
        if (str.length > maxLength) {
            val chunkCount: Int = str.length / maxLength
            for (i in 0..chunkCount) {
                val max: Int = 4000 * (i + 1)
                if (max >= str.length) {
                    doLog(f, str.substring(maxLength * i))
                } else {
                    doLog(f, str.substring(maxLength * i, max))
                }
            }
        } else {
            f(TAG, str)
            toast(str, false)
        }
    }

    @JvmStatic
    fun d(obj: Any?) {
        doLog(ALog::d, obj)
    }

    @JvmStatic
    fun i(obj: Any?) {
        doLog(ALog::i, obj)
    }

    @JvmStatic
    fun e(obj: Any?) {
        doLog(ALog::e, obj)
    }

    @JvmStatic
    fun v(obj: Any?) {
        doLog(ALog::v, obj)
    }

    @JvmStatic
    fun w(obj: Any?) {
        doLog(ALog::w, obj)
    }

    private const val maxLength = 4000
}