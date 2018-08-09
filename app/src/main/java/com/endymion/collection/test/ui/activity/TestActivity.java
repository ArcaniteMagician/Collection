package com.endymion.collection.test.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.endymion.collection.CollectionApplication;
import com.endymion.collection.R;
import com.endymion.collection.apply.presenter.MultiPresenter;
import com.endymion.collection.apply.ui.view.MultiViewBridge;
import com.endymion.common.ui.activity.BasePresenterActivity;
import com.endymion.common.util.TimeMillisUtils;

import java.util.TimeZone;

public class TestActivity extends BasePresenterActivity<MultiPresenter> implements MultiViewBridge {
    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String date = TimeMillisUtils.dateLong2StrGMT8(TestActivity.this, System.currentTimeMillis(), TimeMillisUtils.TIME_FORMAT);
                String date1 = TimeMillisUtils.dateLong2StrGMT8(TestActivity.this, TimeMillisUtils.getInstance().getServerTimeMillis(), TimeMillisUtils.TIME_FORMAT);
                String date2 = TimeMillisUtils.dateLong2StrDefault(TestActivity.this, TimeMillisUtils.getInstance().getServerTimeMillis(), TimeMillisUtils.TIME_FORMAT);
                Log.w(TAG, "System GMT+08:00 is " + date);
                Log.w(TAG, "Server GMT+08:00 is " + date1);
                Log.w(TAG, "Server " + TimeZone.getDefault() + " is " + date2);
            }
        }, 3000);

        mPresenter.getTestPresenter().test();
        mPresenter.getTestPresenter().allMethod();
        mPresenter.getSecondPresenter().allMethod();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_activity_main;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MultiPresenter();
    }

    @Override
    public void extraMethod() {
        Log.w(TAG, "extraMethod()");
    }

    @Override
    public void secondViewTest() {
        Log.w(TAG, "secondTest()");
    }

    public void exit(View view) {
        CollectionApplication.getInstance().exit();
    }
}
