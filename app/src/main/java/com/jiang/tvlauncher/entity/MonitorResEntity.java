package com.jiang.tvlauncher.entity;

/**
 * Created by wwwfa on 2017/8/9.
 */

public class MonitorResEntity extends BaseEntity {

    /**
     * errorcode : 1000
     * result : {"shadowcnf":{"createAuthor":"系统管理员","createTime":"2017-08-20 15:27:51","hotPoint":1,"id":1,"isDefault":1,"isDelete":0,"ladderPwd":"123","monitRate":300000,"powerTurn":1,"projectMode":0,"shadowName":"测试方案","shadowPwd":"862486","threeDim":1,"turnSource":"123","updateAuthor":"系统管理员","updateTime":"2017-09-23 17:30:45","wifi":"智米","wifiPassword":"12345678"},"devInfo":{"androidVersion":"6.0","createAuthor":"system","createTime":"2017-09-27 19:27:41","id":28,"isDelete":0,"merchantId":0,"modelNum":"Z5极光","roomId":0,"serialNum":"DG69H9DBCTAF","systemVersion":"V2.5.19","updateAuthor":"system","updateTime":"2017-09-27 19:27:41","useState":0,"zoomVal":"{\"current_x\":0,\"current_y\":0,\"idx\":0,\"max_x\":100,\"max_y\":100,\"min_x\":0,\"min_y\":0},{\"current_x\":0,\"current_y\":0,\"idx\":1,\"max_x\":0,\"max_y\":100,\"min_x\":-100,\"min_y\":0},{\"current_x\":0,\"current_y\":0,\"idx\":3,\"max_x\":0,\"max_y\":0,\"min_x\":-100,\"min_y\":-100},{\"current_x\":0,\"current_y\":0,\"idx\":2,\"max_x\":100,\"max_y\":0,\"min_x\":0,\"min_y\":-100}"},"launch":{"createAuthor":"","createTime":"2017-07-06 13:08:30","id":3,"isDefault":1,"isDelete":0,"launchName":"开机方案22","launchType":1,"mediaType":1,"mediaUrl":"http://oa6l8rxnr.bkt.clouddn.com/bootanimation.zip","updateAuthor":"系统管理员","updateTime":"2017-09-26 16:18:07"}}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
       int bussFlag = 0;

        public int getBussFlag() {
            return bussFlag;
        }

        public void setBussFlag(int bussFlag){
            this.bussFlag = bussFlag;
        }
    }
}
