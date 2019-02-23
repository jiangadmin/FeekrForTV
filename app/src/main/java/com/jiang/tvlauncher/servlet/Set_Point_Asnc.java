package com.jiang.tvlauncher.servlet;

import android.os.AsyncTask;

import com.jiang.tvlauncher.entity.Point;
import com.jiang.tvlauncher.utils.LogUtil;
import com.xgimi.business.api.beans.DlpKeystoneBean;
import com.xgimi.business.api.hardwares.DlpKeystoneManager;

import java.util.Date;

/**
 * @author: jiangadmin
 * @date: 2019/1/22
 * @Email: www.fangmu@qq.com
 * @Phone: 186 6120 1018
 * TODO: 异步读取梯形校正信息
 */
public class Set_Point_Asnc extends AsyncTask<Point, Integer, String> {
    private static final String TAG = "Set_Point_Asnc";

    @Override
    protected String doInBackground(Point... points) {
        long time = new Date().getTime();

        Point point = points[0];
        LogUtil.e(TAG, "开始写入");

        for (Point.PointBean pointBean : point.getPoint()) {
            DlpKeystoneBean bean = new DlpKeystoneBean();
            bean.setHorizontalValue(Integer.parseInt(pointBean.getCurrent_x()));
            bean.setVerticalValue(Integer.parseInt(pointBean.getCurrent_y()));
            String p;
            switch (pointBean.getIdx()) {
                case "0":
                    p = DlpKeystoneManager.POINT_LEFT_TOP;
                    break;
                case "1":
                    p = DlpKeystoneManager.POINT_LEFT_BOTTOM;
                    break;
                case "2":
                    p = DlpKeystoneManager.POINT_RIGHT_TOP;
                    break;
                case "3":
                    p = DlpKeystoneManager.POINT_RIGHT_BOTTOM;
                    break;
                default:
                    p = "";
                    break;
            }
            DlpKeystoneManager.INSTANCE.adjust(p, bean);
        }
        long time0 = new Date().getTime();
        LogUtil.e(TAG, "结束：" + (time0 - time));

        return null;
    }
}
