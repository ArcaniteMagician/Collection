package com.endymion.collection.apply.presenter;

import com.endymion.collection.apply.ui.view.SecondViewBridge;
import com.endymion.common.presenter.BasePresenter;

/**
 * Created by Jim on 2018/08/02.
 */

public class SecondPresenter extends BasePresenter<SecondViewBridge> {
    public void allMethod() {
        getViewBridge().showToast("2nd");
        getViewBridge().showLoading();
        getViewBridge().hideLoading();
        getViewBridge().secondViewTest();
    }
}
