package cn.ac.lz233.acwear

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import cn.ac.lz233.acwear.util.isRound
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

abstract class BaseFragment : Fragment() {
    val job = Job()
    val scope = CoroutineScope(job)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isRound) onRound()
    }

    open fun onRound() {}
}