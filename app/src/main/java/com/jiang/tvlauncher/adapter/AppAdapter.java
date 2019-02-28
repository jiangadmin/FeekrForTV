package com.jiang.tvlauncher.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiang.tvlauncher.R;
import com.jiang.tvlauncher.entity.AppBean;
import com.jiang.tvlauncher.utils.LogUtil;

import java.util.List;
import java.util.Random;

/**
 * AppFragment adapter
 *
 * @author jacky
 * @version 1.0
 * @since 2016.5.10
 */
public class AppAdapter extends BaseAdapter {
    private static final String TAG = "AppAdapter";

    private List<AppBean> mAppBeanList;
    private Context mContext;
    public Holder mHolder;

    private int[] drawableIds = {
            R.mipmap.app_blue,
            R.mipmap.app_green,
            R.mipmap.app_jasper,
            R.mipmap.app_lawngreen,
            R.mipmap.app_red,
            R.mipmap.app_yellow
    };

    public AppAdapter(Context context, List<AppBean> appBeanList) {
        mContext = context;
        mAppBeanList = appBeanList;
    }

    @Override
    public int getCount() {
        return mAppBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAppBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            mHolder = new Holder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_app, null);
            mHolder.name = (TextView) convertView.findViewById(R.id.item_app_name);
            mHolder.packagename = (TextView) convertView.findViewById(R.id.item_app_package_name);
            mHolder.icon = (ImageView) convertView.findViewById(R.id.item_app_icon);
            mHolder.bg = convertView.findViewById(R.id.item_app_bg);
            convertView.setTag(mHolder);
        } else {
            mHolder = (Holder) convertView.getTag();
        }
        Random random = new Random();
//        mHolder.bg.setBackgroundResource(drawableIds[random.nextInt(6)]);
        AppBean appBean = mAppBeanList.get(position);
        mHolder.icon.setImageDrawable(appBean.getIcon());
        mHolder.name.setText(appBean.getName());
//        mHolder.packagename.setText(appBean.getPackageName());

        LogUtil.e(TAG, ">>>>>>>>>> " + position + " <<<<<<<<<<");
        LogUtil.e(TAG, "路  径：   " + appBean.getDataDir());
        LogUtil.e(TAG, "主页名：  " + appBean.getLauncherName());
        LogUtil.e(TAG, "软件名：  " + appBean.getName());
        LogUtil.e(TAG, "包  名：  " + appBean.getPackageName());

        final int pos = position;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = mContext.getPackageManager().getLaunchIntentForPackage(
                        mAppBeanList.get(pos).getPackageName());
                if (launchIntent != null) {
                    mContext.startActivity(launchIntent);
                }
            }
        });
        return convertView;
    }

    public class Holder {
        private TextView name, packagename;
        private ImageView icon;
        private View bg;
    }
}
