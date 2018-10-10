package com.endymion.common.ui.view;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

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
     *
     * @param msg 提示内容
     */
    default void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示提示
     *
     * @param msgId 提示内容id
     */
    default void showToast(@StringRes int msgId) {
        Toast.makeText(getContext(), msgId, Toast.LENGTH_SHORT).show();
    }

    Context getContext();
}
