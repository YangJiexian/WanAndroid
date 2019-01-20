package com.wanandroid.example.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanandroid.example.R;
import com.wanandroid.example.core.bean.ArticleListData;
import com.wanandroid.example.core.bean.ProjectData;
import com.wanandroid.example.core.bean.ProjectTitleList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectListAdapter extends BaseQuickAdapter<ProjectData.ProjectBeanList.ProjectBean,ProjectListAdapter.ProjectListViewHolder> {


    public ProjectListAdapter(int layoutResId, @Nullable List<ProjectData.ProjectBeanList.ProjectBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(ProjectListViewHolder helper, ProjectData.ProjectBeanList.ProjectBean article) {
        if (!TextUtils.isEmpty(article.getTitle())) {
            helper.setText(R.id.item_project_list_title_tv,article.getTitle());
        }else if ((!TextUtils.isEmpty(article.getEnvelopePic()))){
            ImageView imageView = helper.getView(R.id.item_project_list_iv);
            Glide.with(mContext).load(article.getEnvelopePic()).into(imageView);
        }
    }

    public class ProjectListViewHolder extends BaseViewHolder {

        @BindView(R.id.item_project_list_iv)
        ImageView item_project_list_iv;
        @BindView(R.id.item_project_list_title_tv)
        TextView item_project_list_title_tv;
        @BindView(R.id.item_project_list_content_tv)
        TextView item_project_list_content_tv;
        @BindView(R.id.item_project_list_time_tv)
        TextView item_project_list_time_tv;

        public ProjectListViewHolder(View view) {
            super(view);
            ButterKnife.bind(view);
        }
    }

}
