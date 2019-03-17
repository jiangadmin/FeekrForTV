package com.jiang.tvlauncher.servlet;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jiang.tvlauncher.entity.Base_Model;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.utils.HttpUtil;
import com.jiang.tvlauncher.utils.SaveUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by  jiang
 * on 2017/6/19.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 关机发送
 * update：
 */

public class TurnOff_servlet extends AsyncTask<String, Integer, Base_Model> {
    @Override
    protected Base_Model doInBackground(String... strings) {
        Map<String, String> map = new HashMap<>();
        Base_Model entity;
        if (TextUtils.isEmpty(SaveUtils.getString(Save_Key.ID))) {
            entity = new Base_Model();
            entity.setErrorcode(-3);
            entity.setErrormsg("数据缺失");
        } else
            map.put("devId", SaveUtils.getString(Save_Key.ID));
        map.put("turnType", "3");

        String res = HttpUtil.doPost(Const.URL + "dev/devTurnOffController/turnOff.do", map);
        if (res != null) {
            try {
                entity = new Gson().fromJson(res, Base_Model.class);
            } catch (Exception e) {
                entity = new Base_Model();
                entity.setErrorcode(-2);
                entity.setErrormsg("数据解析失败");
            }
        } else {
            entity = new Base_Model();
            entity.setErrorcode(-1);
            entity.setErrormsg("连接服务器失败");
        }
        return entity;
    }

    @Override
    protected void onPostExecute(Base_Model entity) {
        super.onPostExecute(entity);

        SaveUtils.setBoolean("关机", true);

    }
}
