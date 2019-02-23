package com.jiang.tvlauncher.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.jiang.tvlauncher.FeekrApiManager;
import com.jiang.tvlauncher.utils.LogUtil;

/**
 * @author: jiangyao
 * @date: 2018/7/6
 * @Email: www.fangmu@qq.com
 * @Phone: 186 6120 1018
 * TODO:
 */
public class FeekrService extends Service {
    private static final String TAG = "FeekrService";

    public FeekrService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyIBinder();
    }

    class MyIBinder extends FeekrApiManager.Stub{

        @Override
        public void StartTencentVideo() throws RemoteException {
            LogUtil.e(TAG,"发送广播");
            sendBroadcast(new Intent("FEEKR"));
        }
    }
}
