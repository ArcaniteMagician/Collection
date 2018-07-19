package com.endymion.common.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.endymion.common.presenter.BasePresenter;
import com.endymion.common.presenter.interfaces.BaseViewBridge;

/**
 * Activity基类
 * Created by Jim on 2018/07/12.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseViewBridge {
    private static final String TAG = "BaseActivity";
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initPresenter();
        //noinspection unchecked
        mPresenter.attachView(this);
        test();
    }

    protected abstract void initPresenter();

    protected abstract int getLayoutId();

    public void test() {

    }

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
