package com.example.lvx.project.http;

import android.content.Context;
import com.example.lvx.project.Listener.ProgressListener;
import com.example.lvx.project.contants.AppConstant;
import com.example.lvx.project.contants.HttpConstant;
import com.example.lvx.project.entity.TokenRoot;
import com.example.lvx.project.http.api.DownloadApi;
import com.example.lvx.project.http.api.UserInfoApi;
import com.example.lvx.project.http.interceptor.ProgressResponseBody;
import com.example.lvx.project.http.interceptor.RedirectInterceptor;
import com.example.lvx.project.http.interceptor.StatisticsInterceptor;
import com.example.lvx.project.prefs.AppPreference;
import com.example.lvx.project.prefs.MySPCookiePersistor;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.Cookie;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description: 网络框架Retrofit封装
 * Author: Lzj
 * CreateDate: 2020/5/24
 */
public class HttpRetrofit {
    //private static String BASE_URL = HttpConstant.BASE_URL;
    private static String BASE_URL = HttpConstant.BASE_URL2;
    private static final int DEFAULT_TIMEOUT = 5;
    private static HttpRetrofit httpRetrofit;
    public static Retrofit retrofit;
    public static OkHttpClient okHttpClient;
    private Context context;
    private static ClearableCookieJar cookieJar;//cookie管理
    private static MySPCookiePersistor sharedPrefsCookiePersistor;//本地cookie管理

    private final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()// 调用serializeNulls方法，改变gson对象的默认行为，null值将被输出
            .create();

    public static HttpRetrofit getInstance(Context contextRet) {
        if(httpRetrofit==null) {
            synchronized (HttpRetrofit.class) {
                if (httpRetrofit == null)
                    httpRetrofit = new HttpRetrofit(contextRet);
            }
        }
        BASE_URL =  HttpConstant.BASE_URL;
        return httpRetrofit;
    }

    public HttpRetrofit(Context contextRet) {
        this.context = contextRet;
        init();
    }

    private void init(){
        SetCookieCache cookieCache = new SetCookieCache();
        sharedPrefsCookiePersistor = new MySPCookiePersistor(context);
        cookieJar = new PersistentCookieJar(cookieCache, sharedPrefsCookiePersistor);
        final OkHttpClient client = new OkHttpClient.Builder()//内部client，用于拦截重定向请求
                .addInterceptor(new HttpLoggingInterceptor())
                .cookieJar(cookieJar)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(new RedirectInterceptor(client))//使用重定向拦截器
                .addInterceptor(new StatisticsInterceptor(5,1000))//重试
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        String url = chain.request().url().toString();
                        String token = AppPreference.getString("token","");
                        Request.Builder request = chain.request().newBuilder();
                        if (!url.contains("/authentication/form") && !url.contains("/auth/weixin") && !url.contains("/authentication/mobile")) {
                            //手机登录短信登录以及微信登录接口不加token,在接口上已经添加过
                            request.addHeader("Authorization", token);
                        }
                        Response proceed = chain.proceed(request.build());
                        //如果token过期,去重新请求token 然后设置token的请求头 重新发起请求 用户无感
                        if (isTokenExpired(proceed)) {
                            if (token.equals("")) {
                               // IntentManager.toLoginActivity(context);
                            } else {
                                String newHeaderToken = getNewToken();
                                //使用新的Token，创建新的请求
                                Request newRequest = chain.request().newBuilder().addHeader("Authorization", newHeaderToken).build();
                                return chain.proceed(newRequest);
                            }
                        }
                        //如果用户被挤下线
                        if (isOffLine(proceed)) {
                            AppPreference.clearAll();//移除sp
                            HttpRetrofit.removeAllCookie();//清除缓存cookie
//                            Intent intent = new Intent(context, LoginActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent.putExtra("offline", true);
//                            context.startActivity(intent);
                        }
                        return proceed;
                    }
                })
                .addInterceptor(new LoggingInterceptor())
                .cookieJar(cookieJar)
                .build();
    }

    /**
     * 获取网络加载器器
     */
    public static <T> T createApi(Class<T> clazz) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        T t = retrofit.create(clazz);
        if (t instanceof DownloadApi) {//判断是否是DownloadApi
            return downloadApk().create(clazz);
        }
        return t;
    }
    /**
     * 获取网络加载器器
     */
    public static <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        T t = retrofit.create(clazz);
        return t;
    }

    private  static Retrofit  downloadApk(){
        Retrofit retrofitDownload = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient.newBuilder().addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response orginalResponse = chain.proceed(chain.request());
                        return orginalResponse.newBuilder()
                                .body(new ProgressResponseBody(orginalResponse.body(), new ProgressListener() {
                                    @Override
                                    public void onProgress(long progress, long total, boolean done) {
                                        // EventBus.getDefault().post(new ProgressEvent(total, progress, done));//传递进度
                                    }
                                })).build();
                    }
                }).build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofitDownload;
    }

    /**
     * 同步请求方式，获取最新的Token
     *
     * @return
     */
    private String getNewToken() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserInfoApi userApi = retrofit.create(UserInfoApi.class);
        String refreshToken = AppPreference.getString("refreshToken","");
        retrofit2.Call<JsonObject> refToken = userApi.getRefToken(AppConstant.SP_BASE_TOKEN, "refresh_token", refreshToken);
        retrofit2.Response<JsonObject> response = refToken.execute();
        if (null != response.body() && null != response.body().getAsJsonObject()) {
            JsonObject jsonObject = response.body().getAsJsonObject();
            Gson gson = new Gson();
            TokenRoot tokenBean = gson.fromJson(jsonObject, TokenRoot.class);
            if (null != tokenBean.getData()) {
                AppPreference.putString(AppConstant.SP_TOKEN, "bearer " + tokenBean.getData().getValue());
                AppPreference.putString(AppConstant.SP_REFRESHTOKEN, tokenBean.getData().getRefreshToken().getValue());
                return "bearer " + tokenBean.getData().getValue();
            }
        }
        return "";
    }

    /**
     * 根据Response，判断Token是否失效
     * 401表示token过期
     *
     * @param response
     * @return
     */
    private boolean isTokenExpired(Response response) {
        if (response.code() == 401) {
            return true;
        }
        return false;
    }
    /**
     * 根据Response，判断Token是否失效
     * 402表示用户被挤下线
     *
     * @param response
     * @return
     */
    private boolean isOffLine(Response response) {
        if (response.code() == 402) {
            return true;
        }
        return false;
    }

    /**
     * 删除cookie
     */
    public static void removeAllCookie() {
        cookieJar.clear();
    }

    /**
     * 获取所有cookie
     */
    public static List<Cookie> getAllCookie() {
        return sharedPrefsCookiePersistor.loadAll();
    }
}
