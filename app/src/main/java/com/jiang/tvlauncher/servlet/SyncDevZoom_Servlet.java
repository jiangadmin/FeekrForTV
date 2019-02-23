package com.jiang.tvlauncher.servlet;

import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jiang.tvlauncher.MyAPP;
import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.entity.BaseEntity;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.utils.HttpUtil;
import com.jiang.tvlauncher.utils.SaveUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiangadmin
 * @date: 2017/8/23.
 * @Email: www.fangmu@qq.com
 * @Phone: 186 6120 1018
 * TODO: 上传梯形数据
 */

public class SyncDevZoom_Servlet extends AsyncTask<String, Integer, BaseEntity> {
    private static final String TAG = "SyncDevZoom_Servlet";
    @Override
    protected BaseEntity doInBackground(String... strings) {
        Map map = new HashMap();
        BaseEntity entity;
        map.put("devId", SaveUtils.getString(Save_Key.ID));
        try {

//            String point = MyAppliaction.apiManager.get("getKeyStoneData", null, null);
//            LogUtil.e(TAG,point);
//            map.put("zoomVal", URLEncoder.encode(point, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            entity = new BaseEntity();
            entity.setErrorcode(-3);
            return entity;
        }
        String res = HttpUtil.doPost(Const.URL + "dev/devInfoController/syncDevZoom.do", map);
        if (res != null) {
            try {
                entity = new Gson().fromJson(res, BaseEntity.class);
            } catch (Exception e) {
                entity = new BaseEntity();
                entity.setErrorcode(-2);
            }
        } else {
            entity = new BaseEntity();
            entity.setErrorcode(-1);
        }
        return entity;
    }

    @Override
    protected void onPostExecute(BaseEntity entity) {
        super.onPostExecute(entity);
        Loading.dismiss();
        if (entity.getErrorcode() == 1000)
            Toast.makeText(MyAPP.context, "同步完成", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MyAPP.context, "同步失败", Toast.LENGTH_SHORT).show();
    }
}
