package com.wanandroid.example.core.http;

import com.wanandroid.example.core.bean.ArticleListData;
import com.wanandroid.example.core.bean.BannerListData;
import com.wanandroid.example.core.bean.LoginData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WanAndroidApis {

    @GET("banner/json")
    Call<BannerListData> getBannerData();

    @GET("article/list/{num}/json")
    Call<ArticleListData> getArticleList(@Path("num") int num);

    @POST("user/login")
    Call<LoginData> getLoginData(@Query("username") String username, @Query("password") String password);

}
