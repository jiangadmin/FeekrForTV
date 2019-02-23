package com.jiang.tvlauncher.entity;

/**
 * Created by  jiang
 * on 2017/7/4.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO
 * update：
 */
public class BaseEntity {

    /**
     * errorcode : 0000
     * errormsg : 操作失败,请稍后再试!
     * result :
     */

    private int errorcode;

    private String errormsg;

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }
}
