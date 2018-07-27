package com.endymion.collection;

import android.os.Handler;
import android.util.Log;

import com.endymion.collection.presenter.TestPresenter;
import com.endymion.collection.ui.view.TestViewBridge;
import com.endymion.common.base.BasePresenterActivity;
import com.endymion.common.util.TimeMillisUtils;

import java.util.TimeZone;

public class MainActivity extends BasePresenterActivity<TestPresenter> implements TestViewBridge {
    private static final String TAG = "MainActivity";

    @Override
    protected void initPresenter() {
        mPresenter = new TestPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        mPresenter.getViewBridge().showToast("TEST");
        mPresenter.getViewBridge().showLoading();
        mPresenter.getViewBridge().hideLoading();
        mPresenter.test();
        mPresenter.getViewBridge().extraMethod();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String date = TimeMillisUtils.dateLong2StrGMT8(MainActivity.this, System.currentTimeMillis(), TimeMillisUtils.TIME_FORMAT);
                String date1 = TimeMillisUtils.dateLong2StrGMT8(MainActivity.this, TimeMillisUtils.getInstance().getServerTimeMillis(), TimeMillisUtils.TIME_FORMAT);
                String date2 = TimeMillisUtils.dateLong2StrDefault(MainActivity.this, TimeMillisUtils.getInstance().getServerTimeMillis(), TimeMillisUtils.TIME_FORMAT);
                Log.w(TAG, "System GMT+08:00 is " + date);
                Log.w(TAG, "Server GMT+08:00 is " + date1);
                Log.w(TAG, "Server " + TimeZone.getDefault() + " is " + date2);
            }
        }, 3000);
    }

    @Override
    public void extraMethod() {
        Log.w(TAG, "extraMethod()");
    }
}
