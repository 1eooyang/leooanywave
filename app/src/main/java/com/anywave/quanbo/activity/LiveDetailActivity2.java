package com.anywave.quanbo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.tedcoder.wkvideoplayer.view.SuperVideoPlayer;
import com.anywave.quanbo.App;
import com.anywave.quanbo.R;
import com.anywave.quanbo.adapter.TvDetialContentAdapter;
import com.anywave.quanbo.adapter.TvDetialContentAdapter2;
import com.anywave.quanbo.adapter.TvDetialDayAdapter;
import com.anywave.quanbo.bean.HkList;
import com.anywave.quanbo.bean.Playbean;
import com.anywave.quanbo.http.Constants;
import com.anywave.quanbo.utils.Util;
import com.google.gson.Gson;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LiveDetailActivity2 extends AppCompatActivity {

    HashMap<Integer,List<HkList.DataBeanX.DataBean>> mapDataBean = new HashMap();;

    @BindView(R.id.day_recyclerview)
    RecyclerView dayRecyclerview;
    //    @BindView(R.id.content_recyclerview)


    TvDetialDayAdapter dayAdapter;
     TvDetialContentAdapter2 contentAdapter;
    @BindView(R.id.live_back)
    ImageView liveBack;
    @BindView(R.id.play_btn)
    ImageView mPlayBtnView;
    @BindView(R.id.video_player_item_1)
    SuperVideoPlayer mSuperVideoPlayer;
    private static final String TAG = "LiveDetailActivity2";
    int currentid = -1;
    String url;
    static int currentweek;
    private String liveUrl;
    private Handler handler;

    //    @BindView(R.id.iv_null)
    ImageView ivNull;

    public static NiceVideoPlayer mNiceVideoPlayer;

    RecyclerView contentRecyclerview;

   /* static RecyclerView contentRecyclerview;
    //    @BindView(R.id.content_recyclerview1)
    static RecyclerView contentRecyclerview1;
    //    @BindView(R.id.content_recyclerview2)
    static RecyclerView contentRecyclerview2;
    //    @BindView(R.id.content_recyclerview3)
    static RecyclerView contentRecyclerview3;
    //    @BindView(R.id.content_recyclerview4)
    static RecyclerView contentRecyclerview4;
    //    @BindView(R.id.content_recyclerview5)
    static RecyclerView contentRecyclerview5;
    //    @BindView(R.id.content_recyclerview6)
    static RecyclerView contentRecyclerview6;*/


    public static HashMap<Integer, Boolean> mapivNull = new HashMap();


    private boolean isSettingAdapter;

    private void MoveToPosition(LinearLayoutManager manager, int n) {
        manager.scrollToPositionWithOffset(n, 0);
        manager.setStackFromEnd(true);
    }

    private void play(final String u,long position) {
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();

        TxVideoPlayerController controller = new TxVideoPlayerController(this,handler,u,position);
        if (App.not_native) {
            controller.showProgress(true);
            mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // TYPE_IJKor NiceVideoPlayer.TYPE_NATIVE
            mNiceVideoPlayer.setUp(u, null);
        } else {
            controller.showProgress(false);

            mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_NATIVE); // TYPE_IJKor NiceVideoPlayer.TYPE_NATIVE

            mNiceVideoPlayer.setUp(url, null);
        }
        // 将列表中的每个视频设置为默认16:9的比例
        ViewGroup.LayoutParams params = mNiceVideoPlayer.getLayoutParams();
        params.width = mNiceVideoPlayer.getResources().getDisplayMetrics().widthPixels; // 宽度为屏幕宽度
        params.height = (int) (params.width * 9f / 16f);    // 高度为宽度的9/16
        mNiceVideoPlayer.setLayoutParams(params);
        mNiceVideoPlayer.setVolume(5);
        mNiceVideoPlayer.setController(controller);
        if (position>0) {
            mNiceVideoPlayer.start(position);
        } else {
            mNiceVideoPlayer.start(0);

        }
        App.isClicked = false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_live_detail);
        mNiceVideoPlayer = (NiceVideoPlayer) findViewById(R.id.nice_video_player);
        ivNull = (ImageView) findViewById(R.id.iv_null);
        contentRecyclerview = (RecyclerView) findViewById(R.id.content_recyclerview);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        App.not_native = false;

        currentid = getIntent().getIntExtra("id", -1);
        url = getIntent().getStringExtra("url");
        liveUrl=url;

        SimpleDateFormat dfDate = new SimpleDateFormat("EE");
        String day = dfDate.format(new Date());


        dayRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        dayAdapter = new TvDetialDayAdapter(this, Util.getDayofweek());
        dayRecyclerview.setAdapter(dayAdapter);

        int week = Util.getDayofweek();//1-7 周日-周六
        currentweek = -1;//0-6
        switch (week) {
            case 1:
                currentweek = 6;
                break;
            default:
                currentweek = week - 2;
                break;
        }
        dayAdapter.setOnItemClickListener(new TvDetialDayAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                long time = System.currentTimeMillis() + (position - currentweek) * 24 * 60 * 60 * 1000;

                getHk(currentid, new SimpleDateFormat("yyyy-MM-dd").format(new Date(time)), position);
            }
        });

        contentRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        contentAdapter = new TvDetialContentAdapter2();
        contentRecyclerview.setAdapter(contentAdapter);

        TvDetialContentAdapter.clickBack = false;
        TvDetialContentAdapter.SelectNull();
        handler = new MyHandler(this);
        play(url,0l);

        getDay(currentweek);

        getHk(currentid, Util.getDate(), currentweek);

    }

    private void getDay(int position) {

        switch (position) {

//            case 0:
//                contentRecyclerview.setVisibility(View.VISIBLE);
//
//                break;
//            case 1:
//                contentRecyclerview1.setVisibility(View.VISIBLE);
//                break;
//            case 2:
//                contentRecyclerview2.setVisibility(View.VISIBLE);
//                break;
//            case 3:
//                contentRecyclerview3.setVisibility(View.VISIBLE);
//                break;
//            case 4:
//                contentRecyclerview4.setVisibility(View.VISIBLE);
//                break;
//            case 5:
//                contentRecyclerview5.setVisibility(View.VISIBLE);
//                break;
//            case 6:
//                contentRecyclerview6.setVisibility(View.VISIBLE);
//                break;
            default:

                break;
        }

//        if (mapivNull.get(position) != null && currentweek == position) {
//            if (mapivNull.get(position)) {
//                ivNull.setVisibility(View.GONE);
//            } else {
//                ivNull.setVisibility(View.VISIBLE);
//            }
//        }
    }

    public static void getHk2(final int id, final String date, final int day) {
        RequestParams params = new RequestParams(Constants.hk + "?id=" + id + "&d=" + date + "&pwd=testpwd");
        params.setAsJsonContent(true);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                HkList hkList = new Gson().fromJson(result, HkList.class);
                final List<HkList.DataBeanX.DataBean> dataBeen = hkList.getData().getData();

//                if (dataBeen.size() <= 0) {
//                    mapivNull.put(day, false);
////                    if (day == currentweek)
//                    ivNull.setVisibility(View.VISIBLE);
//                } else {
//                    mapivNull.put(day, true);
////                    if (day == currentweek)
//                    ivNull.setVisibility(View.GONE);
//                }

                final int[] p = {0};

                Log.e(TAG, "run: " + dataBeen.size());
                for (int i = 0; i < dataBeen.size(); i++) {
                    if (dataBeen.get(i).getIsPlaying() == 0) {
                        if (i >= 2)
                            i = i - 2;
                        p[0] = i;
                        break;
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    public  void getHk(final int id, final String date, final int day) {

        if (mapDataBean.get(day)!=null) {
            Log.e(TAG, "getHk:"+mapDataBean.get(day) );
            Log.e(TAG, "getHk:size"+mapDataBean.get(day).size() );
            List<HkList.DataBeanX.DataBean> been = mapDataBean.get(day);
            if (been.size() <= 0) {
                ivNull.setVisibility(View.VISIBLE);
                contentRecyclerview.setVisibility(View.GONE);
            } else {
                ivNull.setVisibility(View.GONE);
                contentRecyclerview.setVisibility(View.VISIBLE);

             int[] p = {0};
            for (int i = 0; i < been.size(); i++) {
                if (been.get(i).getIsPlaying() == 0) {
                    if (i >= 2)
                        i = i - 2;
                    p[0] = i;
                    break;
                }
            }
//            if (p[0]-6>=0)
//                p[0] = p[0] - 6;
            contentAdapter.setDate(App.context, been, day);
            contentAdapter.notifyDataSetChanged();

//            contentRecyclerview.scrollToPosition(p[0]);

            ((LinearLayoutManager)contentRecyclerview.getLayoutManager())
                    .scrollToPositionWithOffset(p[0], 0);
                Log.e(TAG, "getHk:p[0]"+p[0] );

            }
        }else {
//        String url = "http://220.248.15.134:8887/hk/hklist?id=105&d=2017-10-12&pwd=testpwd";

        RequestParams params = new RequestParams(Constants.hk + "?id=" + id + "&d=" + date + "&pwd=testpwd");
//        RequestParams params = new RequestParams(url);
        params.setAsJsonContent(true);
//        params.addHeader("content-type", "application/json");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

//                LinearLayoutManager lm = new LinearLayoutManager(App.context);
//                        final LinearLayoutManager lm = new LinearLayoutManager(LiveDetailActivity2.this);

                HkList hkList = new Gson().fromJson(result, HkList.class);
                final List<HkList.DataBeanX.DataBean> dataBeen = hkList.getData().getData();


                mapDataBean.put(day, dataBeen);

                Log.e(TAG, dataBeen.size() + "onSuccess: " + result);


                if (dataBeen.size() <= 0) {
                    mapivNull.put(day, false);
//                    if (day == currentweek)
                    ivNull.setVisibility(View.VISIBLE);
                } else {
                    mapivNull.put(day, true);
//                    if (day == currentweek)
                    ivNull.setVisibility(View.GONE);
                }


                final int[] p = {0};


                Log.e(TAG, "run: " + dataBeen.size());
                for (int i = 0; i < dataBeen.size(); i++) {
//                            Log.e(TAG, "onSuccess: "+ dataBeen.get(i).getIsPlaying());
                    if (dataBeen.get(i).getIsPlaying() == 0) {
//                                Log.e(TAG, "onSuccess: "+ dataBeen.get(i).getIsPlaying() );
                        if (i >= 2)
                            i = i - 2;
                        p[0] = i;
//                                if (dataBeen.size() >= 8) {
//                                    MoveToPosition(lm, p[0]);
//                                }
                        break;
                    }
                }


//                    contentAdapter = new TvDetialContentAdapter(App.context, dataBeen, day, null);


                contentAdapter.setDate(App.context, dataBeen, day);
                contentAdapter.notifyDataSetChanged();

//                contentRecyclerview.setAdapter(new TvDetialContentAdapter(App.context, dataBeen, day, null));
//                contentRecyclerview.scrollToPosition(p[0]);
                ((LinearLayoutManager)contentRecyclerview.getLayoutManager())
                        .scrollToPositionWithOffset(p[0], 0);

                for (int i = 0; i < 7; i++) {
                    if (i == day) {
                        switch (i) {

//                            case 0:
//
//                                contentRecyclerview.setAdapter(new TvDetialContentAdapter(App.context, dataBeen, day, null));
//                                contentRecyclerview.scrollToPosition( p[0]);
//                                break;
//                            case 1:
//                                contentRecyclerview1.setAdapter(new TvDetialContentAdapter(App.context, dataBeen, day, null));
//                                contentRecyclerview1.scrollToPosition( p[0]);
//                                break;
//                            case 2:
//                                contentRecyclerview2.setAdapter(new TvDetialContentAdapter(App.context, dataBeen, day, null));
//                                contentRecyclerview2.scrollToPosition( p[0]);
//                                break;
//                            case 3:
//                                contentRecyclerview3.setAdapter(new TvDetialContentAdapter(App.context, dataBeen, day, null));
//                                contentRecyclerview3.scrollToPosition( p[0]);
//                                break;
//                            case 4:
//                                contentRecyclerview4.setAdapter(new TvDetialContentAdapter(App.context, dataBeen, day, null));
//                                contentRecyclerview4.scrollToPosition( p[0]);
//                                break;
//                            case 5:
//                                contentRecyclerview5.setAdapter(new TvDetialContentAdapter(App.context, dataBeen, day, null));
//                                contentRecyclerview5.scrollToPosition( p[0]);
//                                break;
//                            case 6:
//                                contentRecyclerview6.setAdapter(new TvDetialContentAdapter(App.context, dataBeen, day, null));
//                                contentRecyclerview6.scrollToPosition( p[0]);
//                                break;
                            default:

                                break;
                        }
                    }

                }

//                                        contentRecyclerview.scrollToPosition( p[0]);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (contentAdapter != null)
            contentAdapter.notifyDataSetChanged();
//        EventBus.getDefault().postSticky(1);
//        play(url);


        //hxp 11.6
        if (mNiceVideoPlayer!=null)
            mNiceVideoPlayer.start();

//        if (!getConnectWifiSsid().contains("Qpop")){
//            App.startActivity(WifiActivity.class);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        super.onStop();
        // 在onStop时释放掉播放器
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.isCurrentApp = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        App.isCurrentApp = false;
    }

    @Override
    public void onBackPressed() {

        // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
        // 所以在Activity中onBackPress要交给NiceVideoPlayer先处理。
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        App.startActivity2(HomeActivity.class);
        // 在onStop时释放掉播放器
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        super.onBackPressed();
    }


    @OnClick(R.id.live_back)
    public void onViewClicked() {
        // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
        // 所以在Activity中onBackPress要交给NiceVideoPlayer先处理。
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        App.startActivity2(HomeActivity.class);
        // 在onStop时释放掉播放器
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        finish();
    }

    @OnClick(R.id.play_btn)
    public void onViewPlayClicked() {
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(final Playbean event) {
        App.currentbacktime = 0;
        if (event.getUrl()==null||event.getUrl().trim().equals("")) {
            play(this.liveUrl,0);
        } else {
            play(event.getUrl(),event.getPosition());
        }
    }

    /**
     *
     */
    private static class MyHandler extends Handler{
        //对Activity的弱引用
        private final WeakReference<LiveDetailActivity2> mActivity;

        public MyHandler(LiveDetailActivity2 activity){
            mActivity = new WeakReference<LiveDetailActivity2>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            LiveDetailActivity2 activity = mActivity.get();
            if(activity==null){
                super.handleMessage(msg);
                return;
            }
            switch (msg.what) {
                case 1:
                    Log.d("wliu","************************************");
                    Log.d("wliu","replay");
                    Log.d("wliu","************************************");

                    String url =  msg.getData().getString("url");
                    long position =  msg.getData().getLong("position");
                    Playbean event = new Playbean(url,1,position);
                    EventBus.getDefault().post(event);
                    break;
                case 2:
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }
}

