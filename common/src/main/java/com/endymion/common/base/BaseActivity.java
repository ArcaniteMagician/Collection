package com.endymion.common.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 普通页面Activity基类
 * Created by Jim on 2018/07/12.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
    }

    protected abstract int getLayoutId();

    protected void test() {

    }
}
