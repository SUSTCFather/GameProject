package com.example.wuziqi.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wuziqi.R;
import com.example.wuziqi.bean.Hall;
import com.example.wuziqi.view.GameHeadView;
import com.example.wuziqi.view.listener.OnItemClickListener;
import com.example.wuziqi.view.listener.OnViewClickListener;

import java.util.ArrayList;
import java.util.List;

public class HallAdapter extends RecyclerView.Adapter<HallAdapter.HallViewHolder>{

    private List<Hall> dataList;

    private Context mContext;

    private OnItemClickListener listener;

    public HallAdapter(Context context, OnItemClickListener listener) {
        this.mContext = context;
        this.listener = listener;
        dataList = new ArrayList<>();
    }

    public void refreshData (List<Hall> data) {
        dataList.clear();
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    public Hall getHall(int pos) {
        return dataList.get(pos);
    }

    @NonNull
    @Override
    public HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_hall, parent,false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HallViewHolder holder, int position) {
        OnViewClickListener lister;
        if(holder.itemView.getTag() == null) {
            lister = new OnViewClickListener(listener);
            holder.itemView.setTag(lister);
        }else {
            lister = (OnViewClickListener) holder.itemView.getTag();
        }
        lister.setPosition(position);

        holder.whiteUser.setOnClickListener(lister);
        holder.blackUser.setOnClickListener(lister);
        //bindView
        Hall hall = dataList.get(position);
        holder.hallId.setText(String.format("- %d -",hall.getHallId()));
        holder.whiteUser.refresh(hall.getWhitePlayer(),null);
        holder.blackUser.refresh(hall.getBlackPlayer(),null);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class HallViewHolder extends RecyclerView.ViewHolder {

        GameHeadView whiteUser;
        GameHeadView blackUser;
        TextView hallId;

        HallViewHolder(@NonNull View itemView) {
            super(itemView);
            whiteUser = itemView.findViewById(R.id.white_user);
            blackUser = itemView.findViewById(R.id.black_user);
            hallId = itemView.findViewById(R.id.hall_id);
        }

    }
}
