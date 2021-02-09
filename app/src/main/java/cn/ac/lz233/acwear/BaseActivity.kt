package cn.ac.lz233.acwear

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.ac.lz233.acwear.util.isRound
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseActivity : AppCompatActivity() {
    val job = Job()
    val scope = CoroutineScope(job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        if (isRound) onRound()
    }

    open fun onRound() {}
}