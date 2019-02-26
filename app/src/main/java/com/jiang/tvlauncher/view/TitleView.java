package com.jiang.tvlauncher.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiang.tvlauncher.R;
import com.jiang.tvlauncher.utils.Tools;

public class TitleView extends RelativeLayout {
    private static final String TAG = "TitleView";

    private View view;
    private final Context context;
    private Typeface typeface;
    private TextView tvTime, tvDate;
    private ImageView imgNetWorkState;

    private final Handler timeHandle = new Handler();

    TimeCount timeCount = new TimeCount(1000, 1000);

    private final Runnable timeRun = new Runnable() {

        public void run() {
            setTvTimeText(TimeUtil.getTime());
            setTvDateDate(TimeUtil.getDate());
            timeHandle.postDelayed(this, 1000);
        }

    };

    public TitleView(Context context) {
        super(context);
        this.context = context;
        initTitleView();
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initTitleView();
    }

    public void initTitleView() {
        view = LayoutInflater.from(context).inflate(R.layout.titleview, this, true);
        tvTime = view.findViewById(R.id.title_bar_hour);
        tvDate = view.findViewById(R.id.title_bar_date);
        imgNetWorkState = view.findViewById(R.id.title_bar_network_state);
        typeface = Typeface.createFromAsset(context.getAssets(), "helvetica_neueltpro_thex.otf");
        tvTime.setTypeface(typeface);
        tvDate.setTypeface(typeface);
        timeHandle.post(timeRun);
        imgNetWorkState = this.findViewById(R.id.title_bar_network_state);

        update();

    }

    public void setTimeColor(String color) {
        if (tvTime != null) {
            tvTime.setTextColor(Color.parseColor(color));
        }
    }

    public void update() {
        timeCount.start();
        //判断有没有网络
        if (Tools.isNetworkConnected()) {
            //是否是有线
            if (Tools.isLineConnected()) {
                imgNetWorkState.setImageResource(R.mipmap.networkstate_ethernet);
            } else {
                WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if (wifiInfo.getBSSID() != null) {
                    // wifi信号强度
                    int signalLevel = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), 4);
                    if (signalLevel == 0) {
                        imgNetWorkState.setImageDrawable(context.getResources().getDrawable(R.mipmap.wifi_1));
                    } else if (signalLevel == 1) {
                        imgNetWorkState.setImageDrawable(context.getResources().getDrawable(R.mipmap.wifi_2));
                    } else if (signalLevel == 2) {
                        imgNetWorkState.setImageDrawable(context.getResources().getDrawable(R.mipmap.wifi_3));
                    } else if (signalLevel == 3) {
                        imgNetWorkState.setImageDrawable(context.getResources().getDrawable(R.mipmap.network_state_on));
                    }
                }
            }
        } else {
            imgNetWorkState.setImageResource(R.mipmap.networkstate_off);
        }
    }

    public void setTvTimeText(String text) {
        tvTime.setText(text);
    }

    public void setTvDateDate(String text) {
        tvDate.setText(text);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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
            update();
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示

        }
    }
}
