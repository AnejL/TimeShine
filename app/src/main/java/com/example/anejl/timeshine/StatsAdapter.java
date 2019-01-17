package com.example.anejl.timeshine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StatsAdapter extends BaseAdapter {
    private ArrayList<StatsItem> stats;
    private LayoutInflater layoutInflater;

    public StatsAdapter(Context aContext, ArrayList<StatsItem> stats){
        this.stats = stats;
        layoutInflater=LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return stats.size();
    }

    @Override
    public Object getItem(int position) {
        return stats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_row_layout,null);
            holder = new ViewHolder();
            holder.name=convertView.findViewById(R.id.n);
            holder.type=convertView.findViewById(R.id.t);
            holder.comment=convertView.findViewById(R.id.c);
            holder.rating=convertView.findViewById(R.id.r);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.name.setText(stats.get(position).getName());
        holder.type.setText(stats.get(position).getType());
        holder.rating.setText(stats.get(position).getRating());
        holder.comment.setText(stats.get(position).getComment());

        return convertView;
    }

    static class ViewHolder{
        TextView name;
        TextView type;
        TextView rating;
        TextView comment;
    }
}
