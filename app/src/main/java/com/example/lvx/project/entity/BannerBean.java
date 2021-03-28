package com.example.lvx.project.entity;

/**
 * Description: 作用描述
 * Author: Lzj
 * CreateDate: 2021/3/23
 */
public class BannerBean {
    /**
     * id : 849810769116659711
     * advType : 1
     * createTime : 2017-04-06T02:27:31.000+0000
     * location : inx_LB
     * beginDate : 2016-12-31T16:00:00.000+0000
     * endDate : 2016-12-31T16:00:00.000+0000
     * advImg : http://img-1253650823.file.myqcloud.com/4c4b1a57-a38f-40ee-aa56-d54040601f70.jpg
     * advUrl : http://www.mstxx.cn/zt/2017cjb/
     * advName : 首页轮播图03
     */

    private String id;
    private int advType;
    private String createTime;
    private String location;
    private String beginDate;
    private String endDate;
    private String advImg;
    private String advUrl;
    private String advName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAdvType() {
        return advType;
    }

    public void setAdvType(int advType) {
        this.advType = advType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAdvImg() {
        return advImg;
    }

    public void setAdvImg(String advImg) {
        this.advImg = advImg;
    }

    public String getAdvUrl() {
        return advUrl;
    }

    public void setAdvUrl(String advUrl) {
        this.advUrl = advUrl;
    }

    public String getAdvName() {
        return advName;
    }

    public void setAdvName(String advName) {
        this.advName = advName;
    }
}
