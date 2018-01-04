package com.anywave.quanbo.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anywave.quanbo.R;
import com.anywave.quanbo.bean.HK2List;
import com.anywave.quanbo.bean.PlayList;
import com.anywave.quanbo.http.Constants;
import com.anywave.quanbo.utils.Util;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/28 0028.
 */
public class LiveAdapter extends MyBaseAdapter<PlayList.DataBean.ListBean> {
    private Context context;
    Map<Integer, Boolean> map;
    private static final String TAG = "LiveAdapter";

    public LiveAdapter(Context c, ArrayList arrayList) {
        super(arrayList);
        context = c;
        map = new HashMap<>();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final PlayList.DataBean.ListBean bean = T.get(position);
        final ViewHolder viewHolder;
//        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_live, null);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
        viewHolder.cbPull.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    RequestParams params = new RequestParams(Constants.hk+"?id="+bean.getId()+"&d="+ Util.getDate()+"&pwd=testpwd");
                    params.setAsJsonContent(true);
                    params.addHeader("content-type", "application/json");
                    x.http().get(params, new Callback.CommonCallback<String>() {

                        @Override
                        public void onCancelled(CancelledException arg0) {
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                        }

                        @Override
                        public void onFinished() {
                        }

                        @Override
                        public void onSuccess(String arg0) {

                            HK2List hkList = new Gson().fromJson(arg0,HK2List.class);
                            List<HK2List.DataBeanX.DataBean> dataBeen = hkList.getData().getData();
                            int currentnum = 0;
                            for (int i = 0; i < dataBeen.size(); i++) {
                                if ( dataBeen.get(i).getIsPlaying() == 0) {
                                    currentnum = i;
                                }
                            }
                            viewHolder.llShow.removeAllViews();
                            viewHolder.llShow.setOrientation(LinearLayout.VERTICAL);
                            for (int i = 0; i < dataBeen.size() - currentnum - 2; i++) {
                                TextView view = (TextView) View.inflate(context, R.layout.item_datail, null);
                                view.setText(dataBeen.get(i+currentnum+2).getStartTimeStr()+"  "+dataBeen.get(i+currentnum+2).getActname());
                                //第一个参数代表横向填充父窗体题，第二个参数纵向为0dp,第三个参数为权重
                                view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1));
                                viewHolder.llShow.addView(view);
                            }
                            viewHolder.llShow.setVisibility(View.VISIBLE);
                        }
                    });
                }else {
                    viewHolder.llShow.setVisibility(View.GONE);
                }
            }
        });

        viewHolder.rlPull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( viewHolder.cbPull.isChecked()) {
                    viewHolder.cbPull.setChecked(false);
                }else {
                    viewHolder.cbPull.setChecked(true);
                }
            }
        });
        ImageOptions options = new ImageOptions(){};

        viewHolder.tvTitle.setText(bean.getTitle());
//        x.image().bind(viewHolder.ivTitle, Constants.LIVE+bean.getImgUrl());
        x.image().loadDrawable(Constants.LIVE + bean.getImgUrl(), options, new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {
                viewHolder.ivTitle.setImageDrawable(result);
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
        viewHolder.tvCurrentLive.setText("当前节目: "+bean.getCurrent());
        viewHolder.tvStart.setText(bean.getCurrentStart());
        viewHolder.tvEnd.setText(bean.getCurrentEnd());

        String next = bean.getNext();
        String[] arr = next.split("\\ ");
        viewHolder.tvNext.setText(bean.getCurrentEnd()+"  "+arr[0]);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_title)
        ImageView ivTitle;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_next)
        TextView tvNext;
        @BindView(R.id.tv_start)
        TextView tvStart;
        @BindView(R.id.tv_end)
        TextView tvEnd;
        @BindView(R.id.tv_current_live)
        TextView tvCurrentLive;
        @BindView(R.id.ll_show)
        LinearLayout llShow;
        @BindView(R.id.cb_pull)
        CheckBox cbPull;
        @BindView(R.id.rl_pull)
        RelativeLayout rlPull;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
