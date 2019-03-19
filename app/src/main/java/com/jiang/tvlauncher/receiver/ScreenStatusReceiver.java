package com.jiang.tvlauncher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jiang.tvlauncher.utils.LogUtil;

/**
 * @author jiangadmin
 * date: 2017/6/19.
 * Email: www.fangmu@qq.com
 * Phone: 186 6120 1018
 * TODO: 亮/熄屏广播监听
 */

public class ScreenStatusReceiver extends BroadcastReceiver {
    private static final String TAG = "ScreenStatusReceiver";
    String SCREEN_ON = "android.intent.action.SCREEN_ON";
    String SCREEN_OFF = "android.intent.action.SCREEN_OFF";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (SCREEN_ON.equals(intent.getAction())) {
            LogUtil.e(TAG, "亮屏");

        } else if (SCREEN_OFF.equals(intent.getAction())) {
            LogUtil.e(TAG, "息屏");
        }
    }
}