package com.example.lvx.project.http.interceptor;

import android.content.Context;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 拦截请求
 * 统计接口使用
 */
public class StatisticsInterceptor implements Interceptor {
    private Context context;

    public StatisticsInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().toString();
        Response responseNromal = chain.proceed(request);//执行请求
        //请求统计
//        long duration = responseNromal.receivedResponseAtMillis() - responseNromal.sentRequestAtMillis();
//        if (duration > 200) {
//            HashMap<String, String> map = new HashMap<String, String>();
//            map.put("url", url);
//            map.put("userId", SPUtil.getString(context, SPConstant.SP_USER_ID));
//            MobclickAgent.onEventValue(context, "RESTAPI_SLOW", map, (int) duration);
//        }
        return responseNromal;
    }

}
