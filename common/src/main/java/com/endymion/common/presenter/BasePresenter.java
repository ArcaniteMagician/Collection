package com.endymion.common.presenter;

import android.util.Log;

import com.endymion.common.ui.view.BaseViewBridge;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Jim on 2018/07/19.
 */

public class BasePresenter<T extends BaseViewBridge> {
    private String TAG = "BasePresenter";

    private T mViewBridge;
    private CompositeDisposable mCompositeDisposable;

    /**
     * 绑定view，在初始化中调用
     */
    public void attachView(T viewBridge) {
        if (viewBridge != null) {
            this.mViewBridge = viewBridge;
        }
    }

    /**
     * 解绑view，在onDestroy中调用
     */
    public void detachView() {
        mViewBridge = null;
    }

    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    public boolean isViewAttached() {
        return mViewBridge != null;
    }

    /**
     * 获取UI控制器
     */
    public T getViewBridge() {
        return mViewBridge;
    }

    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            synchronized (BasePresenter.class) {
                if (mCompositeDisposable == null) {
                    mCompositeDisposable = new CompositeDisposable();
                }
            }
        }
        mCompositeDisposable.add(disposable);
    }

    public void removeDisposable(Disposable disposable) {
        if (mCompositeDisposable != null && mCompositeDisposable.size() > 0) {
            mCompositeDisposable.remove(disposable);
        } else {
            Log.w(TAG, "mCompositeDisposable is null or empty");
        }
    }

    public void clearDisposables() {
        if (mCompositeDisposable != null && mCompositeDisposable.size() > 0) {
            mCompositeDisposable.clear();
        }
    }
}
