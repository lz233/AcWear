package cn.ac.lz233.acwear.module.home

import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import cn.ac.lz233.acwear.AcWearApp
import cn.ac.lz233.acwear.BaseActivity
import cn.ac.lz233.acwear.R
import cn.ac.lz233.acwear.module.home.account.AccountFragment
import cn.ac.lz233.acwear.module.home.feed.FeedFragment
import cn.ac.lz233.acwear.module.video.VideoDetailActivity
import cn.ac.lz233.acwear.util.LogUtil
import cn.ac.lz233.acwear.util.isLogin
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : BaseActivity() {
    private val pageItemCount = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        AppCenter.start(application, "b5bdfb4f-15fc-4811-a738-129e50c7722c", Analytics::class.java, Crashes::class.java)
        if ((!AcWearApp.sp.getBoolean("disableBluetoothNetworkPrompt", false)) && (Settings.Global.getInt(contentResolver, "bluetooth_net_proxy_on", 0) == 1)) {
            LogUtil.toast(getString(R.string.bluetoothNetworkPrompt))
        }
        pageIndicatorView.count = pageItemCount
        viewPager2.adapter = PagerAdapter(this)
        viewPager2.offscreenPageLimit = 2
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                pageIndicatorView.onPageScrolled(position, positionOffset, positionOffsetPixels)
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
        if (!isLogin()) {
            viewPager2.currentItem = pageItemCount-1
            LogUtil.toast(getString(R.string.not_login))
        }
        VideoDetailActivity.actionStart(this,"4245875")
    }

    inner class PagerAdapter constructor(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> FeedFragment()
                else -> AccountFragment()
            }
        }

        override fun getItemCount(): Int {
            return pageItemCount
        }
    }
}