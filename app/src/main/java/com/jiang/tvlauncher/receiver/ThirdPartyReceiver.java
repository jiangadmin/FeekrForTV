package com.jiang.tvlauncher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.servlet.VIPCallBack_Servlet;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;
import com.ktcp.video.thirdagent.JsonUtils;
import com.ktcp.video.thirdagent.KtcpPaySdkProxy;
import com.ktcp.video.thirdagent.ThirdPartyAgent;
import com.ktcp.video.thirdagent.inter.IThirdPartyAgentListener;
import com.ktcp.video.thirdparty.IThirdPartyAuthCallback;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by v_shlicheng on 2018/4/26.
 */
@Deprecated
public class ThirdPartyReceiver extends BroadcastReceiver implements IThirdPartyAgentListener {
    private static final String TAG = "ThirdpartyReceiver";
    private Context context;

    int eveintId;
    String msg = "";

    TimeCount timeCount;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        //新增代码用来处理三方付费的回调只在一处调用
        if (KtcpPaySdkProxy.getInstance().getAgentVersion().compareTo("2.0.0") >= 0) {
            Log.i(TAG, "deal KtcpPaySDK use KtcpPaySDKCallback");
            return;
        }

        LogUtil.e(TAG, "处理登录");

        //处理登录
        if (ThirdPartyAgent.ACTION_SERVER_AGGENT.equals(intent.getAction())) {
            String channel = intent.getStringExtra("channel");
            String data = intent.getStringExtra("data");

            Log.i(TAG, "channel=" + channel + ",data=" + data);

            JSONObject dataObj = JsonUtils.getJsonObj(data);

            //启动APP
            if (dataObj.optInt("type") == ThirdPartyAgent.TYPE_LOGIN) {
                ThirdPartyAgent.getInstance().setOnThirdPartyAgentListener(this);
                ThirdPartyAgent.getInstance().doAuthLogin(channel);

            } else if (dataObj.optInt("type") == ThirdPartyAgent.TYPE_NOTICE) {
                //2=账户登录回调 3=账号退出回调  4=APP退出
                eveintId = dataObj.optInt("eventId");
                String extraJson = dataObj.optString("extra");
                int code = -1;

                if (extraJson != null && extraJson.length() > 0) {
                    JSONObject extraObj = JsonUtils.getJsonObj(extraJson);
                    code = extraObj.optInt("code");
                    msg = extraObj.optString("msg");
                }

                if (eveintId == 2) {
                    timeCount = new TimeCount(100 * 60, 1);
                    timeCount.start();
                } else {
                    if (timeCount != null)
                        timeCount.onFinish();
                }
                // 2 账户登录回调 3 退出登录 4 APP退出
                LogUtil.e(TAG, "状态码：" + eveintId);
                VIPCallBack_Servlet.TencentVip vip = new VIPCallBack_Servlet.TencentVip();

                vip.setCode(String.valueOf(code));
                vip.setMsg(msg);
                vip.setEventId(String.valueOf(eveintId));  // 2 账户登录回调 3 退出登录  4 APP退出
                new VIPCallBack_Servlet().execute(vip);

            }

            //处理支付订单
            else if (dataObj.optInt("type") == ThirdPartyAgent.TYPE_ORDER) {
                long vuid = dataObj.optLong("vuid", -1);
                if (vuid > 0) {
                    String produceId = dataObj.optString("produeceId");
                    String vuSession = dataObj.optString("vuSession");
                    ThirdPartyAgent.getInstance().setOnThirdPartyAgentListener(this);
                    ThirdPartyAgent.getInstance().doOrder(vuid, produceId, vuSession);
                }
            }
        }
    }

    @Override
    public void getAccount(String channel, final IThirdPartyAuthCallback thirdPartyAuthCallback) {
//        //fixme 由厂商实现的接口 成功获取到接口vuid,vtoken,accessToken必须通过data回调给视频客户端，需要视频处理的错误定义好提示文案放errTip中
        Toast.makeText(context, "正在为您提供会员服务", Toast.LENGTH_LONG).show();

        try {
            int vuid = 0;
            if (!TextUtils.isEmpty(Const.ktcp_vuid)) {
                vuid = Integer.getInteger(Const.ktcp_vuid, 0);
            }
            String vtoken = "get vuid error";
            String accessToken = SaveUtils.getString(Save_Key.PARAMS);

            if (TextUtils.isEmpty(accessToken)) {
                Toast.makeText(context, "未获得服务，请重试", Toast.LENGTH_LONG).show();
                return;
            }
            thirdPartyAuthCallback.authInfo(vuid, vtoken, accessToken); //data需要返回vuid,vtoken,accesssToken
        } catch (RemoteException e) {
            e.printStackTrace();
            LogUtil.e(TAG, e.getMessage());
        }

    }

    @Override
    public void getOrder(long vuid, String produceId, String vusession,
                         final IThirdPartyAuthCallback thirdPartyAuthCallback) {
        LogUtil.e(TAG, "getOrder由厂商实现的接口");
        //fixme 由厂商实现的接口 成功下单回调{"orderId":"","errTip":""}给视频客户端，需要视频处理的错误定义好提示文案放errTip中
        final HashMap<String, Object> params = new HashMap<>();
        params.put("orderId", "");
        params.put("errTip", "");
        int status = 0;// 0 获取数据成功 非0失败
        String msg = "success";
        try {
            thirdPartyAuthCallback.orderResult(status, msg, params.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
            LogUtil.e(TAG, e.getMessage());
        }
    }

    /**
     * 计时器
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture * 1000, countDownInterval * 1000);//参数依次为总时长,和计时的时间间隔
        }

        //倒计时完成
        @Override
        public void onFinish() {
            ThirdPartyAgent.getInstance().noticeClient(context, eveintId, msg);
            if (eveintId == 2)
                new TimeCount(100 * 60, 1).start();
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示

        }
    }
}

