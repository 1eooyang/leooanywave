package com.anywave.qpop.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.anywave.qpop.App;
import com.anywave.qpop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogBusActivity extends Activity {
    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    private static final String TAG = "DialogBusActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog_bus_test);
        ButterKnife.bind(this);


        etUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Log.e(TAG, i+"onTextChanged: " +i1 +":i2:"+i2);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick({R.id.ib_close, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_close:
                finish();
                break;
            case R.id.tv_confirm:


                Log.e(TAG, "=="+etUser.getText().toString().trim()+"==" );

                String content = etUser.getText().toString().trim();

                if (TextUtils.isEmpty(content)) {

                }else {
                    App.startActivityParams(BusActivity.class,etUser.getText().toString());
                    finish();
                }

                break;
        }
    }

}
