package com.endymion.collection;

import android.app.Application;

import com.endymion.common.util.TimeMillisUtils;

/**
 * Created by Jim on 2018/07/24.
 */

public class CollectionApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initTime();
    }

    private void initTime() {
        TimeMillisUtils.getInstance().initServerTime(this, "https://www.baidu.com");
    }
}
