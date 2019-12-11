package com.example.wuziqi.main.fragment.hall;


import com.example.wuziqi.bean.Hall;
import com.example.wuziqi.bean.request.EnterRequest;
import com.example.wuziqi.bean.response.EnterResponse;
import com.example.wuziqi.bean.response.HallResponse;


public class HallContract {
    interface HallView {
        void showHall(HallResponse response);

        void showEnter(EnterResponse response);
    }

    interface HallPresenter {
        void getHall();

        void enterHall(EnterRequest request);
    }
}
