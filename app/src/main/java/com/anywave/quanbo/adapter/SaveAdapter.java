package com.anywave.quanbo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anywave.quanbo.R;
import com.anywave.quanbo.bean.HK2List;
import com.anywave.quanbo.bean.SaveList;
import com.anywave.quanbo.http.Constants;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hxp on 2017/10/26 0028.
 */
public class SaveAdapter extends MyBaseAdapter<SaveList> {
    private Context context;
    Map<Integer, Boolean> map;
    private static final String TAG = "SaveAdapter";

    public SaveAdapter(Context c, ArrayList arrayList) {
        super(arrayList);
        context = c;
        map = new HashMap<>();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final SaveList bean = T.get(position);
        final ViewHolder viewHolder;

//        if (convertView == null) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.item_live, null);

        viewHolder = new ViewHolder(convertView);

        convertView.setTag(viewHolder);



        return convertView;
    }

    private void getDetail(int id,String date) {
        RequestParams params = new RequestParams(Constants.hk+"?id="+id+"&d="+date+"&pwd=testpwd");
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

                Log.e(TAG, "onSuccess: "+arg0 );

                HK2List hkList = new Gson().fromJson(arg0,HK2List.class);
                List<HK2List.DataBeanX.DataBean> dataBeen = hkList.getData().getData();



            }

        });
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
