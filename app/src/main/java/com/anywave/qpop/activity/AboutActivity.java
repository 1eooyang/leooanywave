package com.anywave.qpop.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anywave.qpop.App;
import com.anywave.qpop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends Activity {

//    @BindView(R.id.back)
//    ImageButton back;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.ll_clear)
    LinearLayout llClear;
    @BindView(R.id.pw)
    TextView pw;
    @BindView(R.id.ll_addpw)
    LinearLayout llAddpw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.ll_addpw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
//                startActivity(new Intent(AboutActivity.this, PersionActivity.class));
                finish();
                break;
            case R.id.ll_addpw:
                App.startActivity(this,FuWuTiaoKuanActivity.class);



                break;
        }
    }

}
