package com.spring.sutils.db;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 上午11:58 2018/7/31 2018
 * @Modify:
 */
public class OssTokenVo {
    private String address;
    private String OSSAccessKeyId;
    private String Signature;
    private String policy;
    private String path;
    private String key;
    private Integer error;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOSSAccessKeyId() {
        return OSSAccessKeyId;
    }

    public void setOSSAccessKeyId(String OSSAccessKeyId) {
        this.OSSAccessKeyId = OSSAccessKeyId;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }
}
