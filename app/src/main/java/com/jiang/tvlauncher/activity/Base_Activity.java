package com.jiang.tvlauncher.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jiang.tvlauncher.MyAPP;
import com.jiang.tvlauncher.utils.AnimUtils;

/**
 * @author jiangadmin
 * date: 2017/7/3.
 * Email: www.fangmu@qq.com
 * Phone: 186 6120 1018
 * TODO: 公共
 */

public class Base_Activity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyAPP.activity = this;
    }

    public void enlargeAnim(View v) {
        AnimUtils.S(v, 1, 1.1F);
        AnimUtils.Z(v, 0, 5);

    }

    public void reduceAnim(View v) {

        AnimUtils.S(v, 1.1F, 1);
        AnimUtils.Z(v, 5, 0);
    }

}
