package com.jiang.tvlauncher.dialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jiang.tvlauncher.R;
import com.jiang.tvlauncher.activity.Base_Activity;
import com.jiang.tvlauncher.server.MyDownService;

import java.io.File;

/**
 * @author: jiangadmin
 * @date: 2015/12/16.
 * @Email: www.fangmu@qq.com
 * @Phone: 186 6120 1018
 * TODO: 下载更新
 */

public class UpdateWindow extends Base_Activity {

    ProgressBar jindutiao;
    TextView jindu;
    String fileName;

    String TEXT, URL, VERSION;
    int NEEDUPDATE;
    boolean UPTOLOGIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_update);
        initview();
        Intent intent = getIntent();
        TEXT = intent.getStringExtra("text");
        URL = intent.getStringExtra("url");
        VERSION = intent.getStringExtra("version");
        NEEDUPDATE = intent.getIntExtra("needupdate", 0);
        UPTOLOGIN = intent.getBooleanExtra("uptologin", false);

        updateok();
    }

    private void initview() {
        jindutiao = (ProgressBar) findViewById(R.id.update_jindutiao);
        jindu = (TextView) findViewById(R.id.update_jindu);

    }

    public void updateok() {
        register();
        Intent it = new Intent(this, MyDownService.class);
        fileName = URL.substring(URL.lastIndexOf("/") + 1);
        it.putExtra("msg", URL);
        startService(it);
        jindu.setText("0%");
    }


    @Override
    public void onBackPressed() {
        if (NEEDUPDATE == 0)
            return;
        super.onBackPressed();
    }

    // 退出activity时，关闭服务，取消注册广播
    @Override
    protected void onDestroy() {
        stopService(new Intent(this, MyDownService.class));
        try {
            unregisterReceiver(recceiver);
        } catch (Exception e) {
        }
        super.onDestroy();
    }

    // 注册广播
    public void register() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("progress");
        registerReceiver(recceiver, filter);
    }

    // 广播接受处理，progress进度在0-100之间，-1时表示下载已完成
    BroadcastReceiver recceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            int i = intent.getIntExtra("progress", 0);
            if (i != -1) {
                jindu.setText(i + "%");
                jindutiao.setProgress(i);
            } else {
                stopService(new Intent(UpdateWindow.this, MyDownService.class));
                jindu.setText("下载完成");
                installApk();
            }
        }

    };

    private void installApk() {

        /*********下载完成，点击安装***********/
        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + File.separator + fileName));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        /**********加这个属性是因为使用Context的startActivity方法的话，就需要开启一个新的task**********/
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        this.startActivity(intent);
    }
}
