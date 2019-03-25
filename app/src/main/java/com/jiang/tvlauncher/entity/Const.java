package com.jiang.tvlauncher.entity;

import com.jiang.tvlauncher.BuildConfig;
import com.jiang.tvlauncher.MyAPP;

/**
 * Created by  jiang
 * on 2017/6/19.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 常量
 * update：
 */
public class Const {

    public static String URL = BuildConfig.NetUrl;

    public static int Timing = 30;
    public static int ID;

    public static int BussFlag = 1;      //账户状态

    public static int ShowType = 1; //1:图片  2 ：视频
    public static boolean Nets = true; //网络状态  控制发请求

    public static int seconds = 300;      //间隔时间

    public static int FindChannelList = 0;

    public static boolean IsGetVip = false;  //本次开机时候获取过Vip

    public static String 包 = "baobaobao";
    public static String TvViedoDow = "TvViedoDow";//定制版腾讯视频下载地址
    public static String ViedoDow = "ViedoDow";//腾讯视频下载地址

    public static String 云视听 = "KtcpVideo";//云视听
    public static String 云视听Url = "KtcpVideoUrl";//下载地址

    public static String TvViedo = "com.ktcp.tvvideo";//定制版腾讯视频
    public static String TencentViedo = "com.ktcp.video";//腾讯视频ktcp

    public static String 资源管理器 = "com.xgimi.filemanager";//影视资源

    public static String ktcp_vuid = "";//腾讯视频ID
    public static String ktcp_vtoken = "";//腾讯视频ID
    public static String ktcp_accessToken = "";//腾讯视频ID

    /**
     * 存储位置
     */
//    public static String FilePath = Environment.getExternalStorageDirectory().getPath() + "/feekr/Download/";
    public static String FilePath = MyAPP.context.getFilesDir().getPath();


    public static boolean Item_1_Isinstall;
    public static boolean Item_2_Isinstall;
    public static boolean Item_3_Isinstall;
    public static boolean Item_4_Isinstall;

}
