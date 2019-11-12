package com.example.wuziqi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wuziqi.view.AnswerItemView;

import java.util.List;

public class AnswerItemAdapter extends BaseAdapter {

    private List<AnswerChoice> mList;
    private Context mContext;

    public AnswerItemAdapter(Context context, List<AnswerChoice> list){
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int pos) {
        return mList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.listview_item_answer, null);
            holder.itemView = view.findViewById(R.id.item_view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }

        holder.itemView.setContentText(mList.get(pos).getContent());
        holder.itemView.setOptionText(mList.get(pos).getOption());
        return view;
    }

    static class ViewHolder{
        AnswerItemView itemView;
    }
}
