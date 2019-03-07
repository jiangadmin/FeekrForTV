package com.jiang.tvlauncher.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.jiang.tvlauncher.MyAPP;
import com.jiang.tvlauncher.R;
import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.dialog.NetDialog;
import com.jiang.tvlauncher.dialog.PwdDialog;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.FindChannelList;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.entity.Theme_Model;
import com.jiang.tvlauncher.receiver.NetReceiver;
import com.jiang.tvlauncher.servlet.DownUtil;
import com.jiang.tvlauncher.servlet.FindChannelList_Servlet;
import com.jiang.tvlauncher.servlet.GetVIP_Servlet;
import com.jiang.tvlauncher.servlet.Get_Theme_Servlet;
import com.jiang.tvlauncher.servlet.Update_Servlet;
import com.jiang.tvlauncher.utils.FileUtils;
import com.jiang.tvlauncher.utils.ImageUtils;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.RootCmd;
import com.jiang.tvlauncher.utils.SaveUtils;
import com.jiang.tvlauncher.utils.ShellUtils;
import com.jiang.tvlauncher.utils.Tools;
import com.jiang.tvlauncher.view.TitleView;
import com.snm.upgrade.aidl.ApproveDeviceManager;
import com.snm.upgrade.aidl.ITaskCallback;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author jiangadmin
 * date: 2018/10/12.
 * Email: www.fangmu@qq.com
 * Phone: 186 6120 1018
 * TODO: 新主页
 */

public class Home_Activity extends Base_Activity implements View.OnClickListener, View.OnFocusChangeListener {
    private static final String TAG = "Launcher_Activity";

    ImageView main_bg, main_bg_0;
    TextView title_0, title, title_2;

    LinearLayout setting;
    ImageView setting_img, title_icon;
    TextView setting_txt;

    LinearLayout title_view;

    TitleView titleview;

    ImageView home1, home2, home3, home4;
    TextView name1, name2, name3, name4;

    TextView ver;

    List<ImageView> homelist = new ArrayList<>();
    List<TextView> namelist = new ArrayList<>();
    List<Integer> hometype = new ArrayList<>();

    static FindChannelList channelList;

    TimeCount timeCount;
    TitleTime titleTime;

    ImageView imageView;
    VideoView videoView;

    WarningDialog warningDialog = null;

    int i = 1;
    String[] title_list;

    private static ApproveDeviceManager approveDeviceManager;

    NetReceiver netReceiver;
    public static boolean nanchuanAuthFlag = false;       //南传认证标识，false=未认证，true=已认证
    private static boolean NanChuan_Ok = true;             //南传认证结果 false = 认证失败,true=认证成功

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        setContentView(R.layout.activty_launcher);
        MyAPP.activity = this;

        netReceiver = new NetReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(netReceiver, intentFilter);

        initview();
        initeven();

        LogUtil.e(TAG, RootCmd.haveRoot() + "");

        //判断网络
        if (!Tools.isNetworkConnected()) {
            NetDialog.showL();
        }

        onMessage("update");

        //首先显示本地资源
        if (!TextUtils.isEmpty(SaveUtils.getString(Save_Key.Channe))) {
            onMessage(new Gson().fromJson(SaveUtils.getString(Save_Key.Channe), FindChannelList.class));
        }
        //首先显示本地资源
        if (!TextUtils.isEmpty(SaveUtils.getString(Save_Key.Theme))) {
            onMessage(new Gson().fromJson(SaveUtils.getString(Save_Key.Theme), Theme_Model.class));
        }
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    /**
     * 设置颜色
     *
     * @param color 要改的颜色
     */
    private void title_color(String color) {

        title_0.setBackground(Drawable_Color(R.drawable.kuang_0, color));
        title.setBackground(Drawable_Color(R.drawable.kuang_1, color));
        title_2.setBackground(Drawable_Color(R.drawable.kuang_2, color));
        title_icon.setBackground(Drawable_Color(R.drawable.round, color));

        name1.setTextColor(Color.parseColor(color));
        name2.setTextColor(Color.parseColor(color));
        name3.setTextColor(Color.parseColor(color));
        name4.setTextColor(Color.parseColor(color));

    }

    /**
     * 改变图片颜色
     *
     * @param id    图片id
     * @param color 要改的颜色
     * @return Drawable
     */
    private Drawable Drawable_Color(int id, String color) {
        return new BitmapDrawable(getResources(), ImageUtils.tintBitmap(BitmapFactory.decodeResource(getResources(), id), Color.parseColor(color)));
    }

    @Subscribe
    public void onMessage(String showwarn) {
        switch (showwarn) {
            case "0":
                if (warningDialog == null) {
                    warningDialog = new WarningDialog(this);
                }
                warningDialog.show();
                break;
            case "1":
                if (warningDialog != null) {
                    warningDialog.dismiss();
                }
                break;

            case "update":

                //检查更新
                new Update_Servlet(this).execute();
                //查询栏目
                new FindChannelList_Servlet().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                //获取主题
                new Get_Theme_Servlet().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                nanchuan();
                break;

            case "nanchuan":
                nanchuan();
                break;
            default:
                break;
        }
    }

    private void initview() {

        main_bg = (ImageView) findViewById(R.id.main_bg);
        main_bg_0 = (ImageView) findViewById(R.id.main_bg_0);
        title_view = (LinearLayout) findViewById(R.id.title_view);
        title_icon = (ImageView) findViewById(R.id.title_icon);
        title_0 = (TextView) findViewById(R.id.title_0);
        title = (TextView) findViewById(R.id.title);
        title_2 = (TextView) findViewById(R.id.title_2);

        home1 = (ImageView) findViewById(R.id.home_1);
        home2 = (ImageView) findViewById(R.id.home_2);
        home3 = (ImageView) findViewById(R.id.home_3);
        home4 = (ImageView) findViewById(R.id.home_4);

        name1 = (TextView) findViewById(R.id.home_1_name);
        name2 = (TextView) findViewById(R.id.home_2_name);
        name3 = (TextView) findViewById(R.id.home_3_name);
        name4 = (TextView) findViewById(R.id.home_4_name);

        setting = (LinearLayout) findViewById(R.id.setting);
        setting_img = (ImageView) findViewById(R.id.setting_img);
        setting_txt = (TextView) findViewById(R.id.setting_txt);

        titleview = (TitleView) findViewById(R.id.titleview);

        ver = (TextView) findViewById(R.id.ver);
        ver.setText(String.format("V %s", Tools.getVersionName(MyAPP.context)));

        homelist.add(home1);
        homelist.add(home2);
        homelist.add(home3);
        homelist.add(home4);

        namelist.add(name1);
        namelist.add(name2);
        namelist.add(name3);
        namelist.add(name4);

        imageView = (ImageView) findViewById(R.id.image);
        videoView = (VideoView) findViewById(R.id.video);

        //如果有图片
        if (SaveUtils.getBoolean(Save_Key.NewImage)) {
            LogUtil.e(TAG, "有图片");
            imageView.setVisibility(View.VISIBLE);
            Glide.with(this).load(SaveUtils.getString(Save_Key.NewImageUrl)).into(imageView);
            timeCount = new TimeCount(5000, 1000);
            timeCount.start();
        }

        //如果有视频
        else if (SaveUtils.getBoolean(Save_Key.NewVideo)) {
            LogUtil.e(TAG, "有视频 " + SaveUtils.getString(Save_Key.NewVideoUrl));
            videoView.setVisibility(View.VISIBLE);
            videoView.setZOrderOnTop(true);
            videoView.setVideoURI(Uri.parse(SaveUtils.getString(Save_Key.NewVideoUrl)));
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    videoView.setVisibility(View.GONE);
                }
            });
            videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    videoView.setVisibility(View.GONE);
                    return false;
                }
            });
            videoView.start();
        }
    }

    private void initeven() {
        home1.setOnClickListener(this);
        home2.setOnClickListener(this);
        home3.setOnClickListener(this);
        home4.setOnClickListener(this);

        setting.setOnClickListener(this);

        home1.setOnFocusChangeListener(this);
        home2.setOnFocusChangeListener(this);
        home3.setOnFocusChangeListener(this);
        home4.setOnFocusChangeListener(this);

        setting.setOnFocusChangeListener(this);

        //切换焦点给第一个
        home1.setFocusable(true);
        home1.setFocusableInTouchMode(true);
        home1.requestFocus();
    }

    @Override
    public void onBackPressed() {
        //屏蔽返回键
    }

    /**
     * 南方传媒认证
     */
    public void nanchuan() {
        LogUtil.e(TAG, "准备认证");
        if (!nanchuanAuthFlag && Tools.isNetworkConnected()) {
            nanchuanAuthFlag = true;
//            Toast.makeText(this, "开始认证", Toast.LENGTH_SHORT).show();
            LogUtil.e(TAG, "开始认证");

            Intent intent = new Intent("com.snm.upgrade.approve.ApproveManagerServer");
            intent.setPackage("com.snm.upgrade");
            bindService(intent, new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    approveDeviceManager = ApproveDeviceManager.Stub.asInterface(iBinder);
                    try {
                        //registerCallback（）这个注册接口是返回结果回调，先注册
                        approveDeviceManager.registerCallback(new ITaskCallback.Stub() {
                            @Override
                            public void returnResult(String Result) {
                                if (Result.equals("998")) {
                                    NanChuan_Ok = false;
                                    LogUtil.e(TAG, "认证失败");
                                    findViewById(R.id.dispaly).setVisibility(View.VISIBLE);
//                                    Toast.makeText(MyAPP.context, "南方传媒认证失败", Toast.LENGTH_LONG).show();
                                } else {
                                    NanChuan_Ok = true;
                                    LogUtil.e(TAG, "认证成功");
                                    findViewById(R.id.dispaly).setVisibility(View.GONE);
                                    Toast.makeText(MyAPP.context, "南方传媒认证成功", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        //requestApprove()这个是调起我们的认证接口
                        approveDeviceManager.requestApprove();
                    } catch (Exception e) {
                        LogUtil.e(TAG, e.getMessage());
                        CrashReport.postCatchedException(e);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    approveDeviceManager = null;
                }
            }, BIND_AUTO_CREATE);
        }
    }

    boolean showToast = true;
    long[] mHits = new long[7];

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtil.e(TAG, keyCode);
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);// 数组向左移位操作
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();
                if (mHits[0] >= (SystemClock.uptimeMillis() - 5000)) {
                    LogUtil.e(TAG, "Password:" + SaveUtils.getString(Save_Key.Password));
                    if (TextUtils.isEmpty(SaveUtils.getString(Save_Key.Password))) {
                        Setting_Activity.start(this);
                    } else {
                        new PwdDialog(this, R.style.MyDialog).show();
                    }
                } else {
                    showToast = true;
                }
                return true;
            case KeyEvent.KEYCODE_BACK:
                return true;
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
            case KeyEvent.KEYCODE_ENTER:

                //Toast.makeText(this, "南方传媒认证失败", Toast.LENGTH_SHORT).show();
                return !NanChuan_Ok;

        }
        return true;
    }

    /**
     * 主题返回 网络正常情况下
     *
     * @param entity 主题实体类
     */
    @SuppressLint("CheckResult")
    @Subscribe
    public void onMessage(Theme_Model entity) {

        Theme_Model.ResultBean bean = entity.getResult();
        if (bean != null) {

            //图片名
            String imgname = Tools.getFileNameWithSuffix(bean.getBgImg());
            //判断图片文件是否存在
            if (imgname != null && !FileUtils.checkFileExists(imgname)) {
                //下载图片
                new DownUtil().downLoad(bean.getBgImg(), imgname, false);
            }

            //赋值背景 前景显示
            try {
                RequestOptions builder = new RequestOptions();
                builder.placeholder(new BitmapDrawable(getResources(), ImageUtils.getBitmap(new File(Const.FilePath + SaveUtils.getString(Save_Key.BackGround)))));
                builder.error(new BitmapDrawable(getResources(), ImageUtils.getBitmap(new File(Const.FilePath + SaveUtils.getString(Save_Key.BackGround)))));
                Glide.with(this).load(bean.getBgImg()).apply(builder).into(main_bg);
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
                CrashReport.postCatchedException(e);
            }

            //设置图标背景色 对话框颜色
            title_color(bean.getMicLogoColor());

            //设置时间颜色
            if (!TextUtils.isEmpty(bean.getTimesCtrlColor())) {
                titleview.setTimeColor(bean.getTimesCtrlColor());
            }
            //设置对话框内容颜色
            title.setTextColor(Color.parseColor(bean.getTipFontColor()));

            //标题集合
            title_list = null;
            title_list = bean.getTipContents().split("#");

            //是否显示标题
            title_view.setVisibility(bean.getTipShowFlag() == 1 ? View.VISIBLE : View.GONE);

            //是否显示控制台
            setting.setVisibility(bean.getConsoleShowFlag() == 1 ? View.VISIBLE : View.GONE);

            //是否初始化逻辑科技
            if (bean.getStartLgeekFlag() == 1) {

            }

            //是否显示栏目名
            name1.setVisibility(bean.getCnameShowFlag() == 1 ? View.VISIBLE : View.GONE);
            name2.setVisibility(bean.getCnameShowFlag() == 1 ? View.VISIBLE : View.GONE);
            name3.setVisibility(bean.getCnameShowFlag() == 1 ? View.VISIBLE : View.GONE);
            name4.setVisibility(bean.getCnameShowFlag() == 1 ? View.VISIBLE : View.GONE);

            //标题轮询时间
            int title_time = bean.getTipSwitchRate();

            if (title_list != null && title_list.length > 0) {
                title.setText(title_list[0]);
            }

            //倒计时
            if (title_list != null && title_list.length > 1) {
                if (titleTime != null)
                    titleTime.cancel();
                titleTime = null;

                titleTime = new TitleTime(title_time, title_time);
                titleTime.start();
            }
        }
    }

    /**
     * 更新页面
     *
     * @param channelList 返回实体类
     */
    @SuppressLint("CheckResult")
    @Subscribe
    public void onMessage(FindChannelList channelList) {
        Home_Activity.channelList = channelList;

        //更改开机动画
        if (!TextUtils.isEmpty(SaveUtils.getString(Save_Key.BootAn))) {

            //判断文件是否存在
//            if (!FileUtils.checkFileExists(Tools.getFileNameWithSuffix(SaveUtils.getString(Save_Key.BootAn)))) {
            LogUtil.e(TAG, "开始下载");
            new DownUtil().downLoad(SaveUtils.getString(Save_Key.BootAn),
                    "bootanimation.zip", false);
//            }
        }

        if (channelList != null) {
            for (int i = 0; i < channelList.getResult().size(); i++) {

                //限制最大个数
                if (i > 3)
                    return;
                //图片网络地址
                String url = channelList.getResult().get(i).getBgUrl();
                //图片文件名
                String filename = Tools.getFileNameWithSuffix(channelList.getResult().get(i).getBgUrl());
                //设置栏目名称
                namelist.get(i).setText(channelList.getResult().get(i).getChannelName());
                //加载图片 优先本地
                RequestOptions options = new RequestOptions();
                String s = Const.FilePath + SaveUtils.getString(Save_Key.ItemImage + i);
                //判断文件是否存在
                if (FileUtils.checkFileExists(Objects.requireNonNull(Tools.getFileNameWithSuffix(s)))) {
                    options.placeholder(getResources().getDrawable(R.mipmap.item_bg));
                    options.error(new BitmapDrawable(getResources(), ImageUtils.getBitmap(new File(s))));
                }
                options.skipMemoryCache(false);
                options.diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(this).load(url).apply(options).into(homelist.get(i));

                hometype.add(channelList.getResult().get(i).getContentType());

                //判断文件是否存在
                if (filename != null && !FileUtils.checkFileExists(filename)) {
                    //下载图片
                    new DownUtil().downLoad(url, filename, false);

                    //记录文件名
                    SaveUtils.setString(Save_Key.ItemImage + i, filename);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        //账户（信号源）判断
        if (Const.BussFlag == 0) {
            if (warningDialog == null) {
                warningDialog = new WarningDialog(this);
            }
            warningDialog.show();
            return;
        }

        if (!NanChuan_Ok) {
            //Toast.makeText(this, "南方传媒认证失败", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (view.getId()) {
            case R.id.setting:
                LogUtil.e(TAG, "Password:" + SaveUtils.getString(Save_Key.Password));
                if (TextUtils.isEmpty(SaveUtils.getString(Save_Key.Password))) {
                    Setting_Activity.start(this);
                } else {
                    new PwdDialog(this, R.style.MyDialog).show();
                }
                break;
            case R.id.home_1:
                open(0);
                break;
            case R.id.home_2:
                open(1);
                break;
            case R.id.home_3:
                open(2);
                break;
            case R.id.home_4:
                open(3);
                break;
        }
    }

    /**
     * 启动栏目
     */
    public void open(int i) {
        try {

            //数据缺失的情况
            if (hometype.size() <= i) {
                Toast.makeText(this, "栏目未开通！", Toast.LENGTH_SHORT).show();
                return;
            }

            //数据正常的情况
            switch (hometype.get(i)) {
                //无操作
                case 0:
                    Toast.makeText(this, "栏目未开通", Toast.LENGTH_SHORT).show();
                    break;
                //启动指定APP
                case 1:

                    if (channelList.getResult().get(i).getAppList() != null && channelList.getResult().get(i).getAppList().size() > 0) {

                        String packname = channelList.getResult().get(i).getAppList().get(0).getPackageName();

                        //如果要启动定制版腾讯视频
                        if (packname.equals(Const.TvViedo)) {
                            SaveUtils.setString(Const.TvViedoDow, channelList.getResult().get(i).getAppList().get(0).getDownloadUrl());
                            Const.云视听Url = channelList.getResult().get(i).getAppList().get(0).getDownloadUrlBak();
                        }
                        //验证是否有此应用
                        if (Tools.isAppInstalled(packname)) {
                            //如果要启动定制版腾讯视频
                            if (packname.equals(Const.TvViedo)) {

                                //判断时候已经运行
                                if (!TextUtils.isEmpty(ShellUtils.execCommand("ps |grep com.ktcp.tvvideo:webview", false).successMsg)) {
                                    startActivity(new Intent(getPackageManager().getLaunchIntentForPackage(packname)));
                                } else {
                                    Loading.show(this, "请稍后");
                                    //获取VIP账号
                                    new GetVIP_Servlet(true).execute();
                                }
                            } else {
                                startActivity(new Intent(getPackageManager().getLaunchIntentForPackage(packname)));
                            }
                        } else {

                            Loading.show(this, "请稍后");
                            new DownUtil().downLoad(channelList.getResult().get(i).getAppList().get(0).getDownloadUrl(), Tools.getFileNameWithSuffix(channelList.getResult().get(i).getAppList().get(0).getDownloadUrl()) , true);
                        }
                    } else
                        Toast.makeText(this, "栏目未开通", Toast.LENGTH_SHORT).show();
                    break;
                //启动APP列表
                case 2:
                    NewAPPList_Activity.start(this, channelList.getResult().get(i).getAppList());
                    break;
                //启动展示图片
                case 3:
                    Image_Activity.start(this, channelList.getResult().get(i).getContentUrl());
                    break;
                //启动展示视频
                case 4:
                    Video_Activity.start(this, channelList.getResult().get(i).getContentUrl());
                    break;
            }
        } catch (Exception ex) {
            LogUtil.e(TAG, "打开栏目报错" + ex.getMessage());
        }
    }

    /**
     * 密码输入返回
     */
    public void PwdRe() {
        Setting_Activity.start(this);
    }

    /**
     * 焦点变化
     *
     * @param view 焦点位置
     * @param b    变化
     */
    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.setting:
                setting_txt.setTextColor(getResources().getColor(b ? R.color.white : R.color.gray));
                break;
            default:
                if (b) {
                    enlargeAnim(view);
                } else {
                    reduceAnim(view);
                }
                break;
        }
    }

    /**
     * 计时器
     */
    class TimeCount extends CountDownTimer {
        TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        //倒计时完成
        @Override
        public void onFinish() {
            //如果有图片
            if (SaveUtils.getBoolean(Save_Key.NewImage))
                imageView.setVisibility(View.GONE);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示

        }
    }

    /**
     * 标题定时轮询
     */
    class TitleTime extends CountDownTimer {

        TitleTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture * 1000, countDownInterval * 1000);
        }

        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            try {
                title.setText(title_list[i]);

                if (i == title_list.length - 1) {
                    i = 0;
                } else {
                    i++;
                }
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
            }

            start();
        }
    }

    /**
     * 警告框
     */
    public static class WarningDialog extends Dialog {
        WarningDialog(@NonNull Context context) {
            super(context, R.style.MyDialog);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_warning);
            setCanceledOnTouchOutside(false);
            setCancelable(false);
        }
    }
}
