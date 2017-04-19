package com.common.base.listener;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ricky on 2016/06/24.
 * <p/>
 * 头部上推，下拉的自定义touch监听器
 */
public class C_PullTouchListener implements View.OnTouchListener {

    private float startX;
    private float startY;
    private boolean hasPullUp;

    private C_TopPullCallBack callBack;
    private int duration; //动画时间

    private final Handler handler = new Handler();

    public C_PullTouchListener(int duration, C_TopPullCallBack callBack) {
        this.callBack = callBack;
        this.duration = duration;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (startX > 0 && startY > 0) {
                    float offsetX = event.getX() - startX;
                    float offsetY = event.getY() - startY;
                    if (Math.abs(offsetY) > Math.abs(offsetX)) {
                        if (offsetY < -10) {//向上滑
                            if (!hasPullUp) {
                                callBack.onTopPullUp();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        callBack.onTopPullUpComplete();
                                        hasPullUp = true;
                                    }
                                }, duration);
                            }
                        } else if (offsetY > 10) {//向下滑
                            int scrollY;
                            if (v instanceof RecyclerView) {
                                scrollY = ((LinearLayoutManager)((RecyclerView)v).getLayoutManager()).findFirstVisibleItemPosition();
                            } else {
                                scrollY = v.getScrollY();
                            }
                            if (scrollY == 0) {
                                callBack.onTopPullDown();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        callBack.onTopPullDownComplete();
                                        hasPullUp = false;
                                    }
                                }, duration);
                            }
                        }
                    }
                }
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                startX = 0;
                startY = 0;
                break;
        }
        return false;
    }
}
