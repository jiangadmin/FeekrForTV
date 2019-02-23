package com.jiang.tvlauncher.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.jiang.tvlauncher.R;
import com.jiang.tvlauncher.activity.Home_Activity;
import com.jiang.tvlauncher.activity.Launcher_Activity;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.utils.SaveUtils;

/**
 * @author: jiangadmin
 * @date: 2017/7/3.
 * @Email: www.fangmu@qq.com
 * @Phone: 186 6120 1018
 * TODO: 密码输入框
 */


public class PwdDialog extends Dialog {
    private static final String TAG = "PwdDialog";

    ImageView pwd1, pwd2, pwd3, pwd4, pwd5, pwd6;

    Activity activity;

    String password = "";

    public PwdDialog(Activity activity, int theme) {
        super(activity, theme);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_password);
        initview();
    }

    private void initview() {
        pwd1 = findViewById(R.id.pwd1);
        pwd2 = findViewById(R.id.pwd2);
        pwd3 = findViewById(R.id.pwd3);
        pwd4 = findViewById(R.id.pwd4);
        pwd5 = findViewById(R.id.pwd5);
        pwd6 = findViewById(R.id.pwd6);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                updatepwdshow(R.drawable.ic_up, 8);
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                updatepwdshow(R.drawable.ic_down, 2);
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                updatepwdshow(R.drawable.ic_left, 4);
                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                updatepwdshow(R.drawable.ic_right, 6);
                return true;
            case KeyEvent.KEYCODE_BACK:
                dismiss();
                return true;
            case KeyEvent.KEYCODE_HOME:
                dismiss();
                return true;
            default:
                return false;
        }
    }

    public void updatepwdshow(int resid, int npwd) {
        password = password + String.valueOf(npwd);
        switch (password.length()) {
            case 1:
                pwd1.setImageResource(resid);
                break;
            case 2:
                pwd2.setImageResource(resid);
                break;
            case 3:
                pwd3.setImageResource(resid);
                break;
            case 4:
                pwd4.setImageResource(resid);
                break;
            case 5:
                pwd5.setImageResource(resid);
                break;
            case 6:
                pwd6.setImageResource(resid);
                dismiss();

//                if (password.equals("822228")) {
//                    SaveUtils.setString(Const.包, null);
//                    Toast.makeText(activity,"清楚成功",Toast.LENGTH_SHORT).show();
//                }

                if (password.equals(SaveUtils.getString(Save_Key.Password))) {
                    if (activity instanceof Home_Activity) {
                        ((Home_Activity) activity).PwdRe();
                    }

                    if (activity instanceof Launcher_Activity){
                        ((Launcher_Activity)activity).PwdRe();
                    }
                }
                break;
        }
    }
}