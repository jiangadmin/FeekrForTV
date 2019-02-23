package com.jiang.tvlauncher.server;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

import com.jiang.tvlauncher.utils.LogUtil;

/**
 * Created by wwwfa on 2017/9/27.
 */

public class KeyCodeService extends AccessibilityService {
    private static final String TAG = "KeyCodeService";
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        LogUtil.e(TAG,"diandadasdadasdasd");
    }

    @Override
    public void onInterrupt() {
        LogUtil.e(TAG,"diandadasdadasdasd");
    }
}
