package com.example.wuziqi.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wuziqi.R;
import com.example.wuziqi.bean.GameRecord;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{
    private final static int HEADER = 1;

    private final static int NORMAL = 0;

    private List<GameRecord> recordList;

    private Context mContext;

    public HistoryAdapter(Context context) {
        this.mContext = context;
        recordList = new ArrayList<>();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == HEADER) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_header, parent,false);
        }else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_record, parent,false);
        }
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        GameRecord gameRecord = recordList.get(position);
        if(itemViewType == HEADER) {
            holder.dateTxt.setText(gameRecord.getDate());
        }else {
            holder.timeTxt.setText(gameRecord.getTime());
            holder.weekTxt.setText(gameRecord.getWeek());
            holder.winnerName.setText(gameRecord.getWinner().getUserName());
            holder.loserName.setText(gameRecord.getLoser().getUserName());
        }
    }

    public void refreshData(List<GameRecord> list) {
        if(list == null || list.isEmpty()) {
            return;
        }
        recordList.clear();
        GameRecord nowRecord = null;
        for(GameRecord record : list) {
            if(nowRecord == null || !record.getDate().equals(nowRecord.getDate())) {
                nowRecord = record;
                recordList.add(new GameRecord(nowRecord.getGameTime(),HEADER));
            }
            recordList.add(record);
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return recordList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView dateTxt;
        TextView weekTxt;
        TextView timeTxt;
        TextView winnerName;
        TextView loserName;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTxt = itemView.findViewById(R.id.date_txt);
            weekTxt = itemView.findViewById(R.id.week_txt);
            timeTxt = itemView.findViewById(R.id.time_txt);
            winnerName = itemView.findViewById(R.id.winner_name);
            loserName = itemView.findViewById(R.id.loser_name);
        }
    }

    @Override
    public int getItemViewType(int position) {
        GameRecord gameRecord = recordList.get(position);
        return gameRecord.getType();
    }

}
