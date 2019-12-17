package com.lw.bookkeeping.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.gyf.immersionbar.ImmersionBar;
import com.lw.bookkeeping.R;
import com.lw.bookkeeping.View.TabView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends Fragment {

    @BindView(R.id.main_view_pager)
    ViewPager mViewPager;

    @BindArray(R.array.tab_array)
    String[] mTabTitles;

    @BindView(R.id.tab_home)
    TabView mTabHome;

    @BindView(R.id.tab_detail)
    TabView mTabDetail;

    @BindView(R.id.tab_chart)
    TabView mTabChart;

    @BindView(R.id.tab_mine)
    TabView mTabMine;

    private List<TabView> mTabViews = new ArrayList<>();

    private List<Fragment> fragmentList;

    private static final int INDEX_HOME = 0;
    private static final int INDEX_DETAIL = 1;
    private static final int INDEX_CHART = 2;
    private static final int INDEX_MINE = 3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);  // 注册所有组件

        ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .keyboardEnable(false)  //解决软键盘与底部输入框冲突问题
                .init();

        mTabViews.add(mTabHome);
        mTabViews.add(mTabDetail);
        mTabViews.add(mTabChart);
        mTabViews.add(mTabMine);

        MainHomeFragment mainHomeFragment = new MainHomeFragment();
        MainDetailFragment mainDetailFragment = new MainDetailFragment();
        MainChartFragment mainChartFragment = new MainChartFragment();
        MainMineFragment mainMineFragment = new MainMineFragment();

        fragmentList = new ArrayList<>();
        fragmentList.add(mainHomeFragment);
        fragmentList.add(mainDetailFragment);
        fragmentList.add(mainChartFragment);
        fragmentList.add(mainMineFragment);

        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.popBackStack(RecordFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);

        mViewPager.setOffscreenPageLimit(mTabTitles.length - 1);
        mViewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            /**
             * @param position 滑动的时候，position总是代表左边的View， position+1总是代表右边的View
             * @param positionOffset 左边View位移的比例
             * @param positionOffsetPixels 左边View位移的像素
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 左边View进行动画
                mTabViews.get(position).setXPercentage(1 - positionOffset);
                // 如果positionOffset非0，那么就代表右边的View可见，也就说明需要对右边的View进行动画
                if (positionOffset > 0) {
                    mTabViews.get(position + 1).setXPercentage(positionOffset);
                }
            }
        });
    }

    private void updateCurrentTab(int index) {
        for (int i = 0; i < mTabViews.size(); i++) {
            if (index == i) {
                mTabViews.get(i).setXPercentage(1);
            } else {
                mTabViews.get(i).setXPercentage(0);
            }
        }
    }

    @OnClick({R.id.tab_home, R.id.tab_detail, R.id.tab_chart, R.id.tab_mine})
    void onClickTab(View v) {
        switch (v.getId()) {
            case R.id.tab_home:
                mViewPager.setCurrentItem(INDEX_HOME, false);
                updateCurrentTab(INDEX_HOME);
                break;
            case R.id.tab_detail:
                mViewPager.setCurrentItem(INDEX_DETAIL, false);
                updateCurrentTab(INDEX_DETAIL);
                break;
            case R.id.tab_chart:
                mViewPager.setCurrentItem(INDEX_CHART, false);
                updateCurrentTab(INDEX_CHART);
                break;
            case R.id.tab_mine:
                mViewPager.setCurrentItem(INDEX_MINE, false);
                updateCurrentTab(INDEX_MINE);
                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
