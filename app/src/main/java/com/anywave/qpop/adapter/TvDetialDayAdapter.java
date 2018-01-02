package com.anywave.qpop.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.anywave.qpop.bean.DayInfo;
import com.anywave.qpop.R;

import java.util.ArrayList;
import java.util.List;


public class TvDetialDayAdapter extends RecyclerView.Adapter<TvDetialDayAdapter.DetialDayViewHolder> {
    Context context;
    OnItemClickListener listener;
    List<DayInfo> list = new ArrayList<>();
    String day;
    Boolean[] booleen;
    int week ;

    public TvDetialDayAdapter(Context context, int week) {

        this.context = context;
        this.week = week;
        booleen = new Boolean[8];
        for (int i = 0; i < booleen.length; i++) {
            booleen[i] = false;
        }
        booleen[week] =true;
        test();
    }

    @Override
    public DetialDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DetialDayViewHolder(LayoutInflater.from(context).inflate(R.layout.item_day,parent,false));
    }

    @Override
    public void onBindViewHolder(final DetialDayViewHolder holder, final int position) {

        if(list.get(position).isChecked()){
            holder.start_line.setVisibility(View.VISIBLE);
            holder.end_line.setVisibility(View.GONE);
            holder.top_line.setVisibility(View.VISIBLE);
            holder.bottom_line.setVisibility(View.VISIBLE);
            Paint paint = holder.tv.getPaint();
            paint.setFakeBoldText(true);
        }else{
            holder.start_line.setVisibility(View.GONE);
            holder.end_line.setVisibility(View.VISIBLE);
            holder.top_line.setVisibility(View.GONE);
            holder.bottom_line.setVisibility(View.GONE);
            Paint paint = holder.tv.getPaint();
            paint.setFakeBoldText(false);
        }

        holder.tv.setText(list.get(position).getDay());

        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancleAll();
                list.get(position).setChecked(true);
                notifyDataSetChanged();
                if(listener != null){
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DetialDayViewHolder extends RecyclerView.ViewHolder{
        TextView tv ;
        View start_line,end_line,top_line,bottom_line;
        public DetialDayViewHolder(View itemView) {
            super(itemView);
            tv = (TextView)itemView.findViewById(R.id.item_day_tv);
            start_line = itemView.findViewById(R.id.start_line);
            end_line = itemView.findViewById(R.id.end_line);
            top_line = itemView.findViewById(R.id.top_line);
            bottom_line = itemView.findViewById(R.id.bottom_line);
        }
    }

    private void test(){
        list.add(new DayInfo("周一",booleen[2]));
        list.add(new DayInfo("周二",booleen[3]));
        list.add(new DayInfo("周三",booleen[4]));
        list.add(new DayInfo("周四",booleen[5]));
        list.add(new DayInfo("周五",booleen[6]));
        list.add(new DayInfo("周六",booleen[7]));
        list.add(new DayInfo("周日",booleen[1]));

        /*list.add(new DayInfo("周一",true));
        list.add(new DayInfo("周二",false));
        list.add(new DayInfo("周三",false));
        list.add(new DayInfo("周四",false));
        list.add(new DayInfo("周五",false));
        list.add(new DayInfo("周六",false));
        list.add(new DayInfo("周日",false));*/
    }

    private void cancleAll(){
        for(DayInfo dayInfo : list){
            dayInfo.setChecked(false);
        }
    }

    public interface OnItemClickListener{
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
