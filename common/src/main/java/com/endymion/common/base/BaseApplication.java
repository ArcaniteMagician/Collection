package com.endymion.common.base;

import android.support.multidex.MultiDexApplication;

import com.endymion.common.util.TimeMillisUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Application基类
 * Created by Jim on 2018/07/26.
 */

public class BaseApplication extends MultiDexApplication {
    private RefWatcher mRefWatcher;
    private static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initLeakCanary();
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }

    public RefWatcher getRefWatcher() {
        return mRefWatcher;
    }

    protected void initTime(String serverHost) {
        TimeMillisUtils.getInstance().initServerTime(this, serverHost);
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        mRefWatcher = LeakCanary.install(this);
    }
}