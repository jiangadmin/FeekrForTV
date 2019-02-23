package com.jiang.tvlauncher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.utils.SaveUtils;
import com.jiang.tvlauncher.utils.Tools;

/**
 * @author: jiangadmin
 * @date: 2017/6/19.
 * @Email: www.fangmu@qq.com
 * @Phone: 186 6120 1018
 * TODO: 开机广播监听
 */

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

            //记录一次最近一次开机时间
            SaveUtils.setString(Save_Key.TurnOnTime, Tools.NowTime());

            //example:启动程序
//            Intent start = new Intent(context, Home_Activity.class);
//            start.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//
//            context.startActivity(start);
            //注册广播
//            registerNetworkReceiver();

        }
    }
}