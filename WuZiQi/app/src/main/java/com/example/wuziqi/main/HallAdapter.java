package com.example.wuziqi.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wuziqi.R;
import com.example.wuziqi.bean.GameData;
import com.example.wuziqi.bean.Hall;
import com.example.wuziqi.view.listener.OnItemClickListener;
import com.example.wuziqi.view.listener.OnViewClickListener;

import org.w3c.dom.Text;

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

        holder.whiteSeat.setOnClickListener(lister);
        holder.blackSeat.setOnClickListener(lister);
        //bindview
        Hall hall = dataList.get(position);
        holder.hallId.setText(String.format("- %d -",hall.getHallId()));
        if(hall.getWhitePlayer() != null) {
            holder.whiteHead.setImageResource(R.drawable.ic_head);
            holder.whiteName.setText(hall.getWhitePlayer().getUserName());
        }else {
            holder.whiteHead.setImageResource(R.drawable.ic_mine_normal);
            holder.whiteName.setText("");
        }

        if(hall.getBlackPlayer() != null) {
            holder.blackHead.setImageResource(R.drawable.ic_head);
            holder.blackName.setText(hall.getBlackPlayer().getUserName());
        }else {
            holder.blackHead.setImageResource(R.drawable.ic_mine_normal);
            holder.blackName.setText("");
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class HallViewHolder extends RecyclerView.ViewHolder {

        LinearLayout whiteSeat;
        TextView whiteName;
        ImageView whiteHead;
        LinearLayout blackSeat;
        TextView blackName;
        ImageView blackHead;
        TextView hallId;

        public HallViewHolder(@NonNull View itemView) {
            super(itemView);
            whiteSeat = itemView.findViewById(R.id.white_seat);
            whiteName = itemView.findViewById(R.id.white_name);
            whiteHead = itemView.findViewById(R.id.white_head);
            blackSeat = itemView.findViewById(R.id.black_seat);
            blackName = itemView.findViewById(R.id.black_name);
            blackHead = itemView.findViewById(R.id.black_head);
            hallId = itemView.findViewById(R.id.hall_id);
        }
    }
}
