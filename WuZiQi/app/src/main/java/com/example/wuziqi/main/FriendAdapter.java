package com.example.wuziqi.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wuziqi.R;
import com.example.wuziqi.bean.User;
import com.example.wuziqi.view.listener.OnItemClickListener;
import com.example.wuziqi.view.listener.OnViewClickListener;

import java.util.ArrayList;
import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder>{

    private List<User> userList;

    private Context mContext;

    private OnItemClickListener listener;

    public FriendAdapter(Context context, OnItemClickListener listener) {
        this.mContext = context;
        this.listener = listener;
        userList = new ArrayList<>();
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_friend, parent,false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, final int position) {
        User user = userList.get(position);
        holder.name.setText(user.getUserName());
        OnViewClickListener lister;
        if(holder.itemView.getTag() == null) {
            lister = new OnViewClickListener(listener);
            holder.itemView.setOnClickListener(lister);
            holder.itemView.setTag(lister);
        }else {
            lister = (OnViewClickListener) holder.itemView.getTag();
        }
        lister.setPosition(position);
    }

    public void addUser(List<User> users) {
        userList.addAll(users);
        notifyDataSetChanged();
    }

    public int getItemCount() {
        return userList.size();
    }

    public User getUser(int pos) {
        return userList.get(pos);
    }

    class FriendViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.user_name);
        }
    }
}
