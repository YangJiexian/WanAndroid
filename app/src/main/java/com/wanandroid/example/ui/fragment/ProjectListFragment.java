package com.wanandroid.example.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidkun.xtablayout.XTabLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wanandroid.example.R;
import com.wanandroid.example.base.BaseFragment;
import com.wanandroid.example.core.BaseUrl;
import com.wanandroid.example.core.bean.ProjectData;
import com.wanandroid.example.core.bean.ProjectTitleList;
import com.wanandroid.example.core.http.WanAndroidApis;
import com.wanandroid.example.ui.adapter.ProjectListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectListFragment extends BaseFragment {

    @BindView(R.id.project_list_recycler_view)
    RecyclerView mProjectRecyclerView;
    @BindView(R.id.project_list_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    private Context mContext;

    private int mProjectTypeId;
    private int mCurrentPage = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_list,null);
        ButterKnife.bind(this,view);
        mContext = getActivity();
        Bundle bundle = getArguments();
        assert bundle != null;
        mProjectTypeId = bundle.getInt("project_type_id");
        initView();
        return view;
    }

    private List<ProjectData.ProjectBeanList.ProjectBean> list = new ArrayList<>();
    ProjectListAdapter projectListAdapter;
    private void initView() {
        mSmartRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mCurrentPage = 1;
                getProject(mCurrentPage);
                mSmartRefreshLayout.finishRefresh(1000);
            }
        });
        mSmartRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mCurrentPage++;
                getProject(mCurrentPage);
                mSmartRefreshLayout.finishLoadMore(1000);
            }
        });

        projectListAdapter = new ProjectListAdapter(R.layout.item_project_list,list);
        mProjectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProjectRecyclerView.setAdapter(projectListAdapter);

        getProject(1);
    }

    private void getProject(int page) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BaseUrl.service)
                .build();

        WanAndroidApis wanAndroidApis = retrofit.create(WanAndroidApis.class);
        Call<ProjectData> callBanner = wanAndroidApis.getProjectList(1,mProjectTypeId);
        callBanner.enqueue(new Callback<ProjectData>() {
            @Override
            public void onResponse(Call<ProjectData> call, Response<ProjectData> response) {
                //showTitle(response.body().getData());
                list = response.body().getData().getDatas();
                if (mCurrentPage != 1){
                    projectListAdapter.addData(list);
                }else {
                    projectListAdapter.replaceData(list);
                }

            }

            @Override
            public void onFailure(Call<ProjectData> call, Throwable t) {

            }
        });
    }

}
