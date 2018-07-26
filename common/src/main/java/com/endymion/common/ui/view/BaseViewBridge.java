package com.endymion.common.ui.view;

/**
 * 为Presenter提供UI控制方法
 * Created by Jim on 2018/07/19.
 */

public interface BaseViewBridge {
    /**
     * 显示正在加载view
     */
    void showLoading();

    /**
     * 关闭正在加载view
     */
    void hideLoading();

    /**
     * 显示提示
     * @param msg 提示内容
     */
    void showToast(String msg);

    /**
     * 显示请求错误提示
     */
    void showErr();
}
