package cn.ac.lz233.acwear.module.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import cn.ac.lz233.acwear.AcWearApp
import cn.ac.lz233.acwear.BaseActivity
import cn.ac.lz233.acwear.R
import cn.ac.lz233.acwear.util.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity() {
    override fun onRound() {
        super.onRound()
        qrImageView.setPadding(safePadding, safePadding, safePadding, safePadding)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //init
        val scanNetworkService = NetworkService.scanService
        scope.launch {
            try {
                val response = NetworkService.scanService.startScanProcess(System.currentTimeMillis().toString()).await()
                runOnUiThread { qrImageView.setImageBitmap(QRCodeUtil.stringToBitmap(response.get("imageData").asString)) }
                val scanCallback=NetworkService.scanService.getScanResult(response.get("qrLoginToken").asString,response.get("qrLoginSignature").asString,System.currentTimeMillis().toString()).await()
                val acceptCallback=NetworkService.scanService.getAcceptResult(response.get("qrLoginToken").asString,scanCallback.get("qrLoginSignature").asString,System.currentTimeMillis().toString()).await()
                AcWearApp.editor.putString("userId", acceptCallback.get("userId").asString)
                AcWearApp.editor.putString("userName", acceptCallback.get("ac_username").asString)
                AcWearApp.editor.putString("userImg", acceptCallback.get("ac_userimg").asString)
                AcWearApp.editor.putString("passToken", acceptCallback.get("acPasstoken").asString)
                AcWearApp.editor.apply()
                val personalInfo=NetworkService.mainService.getPersonalInfo().await()
                AcWearApp.editor.putString("userLevel", personalInfo.getAsJsonObject("info").get("level").asString)
                AcWearApp.editor.apply()
                finish()
            }catch (e:Throwable){
                LogUtil.d(e)
                LogUtil.toast(getString(R.string.login_timeout))
                finish()
            }
        }
    }

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}