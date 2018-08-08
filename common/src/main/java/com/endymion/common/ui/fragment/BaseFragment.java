package com.endymion.common.ui.fragment;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.endymion.common.BaseApplication;
import com.endymion.common.ui.view.BaseViewBridge;

/**
 * Fragment基类
 * Created by Jim on 2018/07/26.
 */

public class BaseFragment extends Fragment implements BaseViewBridge {
    private static final String TAG = "BaseFragment";

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
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(@StringRes int msgId) {
        Toast.makeText(getActivity(), msgId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication.getInstance().getRefWatcher().watch(this);
    }
}
