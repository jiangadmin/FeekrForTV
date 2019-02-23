package com.jiang.tvlauncher.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.jiang.tvlauncher.MyAPP;

/**
 * Created by 垚垚
 * on 17/7/18.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose: TODO 存储工具
 */

public class SaveUtils {

    public static String PREFERENCE_NAME = "mengdou";

    /**
     * 存储 String
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean setString(String key, String value) {
        SharedPreferences settings = MyAPP.context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * 读取 String
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        SharedPreferences settings = MyAPP.context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, null);
    }

    /**
     * 存储 Int
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean setInt(String key, int value) {
        SharedPreferences settings = MyAPP.context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * 读取 Int
     *
     * @param key
     * @return
     */
    public static int getInt(String key) {
        SharedPreferences settings = MyAPP.context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, -1);

    }

    /**
     * 存储 Long
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean setLong(String key, long value) {
        SharedPreferences settings = MyAPP.context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * 读取 Long
     *
     * @param key
     * @return
     */
    public static long getLong(String key) {
        SharedPreferences settings = MyAPP.context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key, -1);
    }

    /**
     * 存储 Float
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean setFloat(String key, float value) {
        SharedPreferences settings = MyAPP.context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * 读取 Float
     *
     * @param key
     * @return
     */
    public static float getFloat(String key) {
        SharedPreferences settings = MyAPP.context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getFloat(key, -1);
    }

    /**
     * 存储 Boolean
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean setBoolean(String key, boolean value) {
        SharedPreferences settings = MyAPP.context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * 读取 Boolean
     *
     * @param key
     * @return
     */
    public static boolean getBoolean(String key) {
        SharedPreferences settings = MyAPP.context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, false);
    }
}
