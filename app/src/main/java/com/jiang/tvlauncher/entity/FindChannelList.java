package com.jiang.tvlauncher.entity;

import java.util.List;

/**
 * Created by wwwfa on 2017/8/17.
 */

public class FindChannelList extends Base_Model {
    private static final String TAG = "FindChannelList";

    /**
     * errorcode : 1000
     * result : [{"appList":[{"appName":"优酷","createAuthor":"","createTime":"2017-07-18 16:10:59","iconUrl":"","id":1,"isDelete":0,"packageName":"com.youku.la","remark":"","updateAuthor":"","updateTime":"2017-07-18 16:10:59","useState":1},{"appName":"奇异果","createAuthor":"","createTime":"2017-07-18 16:12:01","iconUrl":"","id":2,"isDelete":0,"packageName":"com.gitvjimi.video","remark":"测试","updateAuthor":"系统管理员","updateTime":"2017-08-23 20:13:13","useState":1}],"bgUrl":"http://7xkcno.com2.z0.glb.qiniucdn.com/3D338FEA957441E6303808B33D2628B2.jpg","channelCode":"","channelName":"全网影视","contentType":2,"contentUrl":"","createAuthor":"系统管理员","createTime":"2017-08-11 17:49:03","id":2,"isDelete":0,"parentId":0,"remark":"123","updateAuthor":"系统管理员","updateTime":"2017-08-17 18:02:06","useState":1},{"appList":[{"appName":"奇异果","createAuthor":"","createTime":"2017-07-18 16:12:01","iconUrl":"","id":2,"isDelete":0,"packageName":"com.gitvjimi.video","remark":"测试","updateAuthor":"系统管理员","updateTime":"2017-08-23 20:13:13","useState":1}],"bgUrl":"http://7xkcno.com2.z0.glb.qiniucdn.com/FCD42FBA94AC5FB603D594A431189F38.jpg","channelCode":"","channelName":"电视直播","contentType":1,"contentUrl":"","createAuthor":"系统管理员","createTime":"2017-08-17 18:50:17","id":3,"isDelete":0,"parentId":0,"remark":"","updateAuthor":"系统管理员","updateTime":"2017-08-17 18:50:17","useState":0},{"appList":[],"bgUrl":"http://7xkcno.com2.z0.glb.qiniucdn.com/167587314ADA5E1166FA3E83EF9EED83.jpg","channelCode":"","channelName":"VIP同步院线","contentType":0,"contentUrl":"","createAuthor":"系统管理员","createTime":"2017-08-11 17:40:50","id":1,"isDelete":0,"parentId":0,"remark":"123","updateAuthor":"系统管理员","updateTime":"2017-08-17 18:03:33","useState":1},{"appList":[],"bgUrl":"http://7xkcno.com2.z0.glb.qiniucdn.com/FCD42FBA94AC5FB603D594A431189F38.jpg","channelCode":"","channelName":"更多","contentType":4,"contentUrl":"http://7xkcno.com2.z0.glb.qiniucdn.com/BAE9EBA759CFBF710EFD301901DB258B.mp4","createAuthor":"系统管理员","createTime":"2017-08-17 18:50:17","id":4,"isDelete":0,"parentId":0,"remark":"","updateAuthor":"系统管理员","updateTime":"2017-08-17 19:11:10","useState":1}]
     */

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * appList : [{"appName":"优酷","createAuthor":"","createTime":"2017-07-18 16:10:59","iconUrl":"","id":1,"isDelete":0,"packageName":"com.youku.la","remark":"","updateAuthor":"","updateTime":"2017-07-18 16:10:59","useState":1},{"appName":"奇异果","createAuthor":"","createTime":"2017-07-18 16:12:01","iconUrl":"","id":2,"isDelete":0,"packageName":"com.gitvjimi.video","remark":"测试","updateAuthor":"系统管理员","updateTime":"2017-08-23 20:13:13","useState":1}]
         * bgUrl : http://7xkcno.com2.z0.glb.qiniucdn.com/3D338FEA957441E6303808B33D2628B2.jpg
         * channelCode :
         * channelName : 全网影视
         * contentType : 2
         * contentUrl :
         * createAuthor : 系统管理员
         * createTime : 2017-08-11 17:49:03
         * id : 2
         * isDelete : 0
         * parentId : 0
         * remark : 123
         * updateAuthor : 系统管理员
         * updateTime : 2017-08-17 18:02:06
         * useState : 1
         */

        private String bgUrl;
        private String channelCode;
        private String channelName;
        private int contentType;
        private String contentUrl;
        private String createAuthor;
        private String createTime;
        private int id;
        private int isDelete;
        private int parentId;
        private String remark;
        private String updateAuthor;
        private String updateTime;
        private int useState;
        private List<AppListBean> appList;

        public String getBgUrl() {
            return bgUrl;
        }

        public void setBgUrl(String bgUrl) {
            this.bgUrl = bgUrl;
        }

        public String getChannelCode() {
            return channelCode;
        }

        public void setChannelCode(String channelCode) {
            this.channelCode = channelCode;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public int getContentType() {
            return contentType;
        }

        public void setContentType(int contentType) {
            this.contentType = contentType;
        }

        public String getContentUrl() {
            return contentUrl;
        }

        public void setContentUrl(String contentUrl) {
            this.contentUrl = contentUrl;
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

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
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

        public int getUseState() {
            return useState;
        }

        public void setUseState(int useState) {
            this.useState = useState;
        }

        public List<AppListBean> getAppList() {
            return appList;
        }

        public void setAppList(List<AppListBean> appList) {
            this.appList = appList;
        }

        public static class AppListBean {
            /**
             * appName : 优酷
             * createAuthor :
             * createTime : 2017-07-18 16:10:59
             * iconUrl :
             * id : 1
             * isDelete : 0
             * packageName : com.youku.la
             * remark :
             * updateAuthor :
             * updateTime : 2017-07-18 16:10:59
             * useState : 1
             */

            private String appName;
            private String createAuthor;
            private String createTime;
            private String iconUrl;
            private String downloadUrl;
            private String downloadUrlBak;
            private int id;
            private int isDelete;
            private String packageName;
            private String packageNameBak;
            private String remark;
            private String updateAuthor;
            private String updateTime;
            private int useState;

            public String getDownloadUrlBak() {
                return downloadUrlBak;
            }

            public void setDownloadUrlBak(String downloadUrlBak) {
                this.downloadUrlBak = downloadUrlBak;
            }

            public String getPackageNameBak() {
                return packageNameBak;
            }

            public void setPackageNameBak(String packageNameBak) {
                this.packageNameBak = packageNameBak;
            }

            public String getDownloadUrl() {
                return downloadUrl;
            }

            public void setDownloadUrl(String downloadUrl) {

                this.downloadUrl = downloadUrl;
            }

            public String getAppName() {
                return appName;
            }

            public void setAppName(String appName) {
                this.appName = appName;
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

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
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

            public String getPackageName() {
                return packageName;
            }

            public void setPackageName(String packageName) {
                this.packageName = packageName;
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

            public int getUseState() {
                return useState;
            }

            public void setUseState(int useState) {
                this.useState = useState;
            }
        }
    }
}
