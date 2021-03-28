package com.example.lvx.project.http;

import com.example.lvx.project.base.BaseResponseBean;
import com.example.lvx.project.entity.BannerBean;
import com.example.lvx.project.entity.LoginBean;
import com.example.lvx.project.entity.VersionBean;

import java.util.List;
import java.util.Map;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Description: 接口
 * Author: Administrator
 * CreateDate: 2020/4/7
 */
public interface ApiService {

    public static final String Base_URL = "http://www.danqiuedu.com/";//接口生产环境

    //   public static final String Base_URL = "http://10.0.0.118:90/";//内网测试环境


    //登录
    @Headers("Content-type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("uapi/api/pub/user/login")
    Observable<BaseResponseBean<LoginBean>> login(@FieldMap Map<String, Object> map);
    //获取验证码
    @GET("uapi/api/pub/user/sms")
    Observable<ResponseBody> getVerifyCode(@Query("telephone") String telephone, @Query("sign") String sign);
    //获取验证码 注册时
    @GET("uapi/api/pub/user/register/sms")
    Observable<ResponseBody> getVerifyCodeReg(@Query("telephone") String telephone, @Query("sign") String sign);
    //验证码登录
    @Headers("Content-type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("uapi/api/pub/user/smslogin")
    Observable<BaseResponseBean<LoginBean>> smslogin(@FieldMap Map<String, Object> map);

    //广告
    @GET("uapi/api/pub/banner/data")
    Observable<BaseResponseBean<List<BannerBean>>> getBanner();

    //版本更新
    @GET("uapi/api/pub/sysConfig/getVal")
    Observable<BaseResponseBean<VersionBean>> getVersionCode(@Query("paramKey") String paramKey);
}
