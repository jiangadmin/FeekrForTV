package com.jiang.tvlauncher.utils;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 描述:
 *
 * @Author: rain.tang
 * @Maintainer: rain.tang
 * @Date: Created by Rain.tang on 2017/10/25
 * @Copyright: www.xgimi.com All rights reserved
 */

public class WifiApUtils {
    private static final String TAG = "WifiApUtils";

    private static WifiManager mWifiManager;
    private static WifiApUtils wifiApUtils;

    public static WifiApUtils getInstance(Context context) {
        if (wifiApUtils == null) {
            synchronized (WifiApUtils.class) {
                wifiApUtils = new WifiApUtils(context);
            }
        }
        return wifiApUtils;
    }

    private WifiApUtils(Context context) {
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    public boolean checkWifiApStatus() {
        Method method2 = null;
        try {
            method2 = mWifiManager.getClass().getDeclaredMethod("getWifiApState");
            int state = (int) method2.invoke(mWifiManager);
            //通过放射获取 WIFI_AP的开启状态属性
            Field field = mWifiManager.getClass().getDeclaredField("WIFI_AP_STATE_ENABLED");
            //获取属性值
            int value = (int) field.get(mWifiManager);
            //判断是否开启
            if (state == value) {
                Log.d("Rain.tang", "AP_is_openning");
                return true;
            } else {
                Log.d("Rain.tang", "AP_is_closing");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //调用getWifiApState() ，获取返回值
        return false;
    }

    public static boolean closeWifiAp() {
        Method method = null;
        boolean result;
        try {
            method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            method.setAccessible(true);
            result = (boolean) method.invoke(mWifiManager, null, false);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Rain.tang", "close_WifiAp_Exception : " + e.getLocalizedMessage());
            return false;
        }
    }

    public static boolean openWifiAp(String apName, String apPassWord) {
        closeWifiAp();
        colseWifi();
        Method method = null;
        if (apName.equals("") || apName == null || apPassWord == null || apPassWord.length() < 8 || apPassWord.length() > 32) {
            return false;
        }
        boolean reslut;
        try {
            method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            method.setAccessible(true);
            WifiConfiguration config = new WifiConfiguration();
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.SSID = apName;
            config.preSharedKey = apPassWord;
            reslut = (boolean) method.invoke(mWifiManager, config, true);
            return reslut;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Rain.tang", "openWifiAp_Exception : " + e.getLocalizedMessage());
            return false;
        }
    }

    private static void colseWifi() {
        if (mWifiManager != null) {
            mWifiManager.setWifiEnabled(false);
        }
    }

    public WifiConfiguration getWifiApConfiguration() {
        Method method = null;
        try {
            method = mWifiManager.getClass().getMethod("getWifiApConfiguration");
            method.setAccessible(true);
            WifiConfiguration config = (WifiConfiguration) method.invoke(mWifiManager);
            return config;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Rain.tang", "close_WifiAp_Exception : " + e.getLocalizedMessage());
            return null;
        }
    }


    /**
     * 自定义wifi热点
     *
     * @param enabled 开启or关闭
     * @return
     */
    public static boolean setWifiApEnabled(Context context, boolean enabled, String ssid, String pwd) {
        LogUtil.e(TAG, "WIFIAP:" + enabled);
        boolean result = false;
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (enabled) {
            //wifi和热点不能同时打开，所以打开热点的时候需要关闭wifi
            if (wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(false);
            }
        }
        try {
        LogUtil.e(TAG, "WIFIAP:" + enabled);
            //热点的配置类
            WifiConfiguration apConfig = new WifiConfiguration();
            LogUtil.e(TAG, "WIFIAP:" + enabled);
            //配置热点的名称
            apConfig.SSID = ssid;
            LogUtil.e(TAG, "WIFIAP:" + enabled);
            //配置热点的密码，至少八位
            apConfig.preSharedKey = pwd;
            LogUtil.e(TAG, "WIFIAP:" + enabled);
            apConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            LogUtil.e(TAG, "WIFIAP:" + enabled);
            //配置热点安全性选项
            apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            LogUtil.e(TAG, "WIFIAP:" + enabled);
            //通过反射调用设置热点
            Method method = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            LogUtil.e(TAG, "WIFIAP:" + enabled);
            //返回热点打开状态
            result = (Boolean) method.invoke(wifiManager, apConfig, enabled);
            LogUtil.e(TAG, "WIFIAP:" + enabled);
            if (!result) {
                LogUtil.e(TAG, "WIFIAP创建成功" );
                Toast.makeText(context, "热点创建失败", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            LogUtil.e(TAG, "WIFIAP创建失败" +e.getMessage());
            Toast.makeText(context, "热点创建失败", Toast.LENGTH_SHORT).show();

        }
        return result;
    }
}
