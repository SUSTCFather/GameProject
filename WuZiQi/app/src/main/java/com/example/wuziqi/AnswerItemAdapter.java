package com.example.wuziqi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wuziqi.bean.Answer;
import com.example.wuziqi.view.AnswerItemView;

import java.util.ArrayList;
import java.util.List;

public class AnswerItemAdapter extends BaseAdapter {

    private List<Answer> mList;
    private Context mContext;

    public AnswerItemAdapter(Context context){
        mList = new ArrayList<>();
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

    public void refreshData(List<Answer> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
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
        holder.itemView.setOptionText(mList.get(pos).getSymbol());
        return view;
    }

    static class ViewHolder{
        AnswerItemView itemView;
    }
}
