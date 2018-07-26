package com.endymion.common.base;

import android.support.v4.app.Fragment;

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
