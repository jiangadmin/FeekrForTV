package com.jiang.tvlauncher.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiang.tvlauncher.R;
import com.jiang.tvlauncher.entity.FindChannelList;
import com.jiang.tvlauncher.utils.Tools;

import java.util.List;

/**
 * AppFragment adapter
 *
 * @author jacky
 * @version 1.0
 * @since 2016.5.10
 */
public class NewAppAdapter extends BaseAdapter {
    private static final String TAG = "NewAppAdapter";

    PackageManager pm;
    private List<FindChannelList.ResultBean.AppListBean> mAppBeanList;
    private Context mContext;
    public Holder mHolder;

    public NewAppAdapter(Context context, List<FindChannelList.ResultBean.AppListBean> appBeanList) {
        mContext = context;
        mAppBeanList = appBeanList;
        pm = context.getPackageManager();

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
            mHolder.name =  convertView.findViewById(R.id.item_app_name);
            mHolder.packagename =  convertView.findViewById(R.id.item_app_package_name);
            mHolder.icon =  convertView.findViewById(R.id.item_app_icon);
            mHolder.bg = convertView.findViewById(R.id.item_app_bg);
            convertView.setTag(mHolder);
        } else {
            mHolder = (Holder) convertView.getTag();
        }
        final FindChannelList.ResultBean.AppListBean appBean = mAppBeanList.get(position);

        //判断程序有没有
        if (Tools.isAppInstalled(appBean.getPackageName())) {
            mHolder.icon.setImageDrawable(getAppIcon(appBean.getPackageName()));
        } else {
            mHolder.icon.setImageResource(R.drawable.feekr);
        }
        mHolder.name.setText(appBean.getAppName());
        return convertView;
    }

    public class Holder {
        private TextView name, packagename;
        private ImageView icon;
        private View bg;
    }

    /**
     * 获取程序 图标
     *
     * @param packname
     * @return
     */
    public Drawable getAppIcon(String packname) {
        try {
            ApplicationInfo info = pm.getApplicationInfo(packname, 0);
            return info.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
