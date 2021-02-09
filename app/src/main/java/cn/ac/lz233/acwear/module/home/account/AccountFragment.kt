package cn.ac.lz233.acwear.module.home.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.ac.lz233.acwear.AcWearApp
import cn.ac.lz233.acwear.BaseFragment
import cn.ac.lz233.acwear.R
import cn.ac.lz233.acwear.module.home.HomeActivity
import cn.ac.lz233.acwear.module.login.LoginActivity
import cn.ac.lz233.acwear.util.LogUtil
import cn.ac.lz233.acwear.util.isLogin
import cn.ac.lz233.acwear.util.isRound
import cn.ac.lz233.acwear.util.safePadding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.heytap.wearable.support.widget.HeyMainTitleBar
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : BaseFragment() {

    override fun onRound() {
        super.onRound()
        accountInformationFrameLayout.setPadding(safePadding, safePadding, safePadding, 0)
        checkInItem.setPadding(safePadding, 0, safePadding, 0)
        logoutItem.setPadding(safePadding, 0, safePadding, safePadding)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onResume() {
        super.onResume()
        if (!isRound) activity?.findViewById<HeyMainTitleBar>(R.id.homeHeyMainTitleBar)?.setTitle(getString(R.string.account_title))
        initView()
    }

    private fun initView() {
        Glide.with(this).load(AcWearApp.sp.getString("userImg", "")).error(R.drawable.ic_account).apply(RequestOptions.bitmapTransform(CircleCrop())).into(avatarImageView)
        if (isLogin()) {
            accountInformationFrameLayout.setOnClickListener(null)
            userNameTextView.text = AcWearApp.sp.getString("userName", getString(R.string.login_title))
            userSummaryTextView.text = "${getString(R.string.level)} ${AcWearApp.sp.getString("userLevel", "")}"
            logoutItem.setOnClickListener {
                AcWearApp.editor.remove("userId")
                AcWearApp.editor.remove("userName")
                AcWearApp.editor.remove("userImg")
                AcWearApp.editor.remove("passToken")
                AcWearApp.editor.remove("userLevel")
                AcWearApp.editor.apply()
                activity?.finish()
                startActivity(Intent(activity, HomeActivity::class.java))
            }
        } else {
            accountInformationFrameLayout.setOnClickListener { LoginActivity.actionStart(activity!!) }
            userNameTextView.text = getText(R.string.login_title)
            userSummaryTextView.text = getText(R.string.login_summary)
            checkInItem.setOnClickListener { LogUtil.toast(getString(R.string.not_login)) }
            logoutItem.setOnClickListener { LogUtil.toast(getString(R.string.not_login)) }
        }
    }
}