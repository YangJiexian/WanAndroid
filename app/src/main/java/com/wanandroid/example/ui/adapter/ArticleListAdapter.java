package com.wanandroid.example.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanandroid.example.R;
import com.wanandroid.example.core.bean.ArticleListData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleListAdapter extends BaseQuickAdapter<ArticleListData.ArticleListBean.ArticleBean,ArticleListAdapter.ArticleListViewHolder> {


    public ArticleListAdapter(int layoutResId, @Nullable List<ArticleListData.ArticleListBean.ArticleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(ArticleListViewHolder helper, ArticleListData.ArticleListBean.ArticleBean article) {
        if (!TextUtils.isEmpty(article.getTitle())) {
            helper.setText(R.id.item_search_pager_title, Html.fromHtml(article.getTitle()));
        }
    }

    public class ArticleListViewHolder extends BaseViewHolder {

        @BindView(R.id.item_search_pager_group)
        CardView mItemSearchPagerGroup;
        @BindView(R.id.item_search_pager_like_iv)
        ImageView mItemSearchPagerLikeIv;
        @BindView(R.id.item_search_pager_title)
        TextView mItemSearchPagerTitle;
        @BindView(R.id.item_search_pager_author)
        TextView mItemSearchPagerAuthor;
        @BindView(R.id.item_search_pager_tag_green_tv)
        TextView mTagGreenTv;
        @BindView(R.id.item_search_pager_tag_red_tv)
        TextView mTagRedTv;
        @BindView(R.id.item_search_pager_chapterName)
        TextView mItemSearchPagerChapterName;
        @BindView(R.id.item_search_pager_niceDate)
        TextView mItemSearchPagerNiceDate;

        public ArticleListViewHolder(View view) {
            super(view);
            ButterKnife.bind(view);
        }
    }

}
