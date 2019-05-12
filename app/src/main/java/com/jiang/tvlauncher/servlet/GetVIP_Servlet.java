package com.jiang.tvlauncher.servlet;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jiang.tvlauncher.MyAPP;
import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.entity.VIP_Model;
import com.jiang.tvlauncher.utils.HttpUtil;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;
import com.jiang.tvlauncher.utils.Tools;
import com.ktcp.video.thirdagent.JsonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangyao
 * date: 2018/5/14
 * Email: www.fangmu@qq.com
 * Phone: 186 6120 1018
 * TODO: 获取会员账号
 */
public class GetVIP_Servlet extends AsyncTask<String, Integer, VIP_Model> {
    private static final String TAG = "GetVIP_Servlet";

    boolean IsOpen;

    public GetVIP_Servlet(boolean isOpen) {
        IsOpen = isOpen;
    }

    @Override
    protected VIP_Model doInBackground(String... strings) {
        Map<String, String> map = new HashMap<>();
        VIP_Model entity;
        if (!TextUtils.isEmpty(MyAPP.SN)) {
            map.put("serialNum", MyAPP.SN);
            map.put("mac", Tools.getMacAddress());
        } else {
            entity = new VIP_Model();
            entity.setErrorcode(-3);
            entity.setErrormsg("数据缺失");
        }

        String res = HttpUtil.doPost(Const.URL + "tencent/tencentVideoController/getVuidInfo.do", map);

        LogUtil.e(TAG, "╔═════════════════════════╗");
        LogUtil.e(TAG, "║              ╔════════╗                ║");
        LogUtil.e(TAG, "║              ║ ╔═════╗ ║                ║");
        LogUtil.e(TAG, "╠═══════╣ ║ 会员数据 ║ ╠════════╣");
        LogUtil.e(TAG, "║              ║ ╚═════╝ ║                ║");
        LogUtil.e(TAG, "║              ╚════════╝                ║");
        LogUtil.e(TAG, "╚═════════════════════════╝");


        LogUtil.e(TAG, res);

        //空判断
        if (res != null && res.contains(",\"result\":\"\"")) {
            res = res.replaceAll(",\"result\":\"\"", "");
        }

        if (TextUtils.isEmpty(res)) {
            entity = new VIP_Model();
            entity.setErrorcode(-1);
            entity.setErrormsg("连接服务器失败");
        } else {
            try {
                entity = new Gson().fromJson(res, VIP_Model.class);
            } catch (Exception e) {
                entity = new VIP_Model();
                entity.setErrorcode(-2);
                entity.setErrormsg("数据解析失败");
                LogUtil.e(TAG, e.getMessage());
            }
        }
        return entity;
    }

    @Override
    protected void onPostExecute(VIP_Model entity) {
        super.onPostExecute(entity);
        Loading.dismiss();

        Const.IsGetVip = true;
        if (entity.getErrorcode() == 1000) {
            HashMap<String, Object> params = new HashMap<>();

            Const.ktcp_vuid = String.valueOf(entity.getResult().getVuid());
            Const.ktcp_vtoken = entity.getResult().getVtoken();
            Const.ktcp_accessToken = entity.getResult().getAccessToken();

            params.put("vuid", entity.getResult().getVuid());
            params.put("vtoken", entity.getResult().getVtoken());
            params.put("accessToken", entity.getResult().getAccessToken());
            params.put("errTip", "");

            SaveUtils.setString(Save_Key.PARAMS, JsonUtils.addJsonValue(params));

            //启动应用
            LogUtil.e(TAG, "启动会员版");
            if (IsOpen) {
                Tools.StartApp(MyAPP.activity, Const.TvViedo);
            }
        } else {

            if (Tools.isAppInstalled_0(MyAPP.context, Const.TencentViedo)) {

                //启动应用
                LogUtil.e(TAG, "启动云视听");
                Tools.StartApp(MyAPP.activity, Const.TencentViedo);

            } else {

                if (TextUtils.isEmpty(Const.云视听Url)) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MyAPP.activity);
                    builder.setTitle("抱歉");
                    builder.setMessage("应用 云视听 资源缺失，请联系服务人员");
                    builder.setNegativeButton("确定", (dialog, which) -> dialog.dismiss());
                    builder.show();

                } else {
                    Loading.show(MyAPP.activity, "请稍后");
                    new DownUtil().downLoad(Const.云视听Url, Tools.getFileNameWithSuffix(Const.云视听Url), true);
//                    new DownUtil().downLoad(Const.云视听Url, "video.apk", true);
                }
            }
        }
    }
}
