package com.endymion.weather.presenter;

import com.endymion.common.net.RetrofitHelper;
import com.endymion.common.presenter.BasePresenter;
import com.endymion.common.util.RxUtils;
import com.endymion.weather.conf.Constant;
import com.endymion.weather.model.entity.WeatherInfo;
import com.endymion.weather.model.service.WeatherService;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by Jim on 2018/08/21.
 */

public class WeatherPresenter extends BasePresenter {
    private WeatherService weatherService;

    public WeatherPresenter() {
        this(RetrofitHelper.getInstance().buildRetrofit(Constant.BASE_URL));
    }

    public WeatherPresenter(Retrofit retrofit) {
        weatherService = retrofit.create(WeatherService.class);
    }

    public Observable<WeatherInfo> getWeatherInfo(String cityAdCode) {
        return weatherService.getWeather(Constant.AMAP_WEB_SERVICE_KEY, cityAdCode, "all", null)
                .compose(RxUtils.<WeatherInfo>applySchedulers());
    }
}
