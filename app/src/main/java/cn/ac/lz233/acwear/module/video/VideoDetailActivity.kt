package cn.ac.lz233.acwear.module.video

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.ac.lz233.acwear.BaseActivity
import cn.ac.lz233.acwear.R
import cn.ac.lz233.acwear.module.login.LoginActivity

class VideoDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
    }
    companion object{
        fun actionStart(context: Context,acId:String){
            val intent = Intent(context, LoginActivity::class.java)
            intent.putExtra("acId",acId)
            context.startActivity(intent)
        }
    }
}