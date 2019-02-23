package com.jiang.tvlauncher;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.text.TextUtils;

import com.TvTicketTool.TvTicketTool;
import com.android.aidl.IUserInterfaceService;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.Point;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.servlet.TurnOn_servlet;
import com.jiang.tvlauncher.servlet.VIPCallBack_Servlet;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;
import com.jiang.tvlauncher.utils.Tools;
import com.ktcp.video.ktsdk.TvTencentSdk;
import com.ktcp.video.thirdagent.JsonUtils;
import com.ktcp.video.thirdagent.KtcpContants;
import com.ktcp.video.thirdagent.KtcpPaySDKCallback;
import com.ktcp.video.thirdagent.KtcpPaySdkProxy;
import com.tencent.bugly.crashreport.CrashReport;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by  jiang
 * on 2017/7/3.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO
 * update：
 */

public class MyAPP extends Application implements KtcpPaySDKCallback {
    private static final String TAG = "MyAppliaction";
    public static boolean LogShow = true;
    public static Context context;

    public static boolean IsLineNet = true;//是否是有线网络
    public static String modelNum = "Z6X";
    public static String ID = "";
    public static String SN = "";
    //    public static String SN = "EKJ9J517DXBJ";
    public static int Temp = 0;
    public static int WindSpeed = 0;
    public static String turnType = "2";//开机类型 1 通电开机 2 手动开机
    Point point;
    public static boolean TurnOnS = false;

    public static Activity activity;

    /**
     * 判定是否是极米设备
     */
    public static boolean isxgimi = false;

    IUserInterfaceService iUserInterfaceService;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        //崩溃检测
        CrashReport.initCrashReport(getApplicationContext(), "15b18d3a4c", false);

        LogUtil.e(TAG, "有线连接：" + Tools.isLineConnected());
        Tools.setScreenOffTime(24 * 60 * 60 * 1000);
        LogUtil.e(TAG, "休眠时间：" + Tools.getScreenOffTime());

        SaveUtils.setBoolean(Save_Key.FristTurnOn, true);


        LogUtil.e(TAG, "准备连接AIDL");
        ComponentName componentName = new ComponentName("com.skygz.interfaces", "com.skygz.interfaces.SkyInterfaceCallService");
        bindService(new Intent().setComponent(componentName), serviceConnection, Context.BIND_AUTO_CREATE);


        //添加监听
        KtcpPaySdkProxy.getInstance().setPaySDKCallback(this);


        //开机请求
        new TurnOn_servlet(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        if (!TextUtils.isEmpty(SN)) {
            isxgimi = true;
        }
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        //绑定上服务的时候
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogUtil.e(TAG, "连接AIDL成功");

            //设定为极米设备
            isxgimi = true;

            //得到远程服务
            iUserInterfaceService = IUserInterfaceService.Stub.asInterface(iBinder);

            if (TurnOnS) {
                return;
            }
//            try {
////                //电视/机顶盒机型号
////                iUserInterfaceService.get("Model", null);
////                ictvDeviceManager.getSTBData("SoftwareVersion", null);
////                ictvDeviceManager.getSTBData("MAC", null);
////                //设备SN
////                SN = ictvDeviceManager.getSTBData("SN", null);
////                ictvDeviceManager.getSTBData("GDID", null);
////                ictvDeviceManager.getSTBData("sw", null);
////                ictvDeviceManager.getSTBData("IC", null);
////                ictvDeviceManager.getSTBData("OsVersion", null);
////                ictvDeviceManager.getSTBData("BoardName", null);
////                ictvDeviceManager.getSTBData("apprresult", null);
////
//                if (!TurnOnS) {
//                    new TurnOn_servlet(context).execute();
//                }
//
//            } catch (RemoteException e) {
//                e.printStackTrace();
//                LogUtil.e(TAG, "连接失败" + e.getMessage());
//            }
        }

        //断开服务的时候
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtil.e(TAG, "断开AIDL连接");
        }
    };


    private int status = -1;//接口状态码
    private String msg;//接口提示信息

    /**
     * @param channel 三方厂商对应的渠道号
     * @param extra   包含guid,QUA，TVPlatform等字段的json字符串
     */
    @Override
    public void doLogin(String channel, String extra) {

        final HashMap<String, Object> loginData = new HashMap<>();
        // FIXME:  获取帐号   需要腾讯处理的错误码和提示请沟通好通知处理
        // status 成功返回 0 失败返回对应错误码 厂商业务错误 fixme  腾旅 902xxx 例如902001登录失败
        // msg  错误提示
        // data json数据

        //vuid登录示例
        loginData.put("loginType", "vu");//登录类型 vu ,qq,wx,ph
        loginData.put("vuid", Const.ktcp_vuid);
        loginData.put("vtoken", Const.ktcp_vtoken);
        loginData.put("accessToken", Const.ktcp_accessToken);


        //大票换小票接口
        TvTicketTool.getVirtualTVSKey(this, false, Long.parseLong(Const.ktcp_vuid), Const.ktcp_vtoken, Const.ktcp_accessToken, new TvTencentSdk.OnTVSKeyListener() {
            @Override
            public void OnTVSKeySuccess(String vusession, int expiredTime) {
                LogUtil.e(TAG, "vusession=" + vusession + ",expiredTime=" + expiredTime);
                status = 0;
                msg = "login success";
                loginData.put("vusession", vusession);
                //通过onLoginResponse 将数据回传给腾讯
                KtcpPaySdkProxy.getInstance().onLoginResponse(status, msg, JsonUtils.addJsonValue(loginData));
            }

            @Override
            public void OnTVSKeyFaile(int failedCode, String failedMsg) {
                LogUtil.e(TAG, "failedCode=" + failedCode + ",msg=" + failedMsg);
                status = failedCode;
                msg = failedMsg;
                KtcpPaySdkProxy.getInstance().onLoginResponse(status, msg, JsonUtils.addJsonValue(loginData));
            }
        });
    }

    /**
     * 订单处理  需要腾讯处理的错误码和提示请沟通好通知处理
     *
     * @param vuserId
     * @param productId
     * @param extra
     */
    @Override
    public void doOrder(String vuserId, String productId, String extra) {

        // status 成功返回0 失败返回对应错误码 厂商业务错误 fixme  腾旅 902xxx 例如902101订购失败
        // msg  错误提示
        // data json数据 { "vuid":"","extra":{"orderId":""}}

        //订单返回结果示例
        HashMap<String, Object> params = new HashMap<>();
        int status = 0;//0 订单处理成功 非0失败
        String msg = "make order success";
        params.put("vuid", vuserId);

        JSONObject orderJson;
        try {
            orderJson = new JSONObject();
            orderJson.put("orderId", "orderId12345678");
        } catch (JSONException e) {
            e.printStackTrace();
            orderJson = new JSONObject();
        }
        params.put("extra", orderJson);

        KtcpPaySdkProxy.getInstance().onOrderResponse(status, msg, JsonUtils.addJsonValue(params));

    }

    /**
     * 接收来自腾讯的通知事件
     *
     * @param eventId 1 接收应用启动事件 由三方APP决定登录时机发起登录 2 帐号登录回调  3 帐号退出回调 4 APP退出回调
     * @param params
     */
    @Override
    public void onEvent(int eventId, String params) {
        LogUtil.e(TAG, "eventId:" + eventId + "params:" + params);
        switch (eventId) {
            //接收应用启动事件 由三方APP决定登录时机发起登录
            case 1:
                KtcpPaySdkProxy.getInstance().onEventResponse(KtcpContants.EVENT_ACCOUNT_LOGIN, "");
                break;
            //帐号登录回调
            case 2:
                //示例  {{"extra":"{\"isVip\":false,\"vuid\":278113277,\"msg\":\"login success\",\"code\":0,\"vuSession\":\"97027a6822cce5220250ef76cd58\"}","eventId":2,"type":3}
                break;
            case 3://退出登录回调
                break;
            case 4://APP退出
                break;
            default:
                break;
        }

        try {
            JSONObject extraObj = JsonUtils.getJsonObj(params);
            int code = extraObj.optInt("code");
            String message = extraObj.optString("msg");

            VIPCallBack_Servlet.TencentVip vip = new VIPCallBack_Servlet.TencentVip();
            vip.setCode(String.valueOf(code));
            vip.setMsg(message);
            vip.setEventId(String.valueOf(eventId));  // 2 账户登录回调 3 退出登录  4 APP退出
            new VIPCallBack_Servlet().execute(vip);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }

    }

}
