package com.common.util;

import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by 谢治娴 on 2016/2/19.
 * 使用ViewCompat可以将属性动画兼容到3.0以下版本
 */
public class C_AnimationHelper {
    private final static float FPS = 30;
    private final static long REFRESH_INTERVAL = (long) (1000f / FPS);
    private final static Handler handler = new Handler();

    public final static int ACTION_ALPHA     = 1;
    public final static int ACTION_Y         = 2;
    public final static int ACTION_X         = 3;
    public final static int ACTION_ROTATE    = 4;


    public final static _AccelerateDecelerateInterpolator accelerateDecelerateInterpolator   = new _AccelerateDecelerateInterpolator();
    public final static _LinearInterpolator linearInterpolator                               = new _LinearInterpolator();
    public final static _DecelerateInterpolator decelerateInterpolator                       = new _DecelerateInterpolator();

//    private static void buildALphaRunnable(final View view, final float startValue, final float progress, final float range,
//                                           final float interval, final Interpolator interpolator, final AnimationCallback animationCallback){
//        if (progress >= 1f) {
//            if (animationCallback != null) animationCallback.callback();
//            return;
//        }
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ViewCompat.setAlpha(view,interpolator._getInterpolation(startValue + (progress / (float) 1 * range)));
//                buildALphaRunnable(view, startValue, progress + interval, range, interval, interpolator,animationCallback);
//            }
//        }, REFRESH_INTERVAL);
//    }
//
//    private static void buildYRunnable(final View view, final float startValue, final float progress, final float range,
//                                       final float interval, final Interpolator interpolator, final AnimationCallback animationCallback){
//        if (progress >= 1f) {
//            if (animationCallback != null) animationCallback.callback();
//            return;
//        }
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ViewCompat.setY(view, interpolator._getInterpolation(startValue + progress / (float) 1) * range);
//                buildALphaRunnable(view, startValue, progress + interval, range, interval, interpolator, animationCallback);
//            }
//        }, REFRESH_INTERVAL);
//    }
//
//
//    public final static void startAnimation(View view, float startValue, float endValue ,int interval,Interpolator interpolator, int actionType){
//        startAnimation(view, startValue, endValue, interval, interpolator, actionType, null);
//    }
//
//
//    public final static void startAnimation(View view, float startValue, float endValue ,int interval,Interpolator interpolator, int actionType,AnimationCallback animationCallback){
//        if (view == null) {
//            throw new IllegalArgumentException(new StringBuilder("[C_AnimationHelper::startAnimation]: illegeal view = ").append(view).toString());
//        }
//        if (interpolator == null) {
//            interpolator = linearInterpolator;
//        }
//        switch (actionType) {
//            case ACTION_ALPHA:
//                buildALphaRunnable(view, startValue, 0, endValue - startValue, (FPS / interval),interpolator,animationCallback);
//                break;
//            case ACTION_Y:
//                buildYRunnable(view, startValue, 0, endValue - startValue, (FPS / interval),interpolator,animationCallback);
//            default:
//                break;
//        }
//
//    }

    private static void buildALphaRunnable(final float startValue, final float progress, final float range,
                                           final float interval, final Interpolator interpolator, final AnimationCallback animationCallback,
                                           final View... views
                                           ){
        if (progress >= 1f) {
            if (animationCallback != null) animationCallback.callback();
            return;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (View view:views) {
                    ViewCompat.setAlpha(view, interpolator._getInterpolation(startValue + (progress / (float) 1 * range)));
                }
                buildALphaRunnable( startValue, progress + interval, range, interval, interpolator,animationCallback,views);
            }
        }, REFRESH_INTERVAL);
    }

    private static void buildYRunnable (final float startValue, final float progress, final float range,
                                             final float interval, final Interpolator interpolator, final AnimationCallback animationCallback,
                                             final View... views
    ){
        if (progress >= 1f) {
            if (animationCallback != null) animationCallback.callback();
            return;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (View view:views) {
                    ViewCompat.setY(view, interpolator._getInterpolation(startValue + progress / (float) 1) * range);
                }
                buildYRunnable(startValue, progress + interval, range, interval, interpolator, animationCallback, views);
            }
        }, REFRESH_INTERVAL);
    }



    private static void buildXRunnable (final float startValue, final float progress, final float range,
                                        final float interval, final Interpolator interpolator, final AnimationCallback animationCallback,
                                        final View... views
    ){
        if (progress >= 1f) {
            if (animationCallback != null) animationCallback.callback();
            return;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (View view:views) {
                    ViewCompat.setX(view, interpolator._getInterpolation(progress / (float) 1) * range + startValue);
                }
                buildXRunnable(startValue, progress + interval, range, interval, interpolator, animationCallback,views);
            }
        }, REFRESH_INTERVAL);
    }



    private static void buildRotateRunnable (final float startValue, final float progress, final float range,
                                        final float interval, final Interpolator interpolator, final AnimationCallback animationCallback,
                                        final View... views
    ){
        if (progress >= 1f) {
            if (animationCallback != null) animationCallback.callback();
            return;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (View view:views) {
                    ViewCompat.setRotation(view, interpolator._getInterpolation(progress / (float) 1) * range + startValue);
                }
                buildRotateRunnable(startValue, progress + interval, range, interval, interpolator, animationCallback,views);
            }
        }, REFRESH_INTERVAL);
    }

    public final static void startAnimation( float startValue, float endValue ,int interval,Interpolator interpolator, int actionType,View... views){
        startAnimation(startValue,endValue,interval,interpolator,actionType,null,views);
    }


    public final static void startAnimation( float startValue, float endValue ,int interval,Interpolator interpolator, int actionType,AnimationCallback animationCallback,View... views){
        if (views == null) {
            throw new IllegalArgumentException(new StringBuilder("[C_AnimationHelper::startAnimation]: illegeal views = ").append(views).toString());
        }
        if (interpolator == null) {
            interpolator = linearInterpolator;
        }
        switch (actionType) {
            case ACTION_ALPHA:
                buildALphaRunnable( startValue, 0, endValue - startValue, (FPS / interval),interpolator,animationCallback,views);
                break;
            case ACTION_Y:
                buildYRunnable( startValue, 0, endValue - startValue, (FPS / interval), interpolator, animationCallback, views);
                break;
            case ACTION_X:
                buildXRunnable( startValue, 0, endValue - startValue, (FPS / interval), interpolator, animationCallback, views);
                break;
            case ACTION_ROTATE:
                buildRotateRunnable( startValue, 0, endValue - startValue, (FPS / interval), interpolator, animationCallback, views);
                break;
            default:
                break;
        }

    }


    public interface AnimationCallback{
        void callback();
    }

    public interface Interpolator {
        float  _getInterpolation(float var);
    }
    public static class _AccelerateDecelerateInterpolator extends AccelerateDecelerateInterpolator implements Interpolator {
        @Override
        public float _getInterpolation(float var) {
            return getInterpolation(var);
        }
    }

    public static class _LinearInterpolator extends LinearInterpolator implements Interpolator {
        @Override
        public float _getInterpolation(float var) {
            return getInterpolation(var);
        }
    }

    public static class _DecelerateInterpolator extends DecelerateInterpolator implements  Interpolator{
        @Override
        public float _getInterpolation(float var) {
            return getInterpolation(var);
        }
    }

}
