package com.jiang.tvlauncher.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;

import com.jiang.tvlauncher.MyAPP;
import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.servlet.DownUtil;
import com.jiang.tvlauncher.servlet.GetVIP_Servlet;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;
import com.jiang.tvlauncher.utils.ShellUtils;
import com.jiang.tvlauncher.utils.Tools;

/**
 * @author jiangyao
 * date: 2018/7/6
 * Email: www.fangmu@qq.com
 * Phone: 186 6120 1018
 * TODO:  逻辑科技调用
 */
public class AppBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "AppBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String packname = Const.TvViedo;
        //验证是否有此应用
        if (Tools.isAppInstalled_0(context,packname)) {
            //如果要启动定制版腾讯视频
            //判断是否已经运行
            if (!TextUtils.isEmpty(ShellUtils.execCommand("ps |grep com.ktcp.tvvideo:webview", false).successMsg)) {
                LogUtil.e(TAG, "直接启动");
                MyAPP.activity.startActivity(new Intent(MyAPP.activity.getPackageManager().getLaunchIntentForPackage(packname)));
            } else {
                LogUtil.e(TAG, "获取账号");
                Loading.show(MyAPP.activity, "请稍后");
                //获取VIP账号
                new GetVIP_Servlet(true).execute();
            }

        } else {
            LogUtil.e(TAG, "下载应用");
            if (TextUtils.isEmpty(SaveUtils.getString(Const.TvViedoDow))) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyAPP.activity);
                builder.setTitle("抱歉");
                builder.setMessage("资源缺失<云视听>，请联系服务人员!");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            } else {
                Loading.show(MyAPP.activity, "请稍后");
                new DownUtil().downLoad(SaveUtils.getString(Const.TvViedoDow), packname + ".apk", true);
            }
        }
    }
}
