package com.jiang.tvlauncher.servlet;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jiang.tvlauncher.MyAPP;
import com.jiang.tvlauncher.entity.Base_Model;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.utils.HttpUtil;
import com.jiang.tvlauncher.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangyao
 * date: 2018/5/18
 * Email: www.fangmu@qq.com
 * Phone: 186 6120 1018
 * TODO: 会员登录返回
 */
public class VIPCallBack_Servlet extends AsyncTask<VIPCallBack_Servlet.TencentVip, Integer, Base_Model> {
    private static final String TAG = "VIPCallBack_Servlet";

    @Override
    protected Base_Model doInBackground(TencentVip... tencentVips) {
        TencentVip vip = tencentVips[0];
        Map<String, String> map = new HashMap<>();
        map.put("vuid", Const.ktcp_vuid);
        map.put("vtoken", Const.ktcp_vtoken);
        map.put("serialNum", MyAPP.SN);
        map.put("code", vip.getCode());
        map.put("msg", vip.getMsg());
        map.put("eventId", vip.getEventId());

        String res = HttpUtil.doPost(Const.URL + "tencent/tencentVideoController/tencentNoticeCallBack.do", map);

        Base_Model entity;
        if (TextUtils.isEmpty(res)) {
            entity = new Base_Model();
            entity.setErrorcode(-1);
            entity.setErrormsg("连接服务器失败");
        } else {
            try {
                entity = new Gson().fromJson(res, Base_Model.class);
            } catch (Exception e) {
                entity = new Base_Model();
                entity.setErrormsg("数据解析失败");
                entity.setErrorcode(-2);
                LogUtil.e(TAG, e.getMessage());
            }
        }
        return entity;
    }

    @Override
    protected void onPostExecute(Base_Model entity) {
        super.onPostExecute(entity);
        if (entity.getErrorcode() == 1000) {

        }
    }

    public static class TencentVip {
        private String vuid;
        private String vtoken;
        private String serialNum;
        private String code;
        private String msg;
        private String eventId;

        public String getVuid() {
            return vuid;
        }

        public void setVuid(String vuid) {
            this.vuid = vuid;
        }

        public String getVtoken() {
            return vtoken;
        }

        public void setVtoken(String vtoken) {
            this.vtoken = vtoken;
        }

        public String getSerialNum() {
            return serialNum;
        }

        public void setSerialNum(String serialNum) {
            this.serialNum = serialNum;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }
    }
}
