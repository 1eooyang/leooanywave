package com.anywave.qpop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.anywave.qpop.App;
import com.anywave.qpop.R;
import com.anywave.qpop.activity.LiveDetailActivity;
import com.anywave.qpop.adapter.LiveAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LiveFragment extends Fragment {


    //    @BindView(R.id.lv_live)
//    ListView lvLive;
//    @BindView(R.id.srl)
//    SwipeRefreshLayout srl;
//    Unbinder unbinder;
    LiveAdapter liveAdapter;
    Context context;
    ViewHolder holder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live, container, false);

        holder = new ViewHolder(view);
//        unbinder = ButterKnife.bind(this, view);

        EventBus.getDefault().register(this);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<String> list = new ArrayList();

        for (int i = 0; i < 18; i++) {
            list.add(i + "");
        }
        list.add("");


        liveAdapter = new LiveAdapter(App.context, list);

        holder.lvLive.setAdapter(liveAdapter);

        holder.lvLive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                App.startActivity2(LiveDetailActivity.class);

            }
        });

        holder.srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                        EventBus.getDefault().post(new String());
                    }
                }).start();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }

    @OnClick({R.id.iv_title, R.id.cb_pull})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title:
                break;
            case R.id.cb_pull:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(String event) {
        holder.srl.setRefreshing(false);
    }

    static class ViewHolder {
        @BindView(R.id.lv_live)
        ListView lvLive;
        @BindView(R.id.srl)
        SwipeRefreshLayout srl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
