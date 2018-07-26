package com.endymion.common.base;

import android.os.Bundle;
import android.util.Log;

import com.endymion.common.presenter.BasePresenter;
import com.endymion.common.presenter.interfaces.BaseViewBridge;

/**
 * 业务类页面Activity基类
 * Created by Jim on 2018/07/26.
 */

public abstract class BasePresenterActivity<T extends BasePresenter> extends BaseActivity implements BaseViewBridge {
    private static final String TAG = "BasePresenterActivity";
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        //noinspection unchecked
        mPresenter.attachView(this);
        test();
    }

    protected abstract void initPresenter();

    @Override
    public void showLoading() {
        Log.w(TAG, "showLoading()");
    }

    @Override
    public void hideLoading() {
        Log.w(TAG, "hideLoading()");
    }

    @Override
    public void showToast(String msg) {
        Log.w(TAG, "showToast()");
    }

    @Override
    public void showErr() {
        Log.w(TAG, "showErr()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
