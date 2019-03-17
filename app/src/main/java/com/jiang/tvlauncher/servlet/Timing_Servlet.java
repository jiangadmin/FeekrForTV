package com.jiang.tvlauncher.servlet;

import android.content.Intent;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.jiang.tvlauncher.MyAPP;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.MonitorRes_Model;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.utils.FileUtils;
import com.jiang.tvlauncher.utils.HttpUtil;
import com.jiang.tvlauncher.utils.SaveUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by  jiang
 * on 2017/6/19.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 定时发送
 * update：
 */
public class Timing_Servlet extends AsyncTask<String, Integer, MonitorRes_Model> {

    @Override
    protected MonitorRes_Model doInBackground(String... infos) {
        Map<String, String> map = new HashMap<>();
        map.put("devId", SaveUtils.getString(Save_Key.ID));
        map.put("netSpeed", "1");
        map.put("storage", FileUtils.getRomSize());
        map.put("memoryInfo", FileUtils.getAvailMemory());
        map.put("avaSpace", FileUtils.getFreeDiskSpaceS());
        map.put("cpuTemp", String.valueOf(MyAPP.Temp));
        map.put("fanSpeed", String.valueOf(MyAPP.WindSpeed));

        String res = HttpUtil.doPost(Const.URL + "dev/devRunStateController/monitorRunState.do", map);
        MonitorRes_Model entity;
        if (res != null) {
            try {
                entity = new Gson().fromJson(res, MonitorRes_Model.class);
            } catch (Exception e) {
                entity = new MonitorRes_Model();
                entity.setErrorcode(-2);
                entity.setErrormsg("数据解析失败");
            }

        } else {
            entity = new MonitorRes_Model();
            entity.setErrorcode(-1);
            entity.setErrormsg("连接服务器失败");
        }
        return entity;
    }

    @Override
    protected void onPostExecute(MonitorRes_Model entity) {
        super.onPostExecute(entity);
        switch (entity.getErrorcode()) {
            case 1000:
                Const.BussFlag = entity.getResult().getBussFlag();
                if (Const.BussFlag == 0) {

                    Intent backHome = new Intent(Intent.ACTION_MAIN);

                    backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    backHome.addCategory(Intent.CATEGORY_HOME);

                    MyAPP.context.startActivity(backHome);

                }
                EventBus.getDefault().post(String.valueOf(entity.getResult().getBussFlag()));
                break;
        }
    }
}
