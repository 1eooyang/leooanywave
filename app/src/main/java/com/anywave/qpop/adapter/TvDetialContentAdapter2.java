package com.anywave.qpop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.anywave.qpop.App;
import com.anywave.qpop.R;
import com.anywave.qpop.activity.LiveDetailActivity2;
import com.anywave.qpop.bean.HkList;
import com.anywave.qpop.bean.Playbean;
import com.anywave.qpop.bean.TvDetialAdapterBean;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvDetialContentAdapter2 extends RecyclerView.Adapter<TvDetialContentAdapter2.DetialContentViewHolder> {
    Context context = App.context;
    List<HkList.DataBeanX.DataBean> dataBeen;
    DetialContentViewHolder currentHolder;
    public static boolean clickBack;
    Map<Integer,Boolean> map;
    LinearLayoutManager layoutManager;

    int currentDay;

    Map<Integer,Boolean> backLockMap = new HashMap<>();

//    Map<Integer,DetialContentViewHolder> mapHolder;
    private static final String TAG = "TvDetialContentAdapter2";

    public void setDate(Context context, final List<HkList.DataBeanX.DataBean> dataBeen, final int currentDay){
        this.context = context;
        this.dataBeen = dataBeen;
        this.currentDay = currentDay;

//        backLockMap = new HashMap<>();

                if (App.currentDetailMapArray[currentDay]!=null){
                    map= App.currentDetailMapArray[currentDay];
                }else {
                    map=new HashMap<>();
                    for (int i = 0; i < dataBeen.size(); i++) {
                        map.put(i,false);
                    }
                }

    }
    public TvDetialContentAdapter2(){

    }

    public TvDetialContentAdapter2(Context context, final List<HkList.DataBeanX.DataBean> dataBeen, final int currentDay, LinearLayoutManager layoutManager) {

        this.context = context;
        this.dataBeen = dataBeen;
        this.currentDay = currentDay;
        this.layoutManager = layoutManager;

        backLockMap = new HashMap<>();

        new Thread(){
            @Override
            public void run() {
                super.run();

                if (App.currentDetailMapArray[currentDay]!=null){
                    map= App.currentDetailMapArray[currentDay];
                }else {
                    map=new HashMap<>();
                    for (int i = 0; i < dataBeen.size(); i++) {
                        map.put(i,false);
                    }
                }
            }
        }.start();

        for (int i = 0; i <dataBeen.size() ; i++) {
        }
    }
    public  void clickSelect(){

        for (int i = 0; i < App.currentDetailMapArray.length; i++) {
            if (i!= currentDay) {
//                    App.currentDetailMapArray[i].clear();
                App.currentDetailMapArray[i] = null;
            }

        }

    }
    public static void SelectNull(){

        for (int i = 0; i < App.currentDetailMapArray.length; i++) {
//                    App.currentDetailMapArray[i].clear();

                App.currentDetailMapArray[i] = null;

        }

    }



    @Override
    public DetialContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DetialContentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_tvcontent, parent, false));
    }

    @Override
    public void onBindViewHolder(DetialContentViewHolder holder, int position) {
        holder.bindData(holder, position);
    }

    @Override
    public int getItemCount() {
        if (dataBeen==null){
            return 0;
        }
        return dataBeen.size();
//        return 100;
    }

    class DetialContentViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ViewHolder viewHolder;

        public DetialContentViewHolder(View itemView) {
            super(itemView);
            viewHolder = new ViewHolder(itemView,getLayoutPosition());
        }

        void bindData(final DetialContentViewHolder been, final int position) {

            viewHolder.current = position;

            final HkList.DataBeanX.DataBean dataBean = dataBeen.get(position);
            switch (dataBean.getIsPlaying()) {
                case -1:

                    getLiveStates(0);

                    break;
                case 0:
                        getLiveStates(1);

                    if (!clickBack&& App.currentDetailMapArray[currentDay]==null) {
                        map.put(position,true);
                    }

                    break;
                case 1:
                    getLiveStates(4);
                    break;
                default:

                    break;
            }


            viewHolder.tvTime.setText(dataBean.getStartTimeStr());
            viewHolder.tvTitle.setText(dataBean.getActname());

            viewHolder.tvBackLock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (App.isClicked) {
                    } else {
                        if (LiveDetailActivity2.mNiceVideoPlayer!=null
                                && LiveDetailActivity2.mNiceVideoPlayer.mController!=null) {
                            NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                            ((TxVideoPlayerController) LiveDetailActivity2.mNiceVideoPlayer.mController).loading();
                        }
                        App.isClicked = true;
                        App.not_native = true;
                        clickBack = true;
                        for (int i = 0; i < dataBeen.size(); i++) {
                            map.put(i, false);
                            if (i == position) {
                                map.put(position, true);
                            }
                        }
                        clickSelect();
                        EventBus.getDefault().post(new TvDetialAdapterBean(viewHolder.cb, dataBeen.get(position).getVideoUrl()));
                        App.isClicked = false;
//                    int id = dataBean.getId();
//                    RequestParams params2 = new RequestParams(Constants.checkurl + "?id=" + id + "&pwd=testpwd");

//                    params2.setAsJsonContent(true);
//                    params2.addHeader("content-type", "application/json");
//                    x.http().get(params2, new Callback.CommonCallback<String>() {
//                    @Override
//                    public void onCancelled(CancelledException arg0) {
//                    }
//
//                    @Override
//                    public void onError(Throwable ex, boolean isOnCallback) {
//                    }
//
//                    @Override
//                    public void onFinished() {
//
//                    }

//                    @Override
//                    public void onSuccess(String arg0) {
//                        String[] array1 = arg0.split("\"videoUrl\":\"");
//                        String[] array2 = array1[1].split("\",\"channel\":");
//                        Log.e("wliu112", "onSuccess: url:" + array2[0]);
//                        EventBus.getDefault().post(new TvDetialAdapterBean(viewHolder.cb, array2[0]));
//                        App.isClicked = false;
//                    }
//                    });
                }
                }
            });

            viewHolder.tvSoon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    App.currentDetailMapArray[currentDay] = map;
                }
            });

            viewHolder.tvZhibo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (App.isClicked) {
                    } else {
                        if (LiveDetailActivity2.mNiceVideoPlayer!=null
                                && LiveDetailActivity2.mNiceVideoPlayer.mController!=null) {
                            ((TxVideoPlayerController) LiveDetailActivity2.mNiceVideoPlayer.mController).loading();
                            NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                        }
                        App.isClicked = true;
                        App.not_native = false;
                        EventBus.getDefault().post(new TvDetialAdapterBean(viewHolder.cb, dataBean.getCheckUrl()));
                        for (int i = 0; i < dataBeen.size(); i++) {
                            map.put(i, false);
                            if (i == position) {
                                map.put(position, true);
                                Log.e(TAG, "onClick:map:" + position + " v " + map.get(position));
                            }
                        }
                        clickSelect();
                }
                }
            });


            new Thread(){}.start();
            if (map.get(position)!=null) {
                viewHolder.cb.setChecked(map.get(position));
                showSelect(map.get(position),position);

                Log.e(TAG, map.get(position)+"bindData: ==================== "+position );
            }
        }

        private void showSelect(boolean b,int p) {
                if ( b) {

                    if (dataBeen.get(p).getIsPlaying()==0){
                        viewHolder.tvTime.setTextColor(Color.parseColor("#52CC9A"));
                        viewHolder.tvTitle.setTextColor(Color.parseColor("#52CC9A"));
                        viewHolder.tvBackLock.setVisibility(View.GONE);
                        viewHolder.tvLive.setVisibility(View.VISIBLE);
                        viewHolder.tvLiveing.setVisibility(View.GONE);
                        viewHolder.tvBackLockDis.setVisibility(View.GONE);
                        viewHolder.tvSoon.setVisibility(View.GONE);
                        viewHolder.tvZhibo.setVisibility(View.GONE);
                    }else {
                        viewHolder.tvTime.setTextColor(Color.parseColor("#52CC9A"));
                        viewHolder.tvTitle.setTextColor(Color.parseColor("#52CC9A"));
                        viewHolder.tvBackLock.setVisibility(View.GONE);
                        viewHolder.tvLive.setVisibility(View.GONE);
                        viewHolder.tvLiveing.setVisibility(View.VISIBLE);
                        viewHolder.tvBackLockDis.setVisibility(View.GONE);
                        viewHolder.tvSoon.setVisibility(View.GONE);
                        viewHolder.tvZhibo.setVisibility(View.GONE);
                    }
            }else {

                    if (dataBeen.get(p).getIsPlaying()==-1){
                        viewHolder.tvTime.setTextColor(Color.parseColor("#000000"));
                        viewHolder.tvTitle.setTextColor(Color.parseColor("#000000"));
                        viewHolder.tvBackLock.setVisibility(View.VISIBLE);
                        viewHolder.tvLive.setVisibility(View.GONE);
                        viewHolder.tvLiveing.setVisibility(View.GONE);
                        viewHolder.tvBackLockDis.setVisibility(View.GONE);
                        viewHolder.tvSoon.setVisibility(View.GONE);
                        viewHolder.tvZhibo.setVisibility(View.GONE);
                    }else  if (dataBeen.get(p).getIsPlaying()==0){
                        viewHolder.tvTime.setTextColor(Color.parseColor("#000000"));
                        viewHolder.tvTitle.setTextColor(Color.parseColor("#000000"));
                        viewHolder.tvBackLock.setVisibility(View.GONE);
                        viewHolder.tvLive.setVisibility(View.GONE);
                        viewHolder.tvLiveing.setVisibility(View.GONE);
                        viewHolder.tvBackLockDis.setVisibility(View.GONE);
                        viewHolder.tvSoon.setVisibility(View.GONE);
                        viewHolder.tvZhibo.setVisibility(View.VISIBLE);
                    }else {
                        viewHolder.tvTime.setTextColor(Color.parseColor("#000000"));
                        viewHolder.tvTitle.setTextColor(Color.parseColor("#000000"));
                        viewHolder.tvBackLock.setVisibility(View.GONE);
                        viewHolder.tvLive.setVisibility(View.GONE);
                        viewHolder.tvLiveing.setVisibility(View.GONE);
                        viewHolder.tvBackLockDis.setVisibility(View.GONE);
                        viewHolder.tvSoon.setVisibility(View.VISIBLE);
                        viewHolder.tvZhibo.setVisibility(View.GONE);
                    }

            }
        }

        public void getCurrentStates() {
//            viewHolder.tvTime.setTextColor(Color.parseColor("##52CC9A"));
//            viewHolder.tvTitle.setTextColor(Color.parseColor("##52CC9A"));
            currentHolder.viewHolder.tvBackLock.setVisibility(View.GONE);
            currentHolder.viewHolder.tvLive.setVisibility(View.GONE);
            currentHolder.viewHolder.tvLiveing.setVisibility(View.GONE);
            currentHolder.viewHolder.tvBackLockDis.setVisibility(View.GONE);
            currentHolder.viewHolder.tvSoon.setVisibility(View.GONE);
            currentHolder.viewHolder.tvZhibo.setVisibility(View.GONE);
            currentHolder.viewHolder.tvTitle.setTextColor(Color.parseColor("#000000"));
            currentHolder.viewHolder.tvTime.setTextColor(Color.parseColor("#000000"));

            currentHolder.viewHolder.tvZhibo.setVisibility(View.VISIBLE);
        }

        public void getLiveStates(int state) {

            //播放状态：0回看、1直播中、2播放中、3回看（不可点击状）、4即将 、5直播、


            viewHolder.tvBackLock.setVisibility(View.GONE);
            viewHolder.tvLive.setVisibility(View.GONE);
            viewHolder.tvLiveing.setVisibility(View.GONE);
            viewHolder.tvBackLockDis.setVisibility(View.GONE);
            viewHolder.tvSoon.setVisibility(View.GONE);
            viewHolder.tvZhibo.setVisibility(View.GONE);
            switch (state) {
                case 0:
                    viewHolder.tvTime.setTextColor(Color.parseColor("#000000"));
                    viewHolder.tvTitle.setTextColor(Color.parseColor("#000000"));
                    viewHolder.tvBackLock.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    viewHolder.tvLive.setVisibility(View.VISIBLE);
                    viewHolder.tvTime.setTextColor(Color.parseColor("#52CC9A"));
                    viewHolder.tvTitle.setTextColor(Color.parseColor("#52CC9A"));


                    break;
                case 2:
                    viewHolder.tvTime.setTextColor(Color.parseColor("##52CC9A"));
                    viewHolder.tvTitle.setTextColor(Color.parseColor("##52CC9A"));
                    viewHolder.tvLiveing.setVisibility(View.VISIBLE);

                    break;
                case 3:
                    viewHolder.tvTime.setTextColor(Color.parseColor("#000000"));
                    viewHolder.tvTitle.setTextColor(Color.parseColor("#000000"));
                    viewHolder.tvBackLockDis.setVisibility(View.VISIBLE);

                    break;
                case 4:
                    viewHolder.tvTime.setTextColor(Color.parseColor("#000000"));
                    viewHolder.tvTitle.setTextColor(Color.parseColor("#000000"));
                    viewHolder.tvSoon.setVisibility(View.VISIBLE);
                    break;

                case 5:
                    viewHolder.tvTime.setTextColor(Color.parseColor("#000000"));
                    viewHolder.tvTitle.setTextColor(Color.parseColor("#000000"));
                    viewHolder.tvZhibo.setVisibility(View.VISIBLE);

                    break;
                default:
                    break;
            }
        }

         class ViewHolder {
             int current;
            @BindView(R.id.tv_time)
            TextView tvTime;
            @BindView(R.id.tv_title)
            TextView tvTitle;
            @BindView(R.id.tv_back_lock)
            TextView tvBackLock;
            @BindView(R.id.tv_live)
            TextView tvLive;
            @BindView(R.id.tv_liveing)
            TextView tvLiveing;
            @BindView(R.id.tv_back_lock_dis)
            TextView tvBackLockDis;
            @BindView(R.id.tv_soon)
            TextView tvSoon;
            @BindView(R.id.tv_zhibo)
            TextView tvZhibo;
             @BindView(R.id.cb)
             CheckBox cb;
             int position;
            ViewHolder(View view, int mPosition) {
                ButterKnife.bind(this, view);
                EventBus.getDefault().register(this);
                position = mPosition;
            }
             @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
             public void Event(final TvDetialAdapterBean event) {
                 boolean isCb;
                 if (event.getCheckBox().equals(cb)) {
                     cb.setChecked(true);
                     isCb = true;
                     EventBus.getDefault().post(new Playbean(event.getUrl(),-1,0));
                 } else {
                     cb.setChecked(false);
                     isCb = false;
                 }

                 HkList.DataBeanX.DataBean data = dataBeen.get(current);
                 if (data.getIsPlaying() == -1){
                     if ( isCb) {
                         tvTime.setTextColor(Color.parseColor("#52CC9A"));
                         tvTitle.setTextColor(Color.parseColor("#52CC9A"));
                         tvBackLock.setVisibility(View.GONE);
                         tvLive.setVisibility(View.GONE);
                         tvLiveing.setVisibility(View.VISIBLE);
                         tvBackLockDis.setVisibility(View.GONE);
                         tvSoon.setVisibility(View.GONE);
                         tvZhibo.setVisibility(View.GONE);
                     }else {
                         tvTime.setTextColor(Color.parseColor("#000000"));
                         tvTitle.setTextColor(Color.parseColor("#000000"));
                         tvBackLock.setVisibility(View.VISIBLE);
                         tvLive.setVisibility(View.GONE);
                         tvLiveing.setVisibility(View.GONE);
                         tvBackLockDis.setVisibility(View.GONE);
                         tvSoon.setVisibility(View.GONE);
                         tvZhibo.setVisibility(View.GONE);
                     }
                 }else if (data.getIsPlaying() == 0){
                     if ( isCb) {
                         tvTime.setTextColor(Color.parseColor("#52CC9A"));
                         tvTitle.setTextColor(Color.parseColor("#52CC9A"));
                         tvBackLock.setVisibility(View.GONE);
                         tvLive.setVisibility(View.VISIBLE);
                         tvLiveing.setVisibility(View.GONE);
                         tvBackLockDis.setVisibility(View.GONE);
                         tvSoon.setVisibility(View.GONE);
                         tvZhibo.setVisibility(View.GONE);
                     }else {
                         tvTime.setTextColor(Color.parseColor("#000000"));
                         tvTitle.setTextColor(Color.parseColor("#000000"));
                         tvBackLock.setVisibility(View.GONE);
                         tvLive.setVisibility(View.GONE);
                         tvLiveing.setVisibility(View.GONE);
                         tvBackLockDis.setVisibility(View.GONE);
                         tvSoon.setVisibility(View.GONE);
                         tvZhibo.setVisibility(View.VISIBLE);
                     }
                 }
             }
        }

    }
}
