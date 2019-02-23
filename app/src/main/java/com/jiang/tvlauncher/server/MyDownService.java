package com.jiang.tvlauncher.server;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyDownService extends Service {

    /**
     * 从服务器下载apk
     */
    public File getFileFromServer(String path, ProgressDialog pd) throws Exception {
        // 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            // 获取到文件的大小
            pd.setMax(conn.getContentLength() / 1024);
            InputStream is = conn.getInputStream();

            File file = new File(Environment.getExternalStorageDirectory().getPath()
                    + "/blibao/merchant", "i_blibao_shop.apk");
            //判断文件夹是否被创建
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                // 获取当前下载量
                pd.setProgress(total / 1024);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }


    private static final String TAG = "MyDownService";

    String msg = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        msg = intent.getStringExtra("msg").trim();
        System.out.println(msg);
        new Thread() {

            public void run() {
                download();
            }
        }.start();

        return super.onStartCommand(intent, flags, startId);
    }

    // 根据下载的文件名，新建文件
    public File getNewFile(String msg) {
        String fileName = msg.substring(msg.lastIndexOf("/") + 1);
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();

        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    // 对于下载的进度通过广播跟新ui
    public void download() {
        HttpURLConnection con = null;
        InputStream in = null;
        OutputStream out = null;
        int fileLength = -1;
        int progress = 0;
        byte[] buf;
        Intent it = new Intent();
        it.setAction("progress");

        try {
            URL url = new URL(msg);
            con = (HttpURLConnection) url.openConnection();
            fileLength = con.getContentLength();
            if (fileLength != -1) {
                buf = new byte[1024];
                in = con.getInputStream();
                out = new FileOutputStream(getNewFile(msg));
                int size = 0;
                while ((size = in.read(buf)) != -1) {
                    out.write(buf, 0, size);
                    progress = progress + size;
                    it.putExtra("progress", progress * 100 / fileLength);
                    sendBroadcast(it);
                }
                it.putExtra("progress", -1);
                sendBroadcast(it);
            } else {
                Log.e(TAG, "网址有误");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
