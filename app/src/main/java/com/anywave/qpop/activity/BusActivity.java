package com.anywave.qpop.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anywave.qpop.App;
import com.anywave.qpop.R;
import com.anywave.qpop.adapter.BusAdapter;
import com.anywave.qpop.bean.BusAdapterBean;
import com.anywave.qpop.bean.BusBean;
import com.anywave.qpop.http.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BusActivity extends Activity {


    BusAdapter busAdapter;
//    @BindView(R.id.bus_close)
//    ImageView busClose;
    @BindView(R.id.bus_name)
    TextView busName;
    @BindView(R.id.rl_bus)
    RelativeLayout rlBus;
    @BindView(R.id.lv_bus)
    ListView lvBus;
    @BindView(R.id.tv_select)
    TextView tvSelect;

    private static final String TAG = "BusActivity";
    @BindView(R.id.rl_not_null)
    RelativeLayout rlNotNull;
    @BindView(R.id.rl_null)
    RelativeLayout rlNull;
    private ArrayList<BusBean> buslist;
    ArrayList<String> list;

    Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        buslist = new ArrayList<>();
        list = new ArrayList();

        thread();
        http();



//        MyHttp.get(Constants.gps);
//        MyHttp.getStations(Constants.stations);


//
//        ArrayList<String> list = new ArrayList();
//
//        for (int i = 0; i < 38; i++) {
//            list.add(i + "宜山虹梅路站");
//        }
//        list.add("肇嘉浜路天平路(徐家汇)站");
//
//        busAdapter = new BusAdapter(this, list);

        lvBus.setAdapter(busAdapter);

        lvBus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


        busName.setText("欢迎乘坐" +
                getIntent().getStringExtra("params") +
                "路公交车");
    }

    private void thread() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                thread = this;
                while (true){
                    try {
                        sleep(800);
                        Log.e(TAG, "run: "+ App.currentBusStations);


                        if (App.busAdapter != null&&App.busAdapter.map !=null) {
                            Map<Integer,Boolean> map = App.busAdapter.map;

                            for (Integer key : map.keySet()) {
                                map.get(key);
                                if ( map.get(key)) {
                                    App.currentBusStations = key;

                                    break;
                                }else {
                                    App.currentBusStations = -1;
                                }
                            }

                        }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (App.currentBusStations >=0) {
                                    tvSelect.setTextColor(Color.parseColor("#ffffff"));
                                    }else {
                                        tvSelect.setTextColor(Color.parseColor("#88ffffff"));
                                    }
                                }
                            });


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void http() {
        RequestParams params = new RequestParams(Constants.stations);
        params.setAsJsonContent(true);

        params.addBodyParameter("busRouterId", "");
        params.addBodyParameter("busRouterName", getIntent().getStringExtra("params"));
        params.addHeader("content-type", "application/json");

        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {
                Log.e(TAG, "onCancelled: " + arg0);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG, "onError: " + ex);

                Log.e(TAG, "onSuccess: error" );

                rlNotNull.setVisibility(View.GONE);
                rlNull.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String arg0) {

                Log.e(TAG, "onSuccess: " + arg0);
//                members = new Gson().fromJson(data,Members.class);

                    try {
                        JSONArray jsonArray = new JSONArray(arg0);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            int id = jsonObject.getInt("id");
                            String name = jsonObject.getString("name");
                            String lat = jsonObject.getString("lat");
                            String lng = jsonObject.getString("lng");
//                        Log.d("woider", "id is " + id);
                            Log.e("woider", "name is " + name);
                            Log.e("woider", "version is " + id);

                            BusBean busBean = new BusBean();
                            busBean.setId(id);
                            busBean.setName(name);
                            busBean.setLat(lat);
                            busBean.setLng(lng);

                            buslist.add(busBean);
                        }

//                    NewsAdapter newsAdapter = new NewsAdapter(App.context, (ArrayList) list);
//                    lvSaves.setAdapter(newsAdapter);

                        for (int i = 0; i < buslist.size(); i++) {
                            list.add(buslist.get(i).getName());
                        }

                        busAdapter = new BusAdapter(App.context, list);
//
                        lvBus.setAdapter(busAdapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //17.11.2 释放不当导致主界面崩溃的问题
        if (App.busAdapter != null) {
            App.busAdapter.map.clear();
            App.busAdapter = null;

//            App.busAdapter.map = null;
        }
        App.currentBusStations = -1;
        App.startActivity(this,HomeActivity.class);
        finish();
    }

    @OnClick({R.id.bus_close, R.id.tv_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bus_close:

                //17.11.2 释放不当导致主界面崩溃的问题
                if (App.busAdapter != null) {
                    App.busAdapter.map.clear();
                    App.busAdapter = null;

//                    App.busAdapter.map = null;
                }

                App.currentBusStations = -1;

//                App.startActivity(HomeActivity.class);
//                finish();

                finish();

                break;

            case R.id.tv_select:

                Log.e(TAG, "onViewClicked: "+ App.currentBusStations);
                if (App.currentBusStations >= 0) {

                    App.currentBusStationsName = list.get(App.currentBusStations);

                    App.startActivityParams(DialogBusconfirmActivity.class, list.get(App.currentBusStations));

                    finish();
                } else {

                }

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(BusAdapterBean event) {

//        tvSelect.setTextColor(Color.parseColor("#"));
    }
}


