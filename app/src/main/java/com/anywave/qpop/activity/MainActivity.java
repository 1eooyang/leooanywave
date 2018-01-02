package com.anywave.qpop.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.anywave.qpop.R;
import com.anywave.qpop.adapter.LiveAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @BindView(R.id.lv_live)
    ListView lvLive;
    @BindView(R.id.my)
    ImageButton my;
    @BindView(R.id.live)
    ImageButton live;
    @BindView(R.id.news)
    ImageButton news;
    @BindView(R.id.wifi)
    ImageButton wifi;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.bus)
    ImageButton bus;

    LiveAdapter liveAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ArrayList<String> list = new ArrayList();
        for (int i = 0; i < 18; i++) {
            list.add(i+"");
        }
        list.add("");


        liveAdapter = new LiveAdapter(this,list);

        lvLive.setAdapter(liveAdapter);

    }


    @OnClick({R.id.my, R.id.live, R.id.news, R.id.wifi, R.id.bus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my:
                break;
            case R.id.live:
                break;
            case R.id.news:
                break;
            case R.id.wifi:
                break;
            case R.id.bus:


                break;
        }
    }
}

