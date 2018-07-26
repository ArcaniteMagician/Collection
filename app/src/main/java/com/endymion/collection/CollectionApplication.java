package com.endymion.collection;

import com.endymion.common.base.BaseApplication;

/**
 * 应用
 * Created by Jim on 2018/07/24.
 */

public class CollectionApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initTime("https://www.baidu.com");
    }
}
