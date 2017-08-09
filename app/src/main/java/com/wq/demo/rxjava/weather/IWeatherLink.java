package com.wq.demo.rxjava.weather;

import com.wq.demo.rxjava.weather.bean.WeatherBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by qiangwang on 8/7/17.
 */

public interface IWeatherLink {
    @GET("weather")
    Observable<WeatherBean> getWeather(@Query("city") String city, @Query("key") String key);
}
