package com.endymion.weather;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;

import com.endymion.common.component.ComponentProxyInterface;

import java.io.Serializable;

/**
 * Created by Jim Lee on 2019/2/15.
 */
@Keep
public class WeatherProxy implements ComponentProxyInterface {

    @Override
    public void start(Activity activity, @Nullable Integer requestCode, Serializable... params) {
        Intent intent = new Intent();
        intent.setClass(activity, MainActivity.class);
        if (requestCode == null) {
            activity.startActivity(intent);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }
}
