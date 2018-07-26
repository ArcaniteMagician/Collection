package com.endymion.common.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.endymion.common.ui.view.BaseViewBridge;

/**
 * 普通页面Activity基类
 * Created by Jim on 2018/07/12.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseViewBridge {
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
    }

    protected abstract int getLayoutId();

    protected void test() {

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
}
