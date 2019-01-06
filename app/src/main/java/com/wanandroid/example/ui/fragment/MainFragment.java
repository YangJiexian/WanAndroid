package com.wanandroid.example.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.wanandroid.example.R;
import com.wanandroid.example.core.BaseUrl;
import com.wanandroid.example.core.bean.ArticleListData;
import com.wanandroid.example.core.bean.BannerListData;
import com.wanandroid.example.core.http.WanAndroidApis;
import com.wanandroid.example.ui.adapter.ArticleListAdapter;
import com.wanandroid.example.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainFragment extends Fragment {

    private Banner banner;
    private Context mContext;
    private ArticleListAdapter articleListAdapter;
    private List<ArticleListData.ArticleListBean.ArticleBean> articleBeanList;

    @BindView(R.id.main_fragment_recycler_view)
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,null);
        ButterKnife.bind(view);
        mRecyclerView = view.findViewById(R.id.main_fragment_recycler_view);
        mContext = getActivity();
        initView();
        return view;
    }

    private void initView() {
        articleBeanList = new ArrayList<>();
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.head_banner,null);
        banner = linearLayout.findViewById(R.id.head_banner);
        linearLayout.removeView(banner);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        articleListAdapter = new ArticleListAdapter(R.layout.item_article,articleBeanList);
        articleListAdapter.addHeaderView(banner);
        mRecyclerView.setAdapter(articleListAdapter);
        initBanner();
    }

    private void initBanner() {
        getBannerData();
    }

    private void getBannerData() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BaseUrl.service)
                .build();

        WanAndroidApis wanAndroidApis = retrofit.create(WanAndroidApis.class);
        Call<BannerListData> callBanner = wanAndroidApis.getBannerData();
        callBanner.enqueue(new Callback<BannerListData>() {
            @Override
            public void onResponse(Call<BannerListData> call, Response<BannerListData> response) {
                showBanner(response.body().getData());
            }

            @Override
            public void onFailure(Call<BannerListData> call, Throwable t) {

            }
        });
        Call<ArticleListData> callArticle = wanAndroidApis.getArticleList(1);
        callArticle.enqueue(new Callback<ArticleListData>() {
            @Override
            public void onResponse(Call<ArticleListData> call, Response<ArticleListData> response) {
                articleListAdapter.replaceData(response.body().getData().getDatas());
            }

            @Override
            public void onFailure(Call<ArticleListData> call, Throwable t) {

            }
        });
    }

    public void showBanner(List<BannerListData.BannerDataBean> BannerDataBean){
        List mBannerTitleList = new ArrayList<>();
        List<String> bannerImageList = new ArrayList<>();
        List mBannerUrlList = new ArrayList<>();
        for (BannerListData.BannerDataBean bannerData : BannerDataBean) {
            mBannerTitleList.add(bannerData.getTitle());
            bannerImageList.add(bannerData.getImagePath());
            mBannerUrlList.add(bannerData.getUrl());
        }
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(bannerImageList);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(mBannerTitleList);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(BannerDataBean.size() * 400);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (banner != null){
            banner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (banner != null){
            banner.stopAutoPlay();
        }
    }
}