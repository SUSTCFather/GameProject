package com.example.wuziqi.retrofit;

public interface HttpHandler<T> {

    void onResultSuccess(T response);

    void onResultError(Throwable e);

}
