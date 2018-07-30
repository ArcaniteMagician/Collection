package com.endymion.collection.test.presenter;

import android.util.Log;

import com.endymion.collection.test.ui.view.TestViewBridge;
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
