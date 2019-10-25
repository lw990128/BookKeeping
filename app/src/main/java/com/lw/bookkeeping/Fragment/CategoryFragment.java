package com.lw.bookkeeping.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.lw.bookkeeping.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.category_view_pager)
    ViewPager cViewPager;

    @BindView(R.id.btn_spend)
    Button btnSpend;

    @BindView(R.id.btn_income)
    Button btnIncome;

    @BindView(R.id.btn_backToMain)
    Button btnBackToMain;

    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.category_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);  // 注册所有组件

        initEvent();  // 添加监听

        CategorySpendFragment categorySpendFragment = new CategorySpendFragment();
        CategoryIncomeFragment categoryIncomeFragment = new CategoryIncomeFragment();

        fragmentList.add(categorySpendFragment);
        fragmentList.add(categoryIncomeFragment);

        cViewPager.setOffscreenPageLimit(2);
        cViewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        cViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initEvent(){
        btnIncome.setOnClickListener(this);
        btnSpend.setOnClickListener(this);
        btnBackToMain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_spend:
                cViewPager.setCurrentItem(0);
                break;
            case R.id.btn_income:
                cViewPager.setCurrentItem(1);
                break;
            case R.id.btn_backToMain:
                Navigation.findNavController(btnBackToMain)
                        .popBackStack();
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
