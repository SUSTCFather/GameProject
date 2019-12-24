package com.example.wuziqi.history;

import com.example.wuziqi.bean.response.HistoryResponse;

public interface HistoryContract {
    interface HistoryView {
        void showHistory(HistoryResponse response);
    }

    interface HistoryPresenter {
        void getHistory(long useId);
    }
}
