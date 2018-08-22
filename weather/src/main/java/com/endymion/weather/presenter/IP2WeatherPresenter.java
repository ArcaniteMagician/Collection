package com.endymion.weather.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.endymion.common.net.RetrofitHelper;
import com.endymion.common.presenter.BasePresenter;
import com.endymion.common.util.RxUtils;
import com.endymion.weather.conf.Constant;
import com.endymion.weather.model.entity.IPInfo;
import com.endymion.weather.model.entity.WeatherInfo;
import com.endymion.weather.ui.view.IP2WeatherViewBridge;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Jim on 2018/08/21.
 */

public class IP2WeatherPresenter extends BasePresenter<IP2WeatherViewBridge> {
    private static final String TAG = "IP2WeatherPresenter";

    private IPInfoPresenter ipInfoPresenter;
    private WeatherPresenter weatherPresenter;

    public IP2WeatherPresenter() {
        Retrofit retrofit = RetrofitHelper.getInstance().buildRetrofit(Constant.BASE_URL);
        ipInfoPresenter = new IPInfoPresenter(retrofit);
        weatherPresenter = new WeatherPresenter(retrofit);
    }

    @Override
    public void attachView(IP2WeatherViewBridge viewBridge) {
        ipInfoPresenter.attachView(viewBridge);
        weatherPresenter.attachView(viewBridge);
    }

    @Override
    public void detachView() {
        ipInfoPresenter.detachView();
        weatherPresenter.detachView();
    }

    public IPInfoPresenter getIpInfoPresenter() {
        return ipInfoPresenter;
    }

    public WeatherPresenter getWeatherPresenter() {
        return weatherPresenter;
    }

    public Observable<WeatherInfo> getLocalWeather() {
        return ipInfoPresenter.getIPInfo().compose(RxUtils.<IPInfo>applySchedulers())
                .doOnNext(new Consumer<IPInfo>() {
                    @Override
                    public void accept(IPInfo ipInfo) throws Exception {
                        Log.w(TAG, "IP info, status = " + ipInfo.getStatus());
                    }
                })
                .observeOn(Schedulers.io())// 回到io线程执行任务
                .flatMap(new Function<IPInfo, ObservableSource<WeatherInfo>>() {
                    @Override
                    public ObservableSource<WeatherInfo> apply(IPInfo ipInfo) throws Exception {
                        if (ipInfo != null
                                && Constant.SUCCESS.equals(ipInfo.getStatus())
                                && !TextUtils.isEmpty(ipInfo.getAdcode())) {
                            return weatherPresenter.getWeatherInfo(ipInfo.getAdcode());
                        }
                        return null;
                    }
                });
    }
}
