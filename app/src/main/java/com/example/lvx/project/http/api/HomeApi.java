package com.example.lvx.project.http.api;


import com.example.lvx.project.base.BaseResponseBean;
import com.example.lvx.project.entity.NewsData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Description: 作用描述
 * Author: Lzj
 * CreateDate: 2020/5/24
 */
public interface HomeApi {

    @GET("api/information/app")
    Call<BaseResponseBean<NewsData>> getHealthNews(@Query("page") int page, @Query("size") int size);

}
