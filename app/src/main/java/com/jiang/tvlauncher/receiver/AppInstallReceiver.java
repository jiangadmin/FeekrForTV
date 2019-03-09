package com.jiang.tvlauncher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.servlet.GetVIP_Servlet;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;

/**
 * @author: jiangadmin
 * @date: 2017/7/3.
 * @Email: www.fangmu@qq.com
 * @Phone: 186 6120 1018
 * TODO: 应用安装/卸载广播监听
 */

public class AppInstallReceiver extends BroadcastReceiver {
    private static final String TAG = "AppInstallReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        //安装
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            Loading.dismiss();
            LogUtil.e(TAG, "安装成功");
            //如果之前被卸载过（应用自升级）
//            if (!TextUtils.isEmpty(SaveUtils.getString(Const.包)))
//                if (SaveUtils.getString(Const.包).contains(packageName)) {
//                    return;
//                }
            //自己的APP
            if ("com.jiang.tvlauncher".equals(packageName)) {
                return;
            }

            //如果要启动定制版腾讯视频
            if (packageName.equals(Const.TvViedo)) {

                //获取VIP账号,备用
                new GetVIP_Servlet(true).execute();

                return;
            }

            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            if (launchIntent != null) {
                context.startActivity(launchIntent);
            }
        }
        //卸载
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            LogUtil.e(TAG, "卸载成功");

            //清楚数据
            if (!TextUtils.isEmpty(SaveUtils.getString(Const.包)))
                if (SaveUtils.getString(Const.包).length() > 10000) {
                    SaveUtils.setString(Const.包, null);
                }

            //记录卸载过的包
            if (TextUtils.isEmpty(SaveUtils.getString(Const.包)) || !SaveUtils.getString(Const.包).contains(packageName)) {
                SaveUtils.setString(Const.包, SaveUtils.getString(Const.包) + packageName);
            }

        }
        //替换
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
//            String packageName = intent.getData().getSchemeSpecificPart();
//            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
//            if (launchIntent != null)
//                context.startActivity(launchIntent);
            LogUtil.e(TAG, "替换成功");
        }
    }
}