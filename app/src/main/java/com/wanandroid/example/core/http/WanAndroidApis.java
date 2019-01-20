package com.wanandroid.example.core.http;

import com.wanandroid.example.core.bean.ArticleListData;
import com.wanandroid.example.core.bean.BannerBean;
import com.wanandroid.example.core.bean.LoginData;
import com.wanandroid.example.core.bean.ProjectData;
import com.wanandroid.example.core.bean.ProjectTitleList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WanAndroidApis {

    @GET("banner/json")
    Call<BannerBean> getBannerData();

    @GET("article/list/{num}/json")
    Call<ArticleListData> getArticleList(@Path("num") int num);

    @POST("user/login")
    Call<LoginData> getLoginData(@Query("username") String username, @Query("password") String password);

    @GET("project/tree/json")
    Call<ProjectTitleList> getProjectTitleList();

    @GET("project/list/{num}/json")
    Call<ProjectData> getProjectList(@Path("num") int num,@Query("cid") int cid);
}
