package com.endymion.common;

import android.app.Activity;
import android.support.multidex.MultiDexApplication;

import com.endymion.common.util.TimeMillisUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.LinkedList;
import java.util.List;

/**
 * Application基类
 * Created by Jim on 2018/07/26.
 */

public class BaseApplication extends MultiDexApplication {
    private RefWatcher mRefWatcher;
    private static BaseApplication mInstance;
    private List<Activity> activityList = new LinkedList<>();

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

    /**
     * 添加Activity到容器中
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 遍历所有Activity并finish
     * 在每一个Activity中的onCreate方法里添加该Activity到MyApplication对象实例容器中
     * MyApplication.getInstance().addActivity(this);
     * 在需要结束所有Activity的时候调用exit方法
     * MyApplication.getInstance().exit();
     */
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }
}