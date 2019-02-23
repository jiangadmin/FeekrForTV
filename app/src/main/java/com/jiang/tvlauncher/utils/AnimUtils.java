package com.jiang.tvlauncher.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by 垚垚
 * on 15/6/24.
 * Email: jiangyaoyao@chinarb.cn
 * Phone：18605296932
 * Purpose: 动画类
 */
public class AnimUtils {

    /**
     * 左右摇晃
     *
     * @param v
     */
    public static void animyaohuang(View v) {
        ObjectAnimator animator0 = ObjectAnimator.ofFloat(v, "translationX", 0, 20);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(v, "translationX", 20, -20);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(v, "translationX", -20, 20);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(v, "translationX", 20, -20);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(v, "translationX", -20, 20);
        ObjectAnimator animator5 = ObjectAnimator.ofFloat(v, "translationX", 20, -20);
        ObjectAnimator animator6 = ObjectAnimator.ofFloat(v, "translationX", -20, 20);
        ObjectAnimator animator7 = ObjectAnimator.ofFloat(v, "translationX", 20, -20);
        ObjectAnimator animator8 = ObjectAnimator.ofFloat(v, "translationX", -20, 20);
        ObjectAnimator animator9 = ObjectAnimator.ofFloat(v, "translationX", 20, 0);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator0, animator1, animator2, animator3, animator4, animator5, animator6, animator7, animator8, animator9);
        set.setDuration(30);
        set.start();
    }

    /**
     * 原点旋转
     *
     * @param v
     * @param from
     * @param to
     */
    public static void R(View v, float from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "rotation", from, to);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator);
        set.setDuration(100);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }

    /**
     * Y 轴移动
     *
     * @param v
     * @param from
     * @param to
     */
    public static void Y(View v, float from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationY", from, to);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator);
        set.setDuration(100);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }

    /**
     * X 轴移动
     *
     * @param v
     * @param from
     * @param to
     */
    public static void X(View v, int from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationX", from, to);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator);
        set.setDuration(100);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }

    /**
     * Z 轴移动
     *
     * @param v
     * @param from
     * @param to
     */
    public static void Z(View v, float from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationZ", from, to);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator);
        set.setDuration(100);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }

    /**
     * X 轴旋转
     *
     * @param v
     * @param from
     * @param to
     */
    public static void RX(View v, float from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "rotationX", from, to);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator);
        set.setDuration(100);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }

    /**
     * Y 轴旋转
     *
     * @param v
     * @param from
     * @param to
     */
    public static void RY(View v, float from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "rotationY", from, to);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator);
        set.setDuration(100);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }

    /**
     * Y 轴缩放
     *
     * @param v
     * @param from
     * @param to
     */
    public static void SY(View v, float from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "scaleY", from, to);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator);
        set.setDuration(100);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }

    /**
     * X 轴缩放
     *
     * @param v
     * @param from
     * @param to
     */
    public static void SX(View v, float from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "scaleX", from, to);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator);
        set.setDuration(100);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }

    /**
     * 缩放
     *
     * @param v
     * @param from
     * @param to
     */
    public static void S(View v, float from, float to) {
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(v, "scaleY", from, to);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(v, "scaleX", from, to);
        AnimatorSet set = new AnimatorSet();
        set.play(scaleX).with(scaleY);//两个动画同时开始
        set.setDuration(100);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }
}
