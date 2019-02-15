package com.endymion.common.component;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by Jim Lee on 2019/2/15.
 */
public class EmptyComponentProxy implements ComponentProxyInterface {

    @Override
    public void start(Activity activity, @Nullable Integer requestCode, Serializable... params) {
        Log.e("ComponentProxy", "EmptyComponentProxy is required to start main activity, context = " + activity.getClass().getName());
    }
}
