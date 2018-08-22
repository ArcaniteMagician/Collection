package com.endymion.weather.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.endymion.common.ui.fragment.BasePresenterFragment;
import com.endymion.common.util.RxUtils;
import com.endymion.weather.R;
import com.endymion.weather.model.entity.WeatherInfo;
import com.endymion.weather.presenter.IP2WeatherPresenter;
import com.endymion.weather.ui.view.IP2WeatherViewBridge;

/**
 * Created by arcanite on 2018/8/22.
 */

public class HomeFragment extends BasePresenterFragment<IP2WeatherPresenter> implements IP2WeatherViewBridge {
    public static final String TAG = "HomeFragment";

    @Override
    protected int getLayoutId() {
        return R.layout.weather_fragment_home;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPresenter.getLocalWeather()
                .compose(RxUtils.<WeatherInfo>applySchedulers())
                .subscribe(new RxUtils.SimpleObserver<WeatherInfo>(mPresenter) {
                    @Override
                    public void onResult(WeatherInfo weatherInfo) {
                        if (weatherInfo != null) {
                            Log.w(TAG, "city is " + weatherInfo.getForecasts().get(0).getCity());
                        }
                    }
                });
    }

    @Override
    protected void initPresenter() {
        mPresenter = new IP2WeatherPresenter();
    }
}
