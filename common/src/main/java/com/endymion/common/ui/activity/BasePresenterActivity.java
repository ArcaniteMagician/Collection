package com.endymion.common.ui.activity;

import android.os.Bundle;

import com.endymion.common.presenter.BasePresenter;

/**
 * 业务类页面Activity基类
 * Created by Jim on 2018/07/26.
 */

public abstract class BasePresenterActivity<T extends BasePresenter> extends BaseActivity {
    private static final String TAG = "BasePresenterActivity";
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        //noinspection unchecked
        mPresenter.attachView(this);
    }

    protected abstract void initPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clearDisposables();
        mPresenter.detachView();
    }
}
