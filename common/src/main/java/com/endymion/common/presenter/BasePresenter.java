package com.endymion.common.presenter;

import com.endymion.common.presenter.interfaces.BaseViewBridge;

/**
 *
 * Created by Jim on 2018/07/19.
 */

public class BasePresenter<T extends BaseViewBridge> {
    private T viewBridge;

    /**
     * 绑定view，在初始化中调用
     */
    public void attachView(T viewBridge) {
        if (viewBridge != null) {
            this.viewBridge = viewBridge;
        }
    }

    /**
     * 解绑view，在onDestroy中调用
     */
    public void detachView() {
        viewBridge = null;
    }

    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    public boolean isViewAttached() {
        return viewBridge != null;
    }

    /**
     * 获取UI控制器
     */
    public T getViewBridge() {
        return viewBridge;
    }
}
