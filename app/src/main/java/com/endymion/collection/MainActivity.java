package com.endymion.collection;

import android.util.Log;

import com.endymion.collection.presenter.TestPresenter;
import com.endymion.collection.presenter.interfaces.TestViewBridge;
import com.endymion.common.base.BaseActivity;
import com.endymion.collection.collection.R;

public class MainActivity extends BaseActivity<TestPresenter> implements TestViewBridge {

    @Override
    protected void initPresenter() {
        mPresenter = new TestPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void test() {
        mPresenter.getViewBridge().showToast("TEST");
        mPresenter.getViewBridge().showErr();
        mPresenter.getViewBridge().showLoading();
        mPresenter.getViewBridge().hideLoading();
        mPresenter.test();
        mPresenter.getViewBridge().extraMethod();
    }

    @Override
    public void extraMethod() {
        Log.w("MainActivity", "extraMethod()");
    }
}
