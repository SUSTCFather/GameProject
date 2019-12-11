package com.example.wuziqi.retrofit.manager;



import com.example.wuziqi.retrofit.HttpHandler;
import com.example.wuziqi.retrofit.RetrofitHelper;
import com.example.wuziqi.retrofit.RetrofitService;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//response
public abstract class BaseDataManager<T> implements Observer<T>{
    protected RetrofitService retrofitService;

    private HttpHandler<T> httpHandler;

    public BaseDataManager(HttpHandler<T> handler) {
        this.retrofitService = RetrofitHelper.getInstance().getServer();
        this.httpHandler = handler;
    }

    protected void initObservable(Observable<T> observable) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T response) {
        httpHandler.onResultSuccess(response);
    }

    @Override
    public void onError(Throwable e) {
        httpHandler.onResultError(e);
    }

    @Override
    public void onComplete() {

    }
}
