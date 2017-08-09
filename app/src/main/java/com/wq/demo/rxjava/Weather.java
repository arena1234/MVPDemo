package com.wq.demo.rxjava;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wq.demo.rxjava.weather.IWeatherLink;
import com.wq.demo.rxjava.weather.bean.WeatherBean;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by qiangwang on 8/4/17.
 */

public class Weather {
    private static final String TAG = "qqqq_Weather";
    private static final String BASE_URL = "https://free-api.heweather.com/v5/";
    private static final String KEY = "ec0537145e8c4a7ba6f8e7172c86def2";
    private IWeatherLink mWeatherLink;

    public Weather() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)//日志拦截器
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        // TODO: 16/10/20 进行重定向等操作
                        //Interceptor的典型使用场景，就是对request和response的Headers进行编辑
                        return chain.proceed(chain.request());
                    }
                })//网络拦截器,进行重定向等操作
                .connectTimeout(15, TimeUnit.SECONDS)//设置连接超时
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        mWeatherLink = retrofit.create(IWeatherLink.class);
    }

    public void getWeather(String city) {
        mWeatherLink.getWeather(city, KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    Observer<WeatherBean> mObserver = new Observer<WeatherBean>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
            Log.d(TAG, "onSubscribe");
        }

        @Override
        public void onNext(@NonNull WeatherBean mS) {
            Log.d(TAG, "onNext:"+mS);
            Log.d(TAG, "city:"+mS.getHeWeather5().get(0).getBasic().getCity());
            Log.d(TAG, "city:"+mS.getHeWeather5().get(0).getBasic().getCnty());
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
}
