package com.jiang.tvlauncher.servlet;

import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.FindChannelList;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.utils.HttpUtil;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangadmin
 * date: 2017/8/9.
 * Email: www.fangmu@qq.com
 * Phone: 186 6120 1018
 * TODO: 获取可显示应用列表
 */

public class FindChannelList_Servlet extends AsyncTask<String, Integer, FindChannelList> {
    private static final String TAG = "FindChannelList_Servlet";

    public static int num = 1;
    String res;

    TimeCount timeCount = new TimeCount(3000, 1000);

    @Override
    protected FindChannelList doInBackground(String... strings) {
        Map<String, String> map = new HashMap<>();
        FindChannelList channelList;
        if (TextUtils.isEmpty(SaveUtils.getString(Save_Key.ID))) {
            channelList = new FindChannelList();
            channelList.setErrorcode(-3);
            channelList.setErrormsg("数据缺失");
            return channelList;
        }

        map.put("devId", SaveUtils.getString(Save_Key.ID));
        res = HttpUtil.doPost(Const.URL + "cms/channelController/findChannelList.do", map);

        LogUtil.e(TAG, "╔═════════════════════════╗");
        LogUtil.e(TAG, "║              ╔════════╗                ║");
        LogUtil.e(TAG, "║              ║ ╔═════╗ ║                ║");
        LogUtil.e(TAG, "╠═══════╣ ║ 栏目信息 ║ ╠════════╣");
        LogUtil.e(TAG, "║              ║ ╚═════╝ ║                ║");
        LogUtil.e(TAG, "║              ╚════════╝                ║");
        LogUtil.e(TAG, "╚═════════════════════════╝");

        LogUtil.e(TAG, res);

        if (TextUtils.isEmpty(res)) {
            channelList = new FindChannelList();
            channelList.setErrorcode(-1);
            channelList.setErrormsg("连接服务器失败");
        } else {

            try {
                channelList = new Gson().fromJson(res, FindChannelList.class);
            } catch (Exception e) {
                channelList = new FindChannelList();
                channelList.setErrorcode(-2);
                channelList.setErrormsg("数据解析失败");
                LogUtil.e(TAG, e.getMessage());
            }
        }
        return channelList;
    }

    @Override
    protected void onPostExecute(FindChannelList channelList) {
        super.onPostExecute(channelList);
        Loading.dismiss();

        switch (channelList.getErrorcode()) {
            case 1000:
                SaveUtils.setString(Save_Key.Channe, res);

                EventBus.getDefault().post(channelList);

                break;

            case -3:
                if (Const.FindChannelList < 5) {
                    Const.FindChannelList++;
                    timeCount.start();
                }
                break;
            case -1:
                EventBus.getDefault().post(new Gson().fromJson(SaveUtils.getString(Save_Key.Channe), FindChannelList.class));
                break;
        }

    }

    /**
     * 计时器
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        //倒计时完成
        @Override
        public void onFinish() {
            //再次启动
            new FindChannelList_Servlet().execute();
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示

        }
    }
}
