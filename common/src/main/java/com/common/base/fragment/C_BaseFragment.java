package com.common.base.fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.common.R;
import com.common.app.C_AppManager;
import com.common.base.listener.C_LifeCycleListener;
import com.common.util.C_L;
import com.common.widget.C_TranslucentNavigationBar;
import com.common.widget.C_TranslucentStatusBar;

/**
 * Created by ricky on 2016/05/16.
 * <p/>
 * 自定义的基类fragment
 * <p/>
 * Translucent 在  activity 中实现；
 */
public abstract class C_BaseFragment extends Fragment {

    // 一旦退出后台将标记，onResume的时候知道该生命周期是否由回到前台触发
    protected boolean isFromForeground = false;

    protected View rootView;
    protected FrameLayout rootContainer;
    protected LinearLayout rootTop;
    protected LinearLayout rootBottom;
    protected ImageView ivCover;
    protected C_TranslucentStatusBar statusBar;
    protected C_TranslucentNavigationBar navigationBar;

    protected C_LifeCycleListener lifeCycleListener;

    protected C_LifeCycleListener getLifeCycleListener() {
        return null;
    }

    /**
     * @return XML 布局文件id;
     */
    public int getLayoutResId() {
        return R.layout.c_fragment_base;
    }

    /**
     * @return view
     */
    public View getLayout() {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        this.lifeCycleListener = getLifeCycleListener();
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
        if (lifeCycleListener != null) {
            lifeCycleListener.onCreate(getContext(), false, savedInstanceState);
        }
        if (savedInstanceState != null) {
            // FIXME: 2016/5/19 Activity 回收重启时 ；fragment没有被销毁！
        } else {
        }
        C_L.d("fragment", "[onCreate]" + this);
    }


    /**
     * 注意  如果 onCreateView 在切换时回调要考虑的问题 ： 是否重建View;是否已经有Parent View;
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int resId;
        if ((resId = getLayoutResId()) != 0) {
            try {
                rootView = inflater.inflate(resId, container, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            rootView = getLayout();
        }
        rootContainer = (FrameLayout) rootView.findViewById(R.id.container);
        rootTop = (LinearLayout) rootView.findViewById(R.id.top);
        rootBottom = (LinearLayout) rootView.findViewById(R.id.bottom);
        ivCover = (ImageView) rootView.findViewById(R.id.iv_cove);
        return rootView;
    }


    // 替换Fragment（View层）
    protected void replace(Fragment fragment) {
        if (null == fragment) {
            return;
        }
        getChildFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).commitAllowingStateLoss();
    }

    // 隐藏Fragment（View层）
    protected void hideFragment(Fragment fragment) {
        if (null == fragment) {
            return;
        }
        getChildFragmentManager().beginTransaction()
                .hide(fragment).commitAllowingStateLoss();
    }

    // 显示Fragment（View层）
    protected void showFragment(Fragment fragment) {
        if (null == fragment) {
            return;
        }
        getChildFragmentManager().beginTransaction()
                .show(fragment)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commitAllowingStateLoss();
    }


    public void showIvCover(boolean visible) {
        if (ivCover != null) {
            ivCover.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onStart() {
        if (lifeCycleListener != null) {
            lifeCycleListener.onStart(getContext(), false, isFromForeground, getPageName());
        }
        super.onStart();
    }

    @Override
    public void onResume() {
        if (lifeCycleListener != null) {
            lifeCycleListener.onResume(getContext(), false, isFromForeground, getPageName());
        }
        super.onResume();
        C_L.i("fragment", "[C_BaseFragment::onResume] this = " + this);
        if (!isFromForeground) {
            //app 从后台唤醒，进入前台
            isFromForeground = true;
        }
    }

    @Override
    public void onPause() {
        if (lifeCycleListener != null) {
            lifeCycleListener.onPause(getContext(), false, isFromForeground, getPageName());
        }
        super.onPause();
        C_L.i("fragment", "[C_BaseFragment::onPause] this = " + this);
    }

    @Override
    public void onStop() {
        if (lifeCycleListener != null) {
            lifeCycleListener.onStop(getContext(), false, isFromForeground, getPageName());
        }
        super.onStop();
        C_L.i("fragment", "[C_BaseFragment::onStop] this = " + this);
        if (!C_AppManager.getInstance().isAppOnForeground(getContext())) {
            //app 进入后台

            //全局变量isActive = false 记录当前已经进入后台
            // 为什么不用Activity的呢，因为fragment的生命周期比Activity慢
            isFromForeground = false;
        }
    }

    protected CharSequence getPageName() {
        return null;
    }


    //==================6.0运行时获取权限==========================//

    protected boolean onPermissionsCheckAndRequest(String[] permissions, int requestCode) {
        boolean has_permission = true;
        if (ContextCompat.checkSelfPermission(getActivity(), permissions[0])
                != PackageManager.PERMISSION_GRANTED) {

            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions[0])) {
                has_permission = false;
                ActivityCompat.requestPermissions(getActivity(), permissions, requestCode);
            }
        }
        return has_permission;
    }
}
