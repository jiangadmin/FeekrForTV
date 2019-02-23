package com.snm.upgrade.aidl;
import com.snm.upgrade.aidl.ITaskCallback;
interface ApproveDeviceManager {
	//Device Interface:
	int requestApprove();
    boolean isTaskRunning();   
    void stopRunningTask();   
    void registerCallback(ITaskCallback cb);   
    void unregisterCallback(ITaskCallback cb);	
}