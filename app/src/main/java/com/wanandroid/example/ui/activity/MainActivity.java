package com.wanandroid.example.ui.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wanandroid.example.base.BaseActivity;
import com.wanandroid.example.base.BaseAdapter;
import com.wanandroid.example.ui.fragment.MainFragment;
import com.wanandroid.example.R;
import com.wanandroid.example.ui.fragment.ProjectTabFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 * @author yangjx
 * @date 2019/1/5
 */
public class MainActivity extends BaseActivity {

    Unbinder unbinder;
    @BindView(R.id.main_tab_layout)
    TabLayout mainTablayout;
    @BindView(R.id.main_view_pager)
    ViewPager viewPager;
//    @BindView(R.id.common_toolbar_title_tv)
//    TextView commonToolbarTitle;
//    @BindView(R.id.common_toolbar)
//    Toolbar toolbar;

    private MainFragment mainFragment;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        initUI();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        BaseAdapter baseAdapter = new BaseAdapter();
    }

    /**
     * 初始化 UI
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initUI() {
        //commonToolbarTitle.setText("wanandroid");
        //toolbar.setNavigationIcon(null);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                switch (i){
                    case 0:
                        return new ProjectTabFragment();
                    case 1:
                        return new MainFragment();
                    case 2:
                        return new MainFragment();
                    case 3:
                        return new ProjectTabFragment();
                    default:
                        return new MainFragment();
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        });
        mainTablayout.setupWithViewPager(viewPager);

        for (int i = 0; i<4 ;i++){
            TabLayout.Tab tab = mainTablayout.getTabAt(i);
            View view = View.inflate(this,R.layout.tab_view,null);
            ImageView imageView = view.findViewById(R.id.tab_image_view);
            TextView textView = view.findViewById(R.id.tab_text_view);
            switch (i){
                case 0:
                    imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.icon_home_pager_not_selected));
                    textView.setText("首页");
                    break;
                case 1:
                    imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.icon_knowledge_hierarchy_not_selected));
                    textView.setText("知识体系");
                    break;
                case 2:
                    imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.icon_navigation_not_selected));
                    textView.setText("导航");
                    break;
                case 3:
                    imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.icon_project_not_selected));
                    textView.setText("项目");
                    break;
                    default:
            }

            tab.setCustomView(view);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null){
            unbinder.unbind();
        }
    }
}
