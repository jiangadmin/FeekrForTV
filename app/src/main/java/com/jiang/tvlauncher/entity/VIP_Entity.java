package com.jiang.tvlauncher.entity;

/**
 * @author: jiangyao
 * @date: 2018/5/15
 * @Email: www.fangmu@qq.com
 * @Phone: 186 6120 1018
 * TODO:
 */
public class VIP_Entity extends BaseEntity {

    /**
     * errorcode : 1000
     * result : {"accessToken":"T5JUTtpz+cz8s0iWV2ULaDnqP/j7OUs1pXZ7rKPdGvH7sh63t2HbUM2ZhjwW1cCL9IWoBRcCU9ugli7GF+Qcg/QfY/iYJii8","vtoken":"0B40654679E60821B42A9D028B56AAA1","vuid":"278113277"}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public class ResultBean {
        /**
         * accessToken : T5JUTtpz+cz8s0iWV2ULaDnqP/j7OUs1pXZ7rKPdGvH7sh63t2HbUM2ZhjwW1cCL9IWoBRcCU9ugli7GF+Qcg/QfY/iYJii8
         * vtoken : 0B40654679E60821B42A9D028B56AAA1
         * vuid : 278113277
         */

        private String accessToken;
        private String vtoken;
        private long vuid;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getVtoken() {
            return vtoken;
        }

        public void setVtoken(String vtoken) {
            this.vtoken = vtoken;
        }

        public long getVuid() {
            return vuid;
        }

        public void setVuid(long vuid) {
            this.vuid = vuid;
        }
    }


}
