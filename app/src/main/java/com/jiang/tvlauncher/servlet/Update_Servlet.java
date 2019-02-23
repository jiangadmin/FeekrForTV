package com.jiang.tvlauncher.servlet;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.jiang.tvlauncher.MyAPP;
import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.UpdateEntity;
import com.jiang.tvlauncher.utils.HttpUtil;
import com.jiang.tvlauncher.utils.Tools;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiangadmin
 * @date: 2017/6/19.
 * @Email: www.fangmu@qq.com
 * @Phone: 186 6120 1018
 * TODO: 检查更新
 */

public class Update_Servlet extends AsyncTask<String, Integer, UpdateEntity> {
    private static final String TAG = "Update_Servlet";
    Activity activity;

    public Update_Servlet(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected UpdateEntity doInBackground(String... strings) {
        Map map = new HashMap();
        map.put("serialNum", MyAPP.SN);
        map.put("versionNum", Tools.getVersionName(MyAPP.context));
        map.put("buildNum", String.valueOf(Tools.getVersionCode(MyAPP.context)));

        String res = HttpUtil.doPost(Const.URL + "cms/appVersionController/findNewVersion.do", map);

        UpdateEntity entity;
        if (res != null) {
            try {
                entity = new Gson().fromJson(res, UpdateEntity.class);
            } catch (Exception e) {
                entity = new UpdateEntity();
                entity.setErrorcode(-2);
                entity.setErrormsg("数据解析失败");
            }
        } else {
            entity = new UpdateEntity();
            entity.setErrorcode(-1);
            entity.setErrormsg("连接服务器失败");
        }
        return entity;
    }

    @Override
    protected void onPostExecute(UpdateEntity entity) {
        super.onPostExecute(entity);
        Loading.dismiss();

        if (entity.getErrorcode() == 1000) {
            if (entity.getResult().getBuildNum() > Tools.getVersionCode(MyAPP.context)) {
                Loading.show(activity, "安装中");
                new DownUtil().downLoad(entity.getResult().getDownloadUrl(),"Feekr"+entity.getResult().getVersionNum()+".apk",true);
            }
        } else if (entity.getErrorcode() == 15) {
//            Toast.makeText(activity, TAG+entity.getErrormsg(), Toast.LENGTH_SHORT).show();
        } else {

        }
    }
}
