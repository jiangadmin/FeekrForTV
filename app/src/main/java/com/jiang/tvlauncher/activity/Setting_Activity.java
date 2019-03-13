package com.jiang.tvlauncher.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.jiang.tvlauncher.MyAPP;
import com.jiang.tvlauncher.R;
import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.servlet.Update_Servlet;
import com.jiang.tvlauncher.utils.FileUtils;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.ShellUtils;
import com.jiang.tvlauncher.utils.Tools;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;

/**
 * @author jiangadmin
 * date: 2017/7/3.
 * Email: www.fangmu@qq.com
 * Phone: 186 6120 1018
 * TODO: 控制台
 */

public class Setting_Activity extends Base_Activity implements View.OnClickListener {
    private static final String TAG = "Setting_Activity";

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, Setting_Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        MyAPP.activity = this;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("存储信息");
                String message = "";
                message += "共存储文件：" + FileUtils.getFileList(new File(Const.FilePath));
                message += "\n总体积：" + FileUtils.formatFileSize(FileUtils.getDirSize(new File(Const.FilePath)));
                builder.setMessage(message);
                builder.setPositiveButton("清除", (dialogInterface, i) -> {
                    LogUtil.e(TAG, "清除成功");
                    FileUtils.deleteFile(new File(Const.FilePath));
                    Toast.makeText(getApplicationContext(), "清除成功", Toast.LENGTH_SHORT).show();
                });
                builder.show();
            } catch (Exception e) {
                CrashReport.postCatchedException(e);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //网络设置
            case R.id.setting_1:
                ShellUtils.execCommand("am start -n com.tianci.setting/com.tianci.setting.activity.ConnectSetting\n", false);
                break;
            //图像声音
            case R.id.setting_2:
                ShellUtils.execCommand("am start -n com.tianci.setting/com.tianci.setting.activity.PictureSoundSetting\n", false);
                break;
            //系统设置
            case R.id.setting_3:
                ShellUtils.execCommand("am start -n com.tianci.setting/com.tianci.setting.activity.GeneralSetting\n", false);
//                ShellUtils.execCommand("am start -n com.skyworth_gz.hotel/com.skyworth_gz.hotel.runtime.HotelActivity\n", false);
                break;   //系统设置
            case R.id.setting_4:
//                ShellUtils.execCommand("am start -n com.tianci.setting/com.tianci.setting.activity.GeneralSetting\n", false);
                ShellUtils.execCommand("am start -n com.skyworth_gz.hotel/com.skyworth_gz.hotel.runtime.HotelActivity\n", false);
                break;
            //检测更新
            case R.id.setting_5:
                Loading.show(this, "检查更新");
                new Update_Servlet(this).execute();
                break;

        }
    }
}
