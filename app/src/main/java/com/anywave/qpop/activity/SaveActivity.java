package com.anywave.qpop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.anywave.qpop.App;
import com.anywave.qpop.R;
import com.anywave.qpop.adapter.LiveAdapter;
import com.anywave.qpop.adapter.NewsAdapter;
import com.anywave.qpop.adapter.SaveAdapter;
import com.anywave.qpop.bean.NewsSaveBean;
import com.anywave.qpop.bean.SaveBean;
import com.anywave.qpop.bean.SaveList;
import com.anywave.qpop.http.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SaveActivity extends Activity {

//    @BindView(R.id.back)
//    ImageButton back;

    private static final String TAG = "SaveActivity";
    @BindView(R.id.lv_saves)
    ListView lvSaves;
    @BindView(R.id.tv_save_center)
    TextView tvSaveCenter;
    @BindView(R.id.tv_save_end)
    TextView tvSaveEnd;

    NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        ButterKnife.bind(this);


       /* lvSaves.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                App.startActivity(DialogDeleteActivity.class);
                return false;
            }
        });*/

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

//        getSaveList();
    }

    @Override
    protected void onStart() {
        super.onStart();

        getSaveList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (newsAdapter != null) {
            if (DialogDeleteActivity.confirmDelect) {
                DialogDeleteActivity.confirmDelect = false;
                newsAdapter.T.remove(newsAdapter.delectPosition);
            }

            newsAdapter.notifyDataSetChanged();
        }else {

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void getSaveList() {
        RequestParams params = new RequestParams(Constants.favorites);
        params.setAsJsonContent(true);

        params.addHeader("content-type", "application/json");
        params.addHeader("x-auth-token", App.token);
        Log.e(TAG, "getSaveList: " + App.token);

        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onFinished() {
                Log.e(TAG, "onFinished: ");
            }

            @Override
            public void onSuccess(final String arg0) {

                ArrayList<NewsSaveBean>  list = new ArrayList<NewsSaveBean>();


                Log.e(TAG, "onSuccess: " + arg0);
//                String s = "[{\"type\":\"NEWSLETTER\",\"referenceId\":null,\"name\":\"测试新闻标题1\",\"thumbnail\":null,\"url\":\"http://m.baidu.com\"}]";

                try {

                    JSONArray jsonArray = new JSONArray(arg0);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

//                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String url = jsonObject.getString("url");
//                        Log.d("woider", "id is " + id);
                        Log.e("woider", "name is " + name);
                        Log.e("woider", "version is " + url);


                        NewsSaveBean save = new NewsSaveBean();
                        save.setUrl(url);
                        save.setName(name);
                        list.add(save);
                    }

                    newsAdapter = new NewsAdapter(App.context, (ArrayList) list);

                    lvSaves.setAdapter(newsAdapter);

                    if (jsonArray.length() <= 0) {
//                    if (true) {
                        tvSaveCenter.setVisibility(View.VISIBLE);
                        tvSaveEnd.setVisibility(View.INVISIBLE);
                        lvSaves.setVisibility(View.GONE);

                    } else {
                        tvSaveCenter.setVisibility(View.GONE);
                        tvSaveEnd.setVisibility(View.VISIBLE);
                        lvSaves.setVisibility(View.VISIBLE);

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }



//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        NewsSaveBean saveBean = new Gson().fromJson(arg0, NewsSaveBean.class);
//                    }
//                });

            }
        });
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
