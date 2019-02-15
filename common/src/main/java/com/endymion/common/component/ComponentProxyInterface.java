package com.endymion.common.component;

import android.app.Activity;
import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by Jim Lee on 2019/2/15.
 */
public interface ComponentProxyInterface {

    default void start(Activity activity) {
        start(activity, null);
    }

    void start(Activity activity, @Nullable Integer requestCode, Serializable... params);
}
