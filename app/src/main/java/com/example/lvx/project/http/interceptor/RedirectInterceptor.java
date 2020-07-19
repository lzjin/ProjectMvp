package com.example.lvx.project.http.interceptor;

import com.example.lvx.project.contants.HttpConstant;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 拦截请求重定向
 */

public class  RedirectInterceptor implements Interceptor {

    private OkHttpClient client;
    public RedirectInterceptor(OkHttpClient client) {
        this.client = client;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().toString();
        String cookie = request.header("Cookie");
        switch (url){
            case HttpConstant.URL_LOGIN://sign_check去掉AppAuthToken，会还原出用户。保留session，因为后台验证码需要session
                if (cookie!=null && cookie.contains("AppAuthToken")){
                    String sessionId = cookie.substring(0, cookie.indexOf("AppAuthToken"));
                    request=request.newBuilder().removeHeader("Cookie").addHeader("Cookie",sessionId).build();
                }
                break;
            case HttpConstant.URL_IS_REGISTERED://registered去掉cookie
                if (cookie!=null){
                    request=request.newBuilder().removeHeader("Cookie").build();
                }
                break;
        }

//        if (url.equals(HttpConstant.URL_LOGIN) || url.equals(HttpConstant.URL_IS_REGISTERED)){//去掉sign_check和registered的cookie
//            String cookie = request.header("Cookie");
//            if (cookie!=null && cookie.contains("AppAuthToken")){
//                String sessionId = cookie.substring(0, cookie.indexOf("AppAuthToken"));
//                request=request.newBuilder().removeHeader("Cookie").addHeader("Cookie",sessionId).build();
//            }
//            if (cookie!=null){
//                request=request.newBuilder().removeHeader("Cookie").build();
//            }
//
//        }

        Response responseNromal = chain.proceed(request);//执行请求
//        if (responseNromal.isRedirect()) {//是重定向请求
//            Response responseNew = interceptRedirect(request, responseNromal);//检查重定向
//            if (responseNew!=null){
//                return responseNew;
//            }
//        }
        return responseNromal;
    }






}
