package com.endymion.collection.presenter;

import android.util.Log;

import com.endymion.collection.presenter.interfaces.TestViewBridge;
import com.endymion.common.presenter.BasePresenter;

/**
 * 业务管理
 * Created by Jim on 2018/07/19.
 */

public class TestPresenter extends BasePresenter<TestViewBridge> {
    public void test() {
        Log.w("TestPresenter", "test()");
    }
}
