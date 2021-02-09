package cn.ac.lz233.acwear.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;

import java.lang.reflect.Method;

public class ObservableScrollView extends NestedScrollView {

    private ScrollViewListener scrollViewListener = null;

    {
        //fuckEnableOverScroll(true);
        setOverScrollMode(2);
    }

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void fuckEnableOverScroll(boolean b) {
        try {
            Class<?> scrollViewClass = ScrollView.class;
            Method getSetEnableOverScroll = scrollViewClass.getMethod("setEnableOverScroll", boolean.class);
            getSetEnableOverScroll.setAccessible(true);
            getSetEnableOverScroll.invoke(this, b);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    //重写滚动方法
    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScroll(x, y, oldx, oldy);
        }
    }

    //设置接口
    public interface ScrollViewListener {
        void onScroll(int x, int y, int oldX, int oldY);
    }

}