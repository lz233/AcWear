package cn.ac.lz233.acwear

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.lcodecore.tkrefreshlayout.footer.BallPulseView
import com.lcodecore.tkrefreshlayout.footer.LoadingView
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView
import com.lcodecore.tkrefreshlayout.header.bezierlayout.BezierLayout
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout

class AcWearApp : Application() {
    companion object {
        lateinit var context: Context
        lateinit var sp: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        sp = context.getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)
        editor = sp.edit()
        //TwinklingRefreshLayout.setDefaultHeader(SinaRefreshView::class.java.name)
        TwinklingRefreshLayout.setDefaultFooter(LoadingView::class.java.name)
    }
}