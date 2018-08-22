package com.endymion.weather;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.endymion.common.ui.activity.BaseActivity;
import com.endymion.common.ui.activity.BasePresenterActivity;
import com.endymion.common.util.RxUtils;
import com.endymion.weather.conf.Constant;
import com.endymion.weather.model.entity.WeatherInfo;
import com.endymion.weather.presenter.IP2WeatherPresenter;
import com.endymion.weather.ui.fragment.HomeFragment;
import com.endymion.weather.ui.view.IP2WeatherViewBridge;

public class MainActivity extends BaseActivity {
    private static final String TAG = "WeatherMainActivity";
    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFragment();
    }

    private void initFragment() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        homeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.fl_main, homeFragment, HomeFragment.TAG);
        fragmentTransaction.commit();
    }

    @Override
    protected int getLayoutId() {
        // 注意layout的文件名不能与其它模块中的一样，否则在产品模式下，可能会将其它模块的layout作为布局
        return R.layout.weather_activity_main;
    }
}
