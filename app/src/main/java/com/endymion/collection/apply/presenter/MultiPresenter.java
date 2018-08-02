package com.endymion.collection.apply.presenter;

import com.endymion.collection.apply.ui.view.MultiViewBridge;
import com.endymion.collection.test.presenter.TestPresenter;
import com.endymion.common.presenter.BasePresenter;

/**
 * 当涉及多个Presenter时，定义一个复合Presenter
 * Created by Jim on 2018/08/02.
 */

public class MultiPresenter extends BasePresenter<MultiViewBridge> {
    private TestPresenter testPresenter;
    private SecondPresenter secondPresenter;

    public MultiPresenter() {
        testPresenter = new TestPresenter();
        secondPresenter = new SecondPresenter();
    }

    public TestPresenter getTestPresenter() {
        return testPresenter;
    }

    public SecondPresenter getSecondPresenter() {
        return secondPresenter;
    }

    @Override
    public void attachView(MultiViewBridge viewBridge) {
        testPresenter.attachView(viewBridge);
        secondPresenter.attachView(viewBridge);
    }

    @Override
    public void detachView() {
        testPresenter.detachView();
        secondPresenter.detachView();
    }
}
