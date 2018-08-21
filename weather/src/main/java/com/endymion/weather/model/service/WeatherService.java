package com.endymion.weather.model.service;

import android.support.annotation.Nullable;

import com.endymion.weather.model.entity.WeatherInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 参考文档：https://lbs.amap.com/api/webservice/guide/api/weatherinfo
 * Created by Jim on 2018/08/21.
 */

public interface WeatherService {
    /**
     * @param key        请求服务权限标识
     * @param cityAdCode 城市名称,输入城市的adcode
     * @param type       气象类型,可选值：base:返回实况天气,all:返回预报天气
     * @param acceptType 返回格式,可选值：JSON,XML,默认值：JSON
     */
    @GET("weather/weatherInfo")
    Observable<WeatherInfo> getWeather(@Query("key") String key, @Query("city") String cityAdCode, @Nullable @Query("extensions") String type, @Nullable @Query("output") String acceptType);
}
