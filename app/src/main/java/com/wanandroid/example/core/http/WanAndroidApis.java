package com.wanandroid.example.core.http;

import com.wanandroid.example.core.bean.ArticleListData;
import com.wanandroid.example.core.bean.BannerListData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WanAndroidApis {

    @GET("banner/json")
    Call<BannerListData> getBannerData();

    @GET("article/list/{num}/json")
    Call<ArticleListData> getArticleList(@Path("num") int num);

}
