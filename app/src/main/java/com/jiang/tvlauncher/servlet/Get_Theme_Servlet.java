package com.jiang.tvlauncher.servlet;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jiang.tvlauncher.MyAPP;
import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.entity.Theme_Model;
import com.jiang.tvlauncher.utils.HttpUtil;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangadmin
 * date: 2018/10/14
 * Email: www.fangmu@qq.com
 * Phone: 186 6120 1018
 * TODO: 获取显示主题
 */
public class Get_Theme_Servlet extends AsyncTask<String, Integer, Theme_Model> {
    private static final String TAG = "Get_Theme_Servlet";

    String res;

    @Override
    protected Theme_Model doInBackground(String... strings) {
        Map<String,String> map = new HashMap<>();

        map.put("serialNum", MyAPP.SN);
        map.put("devType", "3");

        res = HttpUtil.doPost(Const.URL + "cms/themeController/findLauncherTheme.do", map);

        LogUtil.e(TAG, "⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩主题设置⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩⇩");
        LogUtil.e(TAG, res);
        LogUtil.e(TAG, "⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧⇧");

        Theme_Model entity;

        if (TextUtils.isEmpty(res)) {
            entity = new Theme_Model();
            entity.setErrorcode(-1);
            entity.setErrormsg("连接服务器失败");
        } else {
            try {
                entity = new Gson().fromJson(res, Theme_Model.class);
            } catch (Exception e) {
                entity = new Theme_Model();
                entity.setErrorcode(-2);
                entity.setErrormsg("数据解析失败");
                LogUtil.e(TAG, e.getMessage());
            }
        }
        return entity;
    }

    @Override
    protected void onPostExecute(Theme_Model entity) {
        super.onPostExecute(entity);

        Loading.dismiss();

        switch (entity.getErrorcode()) {
            case 1000:
                SaveUtils.setString(Save_Key.Theme, res);
                EventBus.getDefault().post(entity);
                break;
            default:
                break;
        }
    }
}
