package com.endymion.common.ui.fragment;

import android.support.v4.app.Fragment;

import com.endymion.common.BaseApplication;

/**
 * Fragment基类
 * Created by Jim on 2018/07/26.
 */

public class BaseFragment extends Fragment {
    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication.getInstance().getRefWatcher().watch(this);
    }
}
