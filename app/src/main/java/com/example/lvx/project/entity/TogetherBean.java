package com.example.lvx.project.entity;

/**
 * Description: 作用描述
 * Author: Lzj
 * CreateDate: 2021/3/23
 */
public class TogetherBean {
    private String url;
    private String versionCode;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    @Override
    public String toString() {
        return "TogetherBean{" +
                "url='" + url + '\'' +
                ", versionCode='" + versionCode + '\'' +
                '}';
    }
}
