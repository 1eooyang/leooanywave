package com.anywave.quanbo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anywave.quanbo.App;
import com.anywave.quanbo.R;
import com.anywave.quanbo.activity.DialogDeleteActivity;
import com.anywave.quanbo.activity.NewsDetailActivity;
import com.anywave.quanbo.bean.HK2List;
import com.anywave.quanbo.bean.NewsSaveBean;
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
 * Created by Administrator on 2017/9/28 0028.
 */
public class NewsAdapter extends MyBaseAdapter<NewsSaveBean> {
    private Context context;
    Map<Integer, Boolean> map;
    private static final String TAG = "LiveAdapter";

    public int delectPosition = -1;

    public NewsAdapter(Context c, ArrayList arrayList) {
        super(arrayList);
        context = c;
        map = new HashMap<>();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final NewsSaveBean bean = T.get(position);
        final ViewHolder viewHolder;

//        if (convertView == null) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.item_save, null);

        viewHolder = new ViewHolder(convertView);

        convertView.setTag(viewHolder);

       viewHolder.title.setText(bean.getName());
        viewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.startActivityParams(NewsDetailActivity.class ,bean.getUrl(),true,true);

            }
        });

        viewHolder.llItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                delectPosition = position;
                App.startActivityParams(DialogDeleteActivity.class,bean.getUrl(),bean.getName());
                return false;
            }
        });

        return convertView;
    }





    private void getDetail(int id, String date) {
        RequestParams params = new RequestParams(Constants.hk + "?id=" + id + "&d=" + date + "&pwd=testpwd");
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

                Log.e(TAG, "onSuccess: " + arg0);

                HK2List hkList = new Gson().fromJson(arg0, HK2List.class);
                List<HK2List.DataBeanX.DataBean> dataBeen = hkList.getData().getData();

            }
        });
    }



    static class ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
