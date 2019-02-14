package com.endymion.weather;

import android.os.Bundle;

import com.endymion.common.ui.activity.BaseActivity;
import com.endymion.weather.ui.fragment.HomeFragment;

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
