package com.example.wuziqi.rank;

import com.example.wuziqi.bean.response.RankResponse;


public interface RankContract {
    interface RankView {
        void showRankList(RankResponse response);
    }

    interface RankPresenter {
        void getRankList();
    }


}
