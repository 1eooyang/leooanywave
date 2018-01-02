package com.anywave.qpop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.anywave.qpop.App;
import com.anywave.qpop.R;
import com.anywave.qpop.activity.BusActivity;
import com.anywave.qpop.bean.BusAdapterBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/28 0028.
 */
public class BusAdapter extends MyBaseAdapter<ArrayList> {
    private Context context;
    public Map<Integer,Boolean> map;
    private String TAG = "BusAdapter";


    public BusAdapter(Context c, ArrayList arrayList) {
        super(arrayList);
        context = c;
        map=new HashMap<>();
        App.busAdapter = this;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final BusAdapter.ViewHolder viewHolder;
//        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_bus, null);

            viewHolder = new BusAdapter.ViewHolder();
            viewHolder.tv_bus_zhan = (TextView) convertView.findViewById(R.id.tv_bus_zhan);
            viewHolder.iv_bus_down = (ImageView) convertView.findViewById(R.id.iv_bus_down);
            viewHolder.ib_bus_select = (ImageButton) convertView.findViewById(R.id.ib_bus_select);
            viewHolder.cb_bus_select = (CheckBox) convertView.findViewById(R.id.cb_bus_select);

            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
        viewHolder.tv_bus_zhan.setText((CharSequence) T.get(position));
        viewHolder.tv_bus_zhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (viewHolder.cb_bus_select.isChecked()) {
                    viewHolder.cb_bus_select.setChecked(false);


                }else {

                    viewHolder.cb_bus_select.setChecked(true);
                }



                CheckBox checkBox =viewHolder.cb_bus_select;

                if (checkBox.isChecked()){

                    EventBus.getDefault().post(new BusAdapterBean(checkBox));

                }else {

                }
                map.put(position,checkBox.isChecked());
            }
        });


        viewHolder.cb_bus_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    viewHolder.iv_bus_down.setVisibility(View.VISIBLE);

                    //判断
                    App.currentBusStations = position;

                    Log.e(TAG, position + "onCheckedChanged: " +App.currentBusStations );

                }else {

                    viewHolder.iv_bus_down.setVisibility(View.INVISIBLE);
                }

                map.put(position,b);



            }
        });

        if (map.get(position)!=null)
            viewHolder.cb_bus_select.setChecked(map.get(position));


        map.put(position,viewHolder.cb_bus_select.isChecked()) ;

        viewHolder.cb_bus_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckBox checkBox =(CheckBox)view;

                if (checkBox.isChecked()){

                    EventBus.getDefault().post(new BusAdapterBean(checkBox));

                }else {

                }
                map.put(position,checkBox.isChecked());
            }
        });




        return convertView;
    }
    class ViewHolder {
        TextView tv_bus_zhan;
        ImageView iv_bus_down;
        ImageButton ib_bus_select;
        CheckBox cb_bus_select;

        ViewHolder(){
            EventBus.getDefault().register(this);
        }

        @Subscribe(threadMode = ThreadMode.MAIN)
        public void Event(BusAdapterBean event) {

            if (event.getCheckBox().equals(cb_bus_select)) {
                cb_bus_select.setChecked(true);
            }else {
                cb_bus_select.setChecked(false);
            }
        }
    }



}
