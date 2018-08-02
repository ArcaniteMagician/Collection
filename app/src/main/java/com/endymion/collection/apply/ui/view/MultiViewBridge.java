package com.endymion.collection.apply.ui.view;

import com.endymion.collection.test.ui.view.TestViewBridge;

/**
 * 当涉及多个Presenter时，定义一个继承自所有Presenter用到的ViewBridge的接口
 * Created by Jim on 2018/08/02.
 */

public interface MultiViewBridge extends TestViewBridge, SecondViewBridge {
}
