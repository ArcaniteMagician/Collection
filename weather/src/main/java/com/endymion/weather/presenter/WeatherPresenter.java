package com.endymion.weather.presenter;

import com.endymion.common.presenter.BasePresenter;
import com.endymion.common.util.RxUtils;
import com.endymion.weather.conf.Constant;
import com.endymion.weather.model.entity.WeatherInfo;
import com.endymion.weather.model.service.WeatherService;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jim on 2018/08/21.
 */

public class WeatherPresenter extends BasePresenter {
    private WeatherService weatherService;

    public WeatherPresenter() {
        this(new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build());
    }

    public WeatherPresenter(Retrofit retrofit) {
        weatherService = retrofit.create(WeatherService.class);
    }

    public Observable<WeatherInfo> getWeatherInfo(String cityAdCode) {
        return weatherService.getWeather(Constant.AMAP_WEB_SERVICE_KEY, cityAdCode, "all", null)
                .compose(RxUtils.<WeatherInfo>applySchedulers());
    }
}
