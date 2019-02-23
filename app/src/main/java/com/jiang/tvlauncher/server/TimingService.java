package com.jiang.tvlauncher.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.servlet.Timing_Servlet;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by  jiang
 * on 2017/7/4.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 定时服务
 * update：
 */

public class TimingService extends Service {
    private static final String TAG = "MyService";

    Timer timer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        if (SaveUtils.getInt(Save_Key.Timming) == 0)
            timer.schedule(new RemindTask(), 1000, 5 * 60 * 1000);
        else
            timer.schedule(new RemindTask(), 1000, SaveUtils.getInt(Save_Key.Timming));
        LogUtil.e(TAG, "启动服务");

    }

    class RemindTask extends TimerTask {
        public void run() {
            new Timing_Servlet().execute();
            LogUtil.e(TAG, "定时服务");
            timer.purge();
        }
    }
}
