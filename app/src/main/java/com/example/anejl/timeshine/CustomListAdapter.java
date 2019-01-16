package com.example.anejl.timeshine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
    private ArrayList<TaskItem> tasks;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context aContext, ArrayList<TaskItem> tasks){
        this.tasks=tasks;
        layoutInflater=LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
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
            holder.duration=convertView.findViewById(R.id.d);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.name.setText(tasks.get(position).getName());
        holder.type.setText(tasks.get(position).getType());
        holder.duration.setText(tasks.get(position).getDuration());
        return convertView;
    }

    static class ViewHolder{
        TextView name;
        TextView type;
        TextView duration;
    }
}
