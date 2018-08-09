package com.endymion.common.ui.activity;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.endymion.common.BaseApplication;
import com.endymion.common.ui.view.BaseViewBridge;

/**
 * 普通页面Activity基类
 * Created by Jim on 2018/07/12.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseViewBridge {
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置全屏模式
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(getLayoutId());
        ((BaseApplication) getApplication()).addActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    /**
     * 在onPause()状态下进行数据持久化操作
     */
    private void saveData() {
    }

    protected abstract int getLayoutId();

    @Override
    public void showLoading() {
        Log.w(TAG, "showLoading()");
    }

    @Override
    public void hideLoading() {
        Log.w(TAG, "hideLoading()");
    }

    @Override
    public void showToast(String msg) {
        Log.w(TAG, "showToast()");
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(@StringRes int msgId) {
        Toast.makeText(this, msgId, Toast.LENGTH_SHORT).show();
    }
}
