package com.anywave.qpop.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.anywave.qpop.R;
import com.anywave.qpop.adapter.MainPagerAdapter;
import com.anywave.qpop.adapter.MyPagerAdapter;
import com.anywave.qpop.fragment.LiveFragment;
import com.anywave.qpop.fragment.NewsFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.pager)
    ViewPager pager;
    MainPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new LiveFragment());
        fragments.add(new NewsFragment());



        adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(adapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
}
