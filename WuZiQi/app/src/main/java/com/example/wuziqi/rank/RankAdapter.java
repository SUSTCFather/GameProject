package com.example.wuziqi.rank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wuziqi.R;
import com.example.wuziqi.bean.User;

import java.util.ArrayList;
import java.util.List;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.FriendViewHolder>{

    private List<User> userList;

    private Context mContext;

    public RankAdapter(Context context) {
        this.mContext = context;
        userList = new ArrayList<>();
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rank, parent,false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, final int position) {
        User user = userList.get(position);
        //前五名
        if(position <= 4) {
            holder.score.setTextColor(ContextCompat.getColor(mContext,R.color.colorGold));
        }else {
            holder.score.setTextColor(ContextCompat.getColor(mContext,R.color.colorGreen));
        }
        holder.index.setText(String.valueOf(position+1));
        holder.score.setText(String.valueOf(user.getScore()));
        holder.record.setText(String.format("%d胜%d负",user.getWinNum(),user.getLoseNum()));
        holder.name.setText(user.getUserName());
    }

    public void refreshData(List<User> users) {
        userList.clear();
        userList.addAll(users);
        notifyDataSetChanged();
    }

    public int getItemCount() {
        return userList.size();
    }

    class FriendViewHolder extends RecyclerView.ViewHolder {

        TextView index;
        TextView name;
        TextView record;
        TextView score;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            index = itemView.findViewById(R.id.index);
            name = itemView.findViewById(R.id.name);
            record = itemView.findViewById(R.id.record);
            score = itemView.findViewById(R.id.score);
        }
    }
}
