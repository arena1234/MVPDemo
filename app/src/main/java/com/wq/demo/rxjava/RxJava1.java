package com.wq.demo.rxjava;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by qiangwang on 8/4/17.
 */

public class RxJava1 {
    private static final String TAG = "qqqq_RxJava1";
    public RxJava1(){
        Observable<String> observable  = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(@NonNull ObservableEmitter e) throws Exception {
                e.onNext("11111111111");
                e.onNext("22222222222");
                e.onNext("33333333333");
                e.onComplete();
            }
        });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(@NonNull String mS) {
                Log.d(TAG, "onNext:"+mS);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };
        observable.subscribeOn(Schedulers.newThread())      // 发送者所在线程
                .observeOn(AndroidSchedulers.mainThread())  // 观察者所在线程
                .subscribe(observer);
    }
}
