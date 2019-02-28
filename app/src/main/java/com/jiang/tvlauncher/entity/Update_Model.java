package com.jiang.tvlauncher.entity;

/**
 * Created by  jiang
 * on 2017/7/16.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 更新
 * update：
 */
public class Update_Model extends Base_Model {

    /**
     * errorcode : 1000
     * result : {"buildNum":10,"createAuthor":"","createTime":"2017-06-29 18:03:51","devType":1,"downloadUrl":"http://127.0.0.1:8020/web_feekr_admin/index.html?__hbt=1498720532772","id":1,"isDelete":0,"remark":"http://127.0.0.1:8020/web_feekr_admin/index.html?__hbt=1498720532772","updateAuthor":"","updateTime":"2017-06-29 18:03:51","updateType":2,"useState":1,"versionNum":"1.0.0"}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * buildNum : 10
         * createAuthor :
         * createTime : 2017-06-29 18:03:51
         * devType : 1
         * downloadUrl : http://127.0.0.1:8020/web_feekr_admin/index.html?__hbt=1498720532772
         * id : 1
         * isDelete : 0
         * remark : http://127.0.0.1:8020/web_feekr_admin/index.html?__hbt=1498720532772
         * updateAuthor :
         * updateTime : 2017-06-29 18:03:51
         * updateType : 2
         * useState : 1
         * versionNum : 1.0.0
         */

        private int buildNum;
        private String createAuthor;
        private String createTime;
        private int devType;
        private String downloadUrl;
        private int id;
        private int isDelete;
        private String remark;
        private String updateAuthor;
        private String updateTime;
        private int updateType;
        private int useState;
        private String versionNum;

        public int getBuildNum() {
            return buildNum;
        }

        public void setBuildNum(int buildNum) {
            this.buildNum = buildNum;
        }

        public String getCreateAuthor() {
            return createAuthor;
        }

        public void setCreateAuthor(String createAuthor) {
            this.createAuthor = createAuthor;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDevType() {
            return devType;
        }

        public void setDevType(int devType) {
            this.devType = devType;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getUpdateAuthor() {
            return updateAuthor;
        }

        public void setUpdateAuthor(String updateAuthor) {
            this.updateAuthor = updateAuthor;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getUpdateType() {
            return updateType;
        }

        public void setUpdateType(int updateType) {
            this.updateType = updateType;
        }

        public int getUseState() {
            return useState;
        }

        public void setUseState(int useState) {
            this.useState = useState;
        }

        public String getVersionNum() {
            return versionNum;
        }

        public void setVersionNum(String versionNum) {
            this.versionNum = versionNum;
        }
    }
}
