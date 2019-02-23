// IUserInterfaceService.aidl
package com.android.aidl;

// Declare any non-default types here with import statements

interface IUserInterfaceService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

     //获取barcode
     String getSerialCode();

     //获取mac地址
     String getMacAddress();

     //截图
    boolean getScreenShot(int width,int height,String path);

     //切换信号源
    boolean switchSource(int source);

    //获取亮度
    int getBrightness();
    //设置亮度
    int setBrightness(int brightness);

    //获取音量
    int getVolume();
    //设置音量
    void setVolume(int vol);
    //获取是否静音
    boolean getMute();
    //设置静音
    void setMute(boolean onoff);
    //获取开机音量
    int getBootUpVolume();
    //设置开机音量
    boolean setBootUpVolume(int volume);
    //获取adb开关
    boolean getAdbMode();
    //设置adb模式，true为开，false为关
    boolean setAdbMode(boolean mode);
    //设置屏幕开关
    boolean setPanelOnOff(boolean onOff);
}
