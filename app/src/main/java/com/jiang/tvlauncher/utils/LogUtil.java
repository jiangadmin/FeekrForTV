
package com.jiang.tvlauncher.utils;

import android.app.Activity;
import android.util.Log;

import com.jiang.tvlauncher.MyAPP;

/**
 * Created by 垚垚
 * on 15/7/23.
 * Email: www.fangmu@qq.com
 * Phone：18661201018
 * Purpose: TODO LOG日志
 */
public class LogUtil {
    static String className;
    static String methodName;
    static int lineNumber;

    public static int INFO = Log.INFO;
    public static int VERBOSE = Log.VERBOSE;
    public static int DEBUG = Log.DEBUG;
    public static int WARN = Log.WARN;
    public static int ERROR = Log.ERROR;


    public static boolean isLoggable(String key, int type) {
        return Log.isLoggable(key, type);
    }

    public static void v(String key, String msg) {
        if (MyAPP.LogShow) {
            getMethodNames(new Throwable().getStackTrace());
            Log.v(key, ">>" + createLog(msg));
        }
    }

    /**
     * TODO
     *
     * @param key
     * @param msg
     */
    public static void d(String key, String msg) {
        if (MyAPP.LogShow) {
            getMethodNames(new Throwable().getStackTrace());
            Log.d(key, ">>" + createLog(msg));
        }
    }

    public static void i(String key, String msg) {
        if (MyAPP.LogShow) {
            getMethodNames(new Throwable().getStackTrace());
            Log.i(key, ">>" + createLog(msg));
        }
    }

    public static void w(String key, String msg) {
        if (MyAPP.LogShow) {
            getMethodNames(new Throwable().getStackTrace());
            Log.w(key, ">>" + createLog(msg));
        }
    }

    public static void w(String key, Exception msg) {
        if (MyAPP.LogShow) {
            getMethodNames(new Throwable().getStackTrace());
            Log.w(key, ">>" + createLog(msg.toString()));
        }
    }

    public static void w(String key, String msg, Throwable t) {
        if (MyAPP.LogShow) {
            getMethodNames(new Throwable().getStackTrace());
            Log.w(key, ">>" + createLog(msg), t);
        }
    }

    public static void wtf(String key, String msg) {
        if (MyAPP.LogShow) {
            getMethodNames(new Throwable().getStackTrace());
            Log.wtf(key, ">>" + createLog(msg));
        }
    }

    public static void wtf(String key, String msg, Throwable t) {
        if (MyAPP.LogShow) {
            getMethodNames(new Throwable().getStackTrace());
            Log.wtf(key, ">>" + createLog(msg), t);
        }
    }

    public static void e(String key, String msg, Throwable tr) {
        if (MyAPP.LogShow) {
            getMethodNames(new Throwable().getStackTrace());
            Log.e(key, ">>" + createLog(msg));
        }
    }

    public static void e(String key, int msg) {
        if (MyAPP.LogShow) {
            getMethodNames(new Throwable().getStackTrace());
            Log.e(key, ">>" + createLog(msg + ""));
        }
    }

    public static void e(String key, String msg) {
        if (MyAPP.LogShow) {
            getMethodNames(new Throwable().getStackTrace());
            Log.e(key, ">>" + createLog(msg));
        }
    }

    public static void ee(String key, String msg) {
        if (MyAPP.LogShow) {
            getMethodNames(new Throwable().getStackTrace());
            Log.e(key, ">>" + createLog(msg));
        }
    }

    public static void e(Activity activity, String msg) {
        if (MyAPP.LogShow) {
            getMethodNames(new Throwable().getStackTrace());
            Log.e(activity.toString(), ">>" + createLog(msg));
        }
    }

    static int showLength = 3900;

    private static String createLog(String log) {
        log = "方法:" + methodName + ">>行数:" + lineNumber + ">>" + log;
        return log;

    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

}
