package com.endymion.weather;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.endymion.common.ui.activity.BasePresenterActivity;
import com.endymion.common.util.RxUtils;
import com.endymion.weather.conf.Constant;
import com.endymion.weather.model.entity.WeatherInfo;
import com.endymion.weather.presenter.IP2WeatherPresenter;
import com.endymion.weather.ui.view.IP2WeatherViewBridge;

public class MainActivity extends BasePresenterActivity<IP2WeatherPresenter> implements IP2WeatherViewBridge {
    private static final String TAG = "WeatherMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "weather", Toast.LENGTH_SHORT).show();
        TextView textView = findViewById(R.id.txt);
        Log.w("Weather", String.valueOf(textView == null));
        mPresenter.getLocalWeather()
                .compose(RxUtils.<WeatherInfo>applySchedulers())
                .subscribe(new RxUtils.SimpleObserver<WeatherInfo>(mPresenter) {
                    @Override
                    public void onResult(WeatherInfo weatherInfo) {
                        if (weatherInfo != null && Constant.SUCCESS.equals(weatherInfo.getStatus())) {
                            // TODO- 界面处理
                            Log.w(TAG, "城市为" + weatherInfo.getForecasts().get(0).getCity());
                        }
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        // 注意layout的文件名不能与其它模块中的一样，否则在产品模式下，可能会将其它模块的layout作为布局
        return R.layout.weather_activity_main;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new IP2WeatherPresenter(this);
    }
}
