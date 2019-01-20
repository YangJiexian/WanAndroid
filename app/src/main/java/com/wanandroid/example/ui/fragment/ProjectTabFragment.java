package com.wanandroid.example.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidkun.xtablayout.XTabLayout;
import com.wanandroid.example.R;
import com.wanandroid.example.base.BaseFragment;
import com.wanandroid.example.core.BaseUrl;
import com.wanandroid.example.core.bean.ProjectTitleList;
import com.wanandroid.example.core.http.WanAndroidApis;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectTabFragment extends BaseFragment {

    ProjectTitleList projectTitleList;
    List<ProjectTitleList.ProjectTitleBean> dataTitle;


    @BindView(R.id.xTablayout)
    XTabLayout mTablayout;
    @BindView(R.id.project_view_pager)
    ViewPager mViewPager;

    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project,null);
        mTablayout = view.findViewById(R.id.xTablayout);
        ButterKnife.bind(this,view);
        mContext = getActivity();
        getProjectTitle();
        return view;
    }

    private void getProjectTitle() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BaseUrl.service)
                .build();

        WanAndroidApis wanAndroidApis = retrofit.create(WanAndroidApis.class);
        Call<ProjectTitleList> callBanner = wanAndroidApis.getProjectTitleList();
        callBanner.enqueue(new Callback<ProjectTitleList>() {
            @Override
            public void onResponse(Call<ProjectTitleList> call, Response<ProjectTitleList> response) {
                showTitle(response.body().getData());
                dataTitle = response.body().getData();
                setViewPager();
            }

            @Override
            public void onFailure(Call<ProjectTitleList> call, Throwable t) {

            }
        });



    }

    private void setViewPager() {
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                ProjectListFragment fragment = new ProjectListFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("project_type_id",dataTitle.get(position).getId());
                fragment.setArguments(bundle);
                return fragment;
            }

            @Override
            public int getCount() {
                return dataTitle == null ? 0 : dataTitle.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return dataTitle.get(position).getName();
            }
        });
        mTablayout.setupWithViewPager(mViewPager);
    }

    private void showTitle(List<ProjectTitleList.ProjectTitleBean> datas) {
//        for (int i = 0;i < datas.size() ;i++){
//            mTablayout.addTab(mTablayout.newTab().setText(datas.get(i).getName()));
//        }
    }



}
