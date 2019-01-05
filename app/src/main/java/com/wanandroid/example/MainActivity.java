package com.wanandroid.example;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 * @author yangjx
 * @date 2019/1/5
 */
public class MainActivity extends AppCompatActivity {

    Unbinder unbinder;
    @BindView(R.id.main_tab_layout)
    TabLayout mainTablayout;
    @BindView(R.id.main_view_pager)
    ViewPager viewPager;

    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        initUI();
    }

    /**
     * 初始化 UI
     */
    private void initUI() {
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return new MainFragment();
            }

            @Override
            public int getCount() {
                return 1;
            }
        });
        mainTablayout.setupWithViewPager(viewPager);

        TabLayout.Tab tab = mainTablayout.getTabAt(0);
        View view = View.inflate(this,R.layout.tab_view,null);
        tab.setCustomView(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null){
            unbinder.unbind();
        }
    }
}
