package com.endymion.common.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.endymion.common.presenter.BasePresenter;

/**
 * 业务类Fragment基类
 * Created by arcanite on 2018/8/8.
 */

public abstract class BasePresenterFragment<T extends BasePresenter> extends BaseFragment {
    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        //noinspection unchecked
        mPresenter.attachView(this);
    }

    protected abstract void initPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }
}
