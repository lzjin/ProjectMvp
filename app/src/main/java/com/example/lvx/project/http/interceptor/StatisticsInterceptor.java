package com.example.lvx.project.http.interceptor;

import com.example.lvx.project.utils.MLog;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 拦截请求  失败重试
 * 统计接口使用
 */
public class StatisticsInterceptor implements Interceptor {
    private int mMaxRetryCount;//最大重试次数
    private long mRetryInterval;

    public StatisticsInterceptor(int maxRetryCount, long retryInterval) {
        mMaxRetryCount = maxRetryCount;
        mRetryInterval = retryInterval;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);//执行请求
        String url = request.url().toString();
        int retryNum = 0;
        while(!response.isSuccessful()&&retryNum<mMaxRetryCount){
            try {
                Thread.sleep(mRetryInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retryNum++;
            response = chain.proceed(request);
            MLog.e("--------------------重试次数="+retryNum);
        }
        return response;
    }

}
