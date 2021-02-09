package cn.ac.lz233.acwear.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import com.heytap.wearable.support.widget.HeyBackTitleBar

class FuckHeyBackTitleBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : HeyBackTitleBar(context, attrs, defStyleAttr) {
    init {
        setBackListener(null, context as Activity)
    }
}