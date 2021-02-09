package cn.ac.lz233.acwear.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import cn.ac.lz233.acwear.AcWearApp
import cn.ac.lz233.acwear.R
import kotlin.math.roundToInt
import kotlin.math.sqrt

val isRound by lazy { AcWearApp.context.getString(R.string.is_round).toBoolean() }
val safePadding by lazy {
    val windowManager = AcWearApp.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val metrics = DisplayMetrics()
    //AcWearApp.context.display.getRealMetrics(metrics)
    windowManager.defaultDisplay.getMetrics(metrics)
    val width = metrics.widthPixels
    ((width - width / sqrt(2.0)) / 2).roundToInt()
}
fun isLogin():Boolean=!AcWearApp.sp.getString("passToken","").equals("")
fun dp2px(context: Context, dpValue: Float): Int = (dpValue * context.resources.displayMetrics.density + 0.5f).toInt()
fun sp2px(context: Context, spValue: Float): Int = (spValue * context.resources.displayMetrics.scaledDensity + 0.5f).toInt()